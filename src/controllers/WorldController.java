

package controllers;

import game.effects.Buff;
import game.items.PassiveItem;
import game.items.Target;
import game.units.AttackType;
import game.units.Creature;
import game.units.Hero;
import game.units.HeroStats;
import game.units.Stat;
import game.units.Unit;
import game.units.UnitStats;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

import networking.Client;
import networking.NetworkM;
import networking.Server;
import player.Player;
import renderer.Camera;
import renderer.WorldRenderer;
import storage.converters.WorldLoader;
import storage.generators.TemporaryLoader;
import tools.Constants;
import tools.Geometry;
import world.World;
import world.icons.ItemIcon;
import world.icons.Party;
import world.icons.WorldIcon;
import world.tiles.CityTile;
import world.tiles.ImpassableTile;
import world.tiles.PassableTile;
import world.tiles.Tile;
import world.towns.City;
import GUI.EscapeDialog.EscapeDialog;
import GUI.world.GameDialog;
import GUI.world.MainFrame;

/**
 * A WorldController. This is the glue between the model (World) and the view (gui, renderer). It responds to mouse, key, and button presses
 * and informs the World to update its game state. It then tells the gui to redraw itself to show any changes in game state. It also handles
 * interactions between different GUIs - for example, passing over control to TownController when the player opens up a town.
 * @author Aaron
 */
public class WorldController{

	//boolean server or client, true if server, false if client.
	private boolean serverOrClient = false;

	//server and client.
	private Server server;
	private Client client;

	// gui and renderer: the view
	private MainFrame gui;

	// current session in town, if there is one
	private TownController townController;

	// world this controller is for: the model
	private final World world;

	// player this controller belongs to
	private Player player;

	// current tile the player has clicked
	private Point selected;
	private Point hover; // point your mouse is over

	// highlighted tiles that are showing where the player's selected party can move to
	private Set<Point> highlightedTiles;

	// the camera that the player is viewing from
	private Camera camera;

	// whether this worldController is currently doing anything
	private boolean active = true;
	private long lastMouse = 0;


	private ServerSocket serverSocket;
	private Socket socket;


	// key bindings
	private static final int ROTATE_CW = KeyEvent.VK_R;
	private static final int ROTATE_CCW = KeyEvent.VK_E;
	private static final int PAN_UP = KeyEvent.VK_UP;
	private static final int PAN_DOWN = KeyEvent.VK_DOWN;
	private static final int PAN_RIGHT = KeyEvent.VK_RIGHT;
	private static final int PAN_LEFT = KeyEvent.VK_LEFT;

	public WorldController(World w, Player p){
		world = w;
		player = p;
		camera = WorldRenderer.getCentreOfWorld(w);
		gui = new MainFrame();
		gui.setController(this);
		selected = null;
		highlightedTiles = new HashSet<>();

		/**
		 * comment this out to get controller to load from main menu
		 */

		if(serverOrClient){
			NetworkM.createServer(this, 2020, 2);
			try {
				server = new Server();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {

			try {
				NetworkM.createClient("localhost", 2020, "selemonClient");
				client = new Client("130.195.6.170", 45612, 45812);
			} catch (IOException e) {

				e.printStackTrace();
			}
			//			client = new Client("130.195.6.98", 45612);

		}

	}


	public MainFrame getGui(){
		return gui;
	}


	/**
	 * Player has pushed a key. Perform any actions and update/redraw game-state if necessary.
	 * @param ke: details about the key event
	 */
	public void keyPressed(KeyEvent ke){

		int code = ke.getKeyCode();
		if (code == ROTATE_CW){
			camera.rotateClockwise();
			world.updateSprites(true);
			notifier();
			gui.redraw();
		}
		else if (code == ROTATE_CCW){
			camera.rotateCounterClockwise();
			world.updateSprites(false);
			notifier();
			gui.redraw();
		}
		else if (code == PAN_UP){
			camera.panUp();
			notifier();
			gui.redraw();
		}
		else if (code == PAN_DOWN){
			camera.panDown();
			notifier();
			gui.redraw();
		}
		else if (code == PAN_RIGHT){
			camera.panRight();
			notifier();
			gui.redraw();
		}
		else if (code == PAN_LEFT){
			camera.panLeft();
			notifier();
			gui.redraw();
		}
		else if(code == KeyEvent.VK_ESCAPE){
			EscapeDialog dialog = new EscapeDialog(gui,this);
	    }


	}

	/**
	 * Player has clicked on something. Perform any actions depending on the nature of their
	 * click and update + redraw game-state if necessary.
	 * @param me: details about the click.
	 * @param panel: what they clicked on (inventory, world, etc.)
	 */
	public void mousePressed(MouseEvent me){

		Point ptIso = new Point(me.getX(),me.getY());
		Point ptCartesian = Geometry.isometricToCartesian(ptIso, camera, world.dimensions);
		Tile clickedTile = world.getTile(ptCartesian);
		Tile selectedTile = world.getTile(selected);

		// double clicked a city
		if (leftClicked(me) && selectedTile != null
				&& clickedTile instanceof CityTile && selectedTile instanceof CityTile
				&& doubleClicked() && ((CityTile)(clickedTile)).getCity().ownedBy(player) )
		{
			CityTile c1 = (CityTile)clickedTile;
			CityTile c2 = (CityTile)selectedTile;
			if (c1.getCity() == c2.getCity()){
				startTownView(c1.getCity());
			}
			this.lastMouse = System.currentTimeMillis();

		}


		// deselected the tile
		else if (selected != null && leftClicked(me) && selectedTile == clickedTile){
			System.out.println("deselect");
			deselect();
			gui.updateInfo(null);
			gui.redraw();
			this.lastMouse = System.currentTimeMillis();
		}

		// selected the tile
		else if (selectedTile != clickedTile && leftClicked(me)){
			selected = ptCartesian;
			highlightTiles(clickedTile);
			gui.updateInfo(clickedTile);
			gui.redraw();
			this.lastMouse = System.currentTimeMillis();
		}

		// moved
		else if (selected != null && rightClicked(me) && isMyTurn()){

			boolean moved = world.moveParty(player, selected, ptCartesian);
			if (moved){
				selected = ptCartesian;
				highlightTiles(clickedTile);
				gui.updateInfo(clickedTile);
				gui.redraw();
				this.lastMouse = System.currentTimeMillis();
			}
			else if (clickedTile != null) {
				if(clickedTile.passable() && clickedTile.occupant() instanceof ItemIcon) {
					new GameDialog(gui,"Inventory full! You cannot pick up more items!");
				}
			}
		}

	}

	/**
	 * The mouse has moved from lastDrag -> point. Pan the camera and redraw game-state.
	 * @param lastDrag: point mouse started at
	 * @param point: point mouse moved to.
	 */
	public void mouseDragged(Point lastDrag, Point point) {
		int x = point.x - lastDrag.x;
		int y = point.y - lastDrag.y;
		camera.pan(x,y);
		gui.redraw();
	}

	/**
	 * The mouse has been moved. Perform any actions and redraw game state.
	 * @param me: mouse event that moved.
	 */
	public void mouseMoved(MouseEvent me){
		Point ptIso = new Point(me.getX(),me.getY());
		Point ptCartesian = Geometry.isometricToCartesian(ptIso, camera, world.dimensions);
		Tile tileHover = world.getTile(ptCartesian);
		if (tileHover == null) this.hover = ptCartesian;
		else this.hover = null;
	}

	/**
	 * Player has clicked a button. Perform any actions and redraw game state.
	 * @param button: the button they clicked.
	 */
	public void buttonPressed(String button){

		if (button.equals("EndTurn")){
			world.endTurn();
			deselect();
			gui.updateInfo(null);
			gui.redraw();
			gui.makeGameDialog("Day " + world.getDay());
		}

	}

	/**
	 * Deselect the currently selected tile. Un-highlight everything.
	 */
	private void deselect(){
		selected = null;
		resetHighlightedTiles();
	}

	/**
	 * Resets the set of tiles highlighted by this World Controller.
	 */
	private void resetHighlightedTiles(){
		this.highlightedTiles = new HashSet<>();
	}

	/**
	 * If the provided tile has a party on it that belongs to this player, highlight
	 * all the tiles to which the party can move.
	 * @param tile: tile that a party of the player's is standing on.
	 */
	private void highlightTiles(Tile tile){
		if (this.player != world.getCurrentPlayer()) return;
		if (tile != null){
			WorldIcon wi = tile.occupant();
			if (wi != null && wi instanceof Party){
				Party p = (Party)wi;
				if (p.ownedBy(this.player)){
					highlightedTiles = world.getValidMoves(p,tile);
					return;
				}
			}
		}
		resetHighlightedTiles();
	}

	/**
	 * Return true if it is currently the turn of the player attached to this WorldController.
	 * @return: true if it is this controller's owner's turn.
	 */
	public boolean isMyTurn(){
		return world.getCurrentPlayer() == this.player;
	}

	/**
	 * Return true if the time between now and the last mouse event was enough to be
	 * considered a "double click".
	 * @return: true if it was a double click.
	 */
	public boolean doubleClicked(){
		return System.currentTimeMillis() - this.lastMouse < 700;
	}

	/**
	 * Return true if the given mouse event was a left-click.
	 * @param me: mouse event
	 * @return: true if me was a left-click.
	 */
	public boolean leftClicked(MouseEvent me){
		return SwingUtilities.isLeftMouseButton(me);
	}

	/**
	 * Return true if the given mouse event was a right-click.
	 * @param me: mouse event
	 * @return: true if me was a right-click.
	 */
	public boolean rightClicked(MouseEvent me){
		return SwingUtilities.isRightMouseButton(me);
	}

	/**
	 * Return true if this point is being highlighted by the world controller
	 * @param p: a point in Cartesian space
	 * @return: true if this point is being highlighted by the world controller
	 */
	public boolean isHighlighted(Point p){
		return highlightedTiles.contains(p);
	}

	/**
	 * Ends the current town session. Also awakens this WorldController and its attached
	 * gui so they now respond to events.
	 */
	public void endTownView(){
		awake();
		this.townController = null;
		deselect();
		gui.updateInfo(null);
		gui.redraw();
	}

	/**
	 * Opens a new town session with the specified city. Suspends this WorldController
	 * and its attached gui and the program's control flow passes to the
	 * TownController that this method makes.
	 * @param city: the city that you will be viewing through the TownController.
	 */
	public void startTownView(City city){
		suspend();
		this.townController = new TownController(city,this);
	}

	/**
	 * Awaken this controller. Its attached gui will now draw information and respond
	 * to events.
	 */
	public void awake(){
		gui.awake();
		this.active = true;
	}

	/**
	 * Make this controller inactive until awakened. Its attached gui will also become
	 * unresponsive.
	 */
	public void suspend(){
		this.active = false;
		gui.suspend();
	}

	/**
	 * Return the world being controlled by this controller.
	 * @return: world
	 */
	public World getWorld(){
		return world;
	}

	/**
	 * Return the viewing camera of this controller.
	 * @return: camera
	 */
	public Camera getCamera(){
		return camera;
	}

	/**
	 * Returns the player this controller belongs to.
	 * @return: player
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Return the tile that is currently selected by the player attached to this controller.
	 * @return: a tile, or null if there is no tile selected.
	 */
	public Tile getSelectedTile(){
		return world.getTile(selected);
	}

	public void notifier(){

		if(client!=null)client.notifyThread();
		if(client == null)System.out.println("still not initiated");
	}

	public static void main(String[] args) throws IOException{
		aaron_main(args);
	}

	public static void aaron_main_2(String[] args) throws IOException{
		World world = WorldLoader.load(Constants.DATA_WORLDS + "test_save.xml");
		new WorldController(world,world.getPlayers()[0]);
	}

	public static void myles_main(String[] dun_goofed) {


	}

	public static void aaron_main(String[] args){
		/*Loading items*/
		Buff[] buffsAmulet = new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,5) };
		PassiveItem amulet = new PassiveItem("amulet", "amulet", "An amulet that grants sickening gains.\n +5 Damage",buffsAmulet,Target.HERO, "liontalisman.xml");

		Buff[] buffsWeapon = new Buff[]{ Buff.newPermaBuff(Stat.DAMAGE,5), Buff.newTempBuff(Stat.ARMOUR, 10) };
		PassiveItem weapon = new PassiveItem("weapon", "weapon", "A powerful weapon crafted by the mighty Mizza +5 Damage",buffsWeapon,Target.HERO,null);

		Buff[] buffsArrows= new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,1) };
		PassiveItem arrows = new PassiveItem("poisonarrow", "poisonarrow", "Poisonous arrows whose feathers were made from the hairs of Mizza. All archers in party gain +1 damage",buffsArrows, Target.PARTY,null);



		ItemIcon itemIcon = new ItemIcon(amulet);

		ItemIcon itemIcon2 = new ItemIcon(weapon);

		ItemIcon itemIcon3 = new ItemIcon(arrows);


		/*Loading the playey*/
		Player p = new Player("John The Baptist",1);
		World w = TemporaryLoader.loadWorld("world_temporary.txt",p);
		HeroStats stats_hero = new HeroStats(60,10,80,0,6,10,AttackType.MELEE);
		Hero hero = new Hero("ovelia","ovelia",p,stats_hero);

		/*
		Creature[][] members = Party.newEmptyParty();
		members[0][0] = hero;
		Party party = new Party(hero, p, members);
		party.refresh();*/

		/*load a party*/
		//Hero h2 = new Hero("dark_knight","knight",p,new HeroStats(140,35,55,5,8,6,AttackType.MELEE));
		Unit u3 = new Unit("knight","knight",p,new UnitStats(100,25,40,1,AttackType.MELEE));
		Unit u4 = new Unit("archer","archer",p,new UnitStats(60,15,70,0,AttackType.RANGED));
		Unit u5 = new Unit("archer","archer",p,new UnitStats(60,15,70,0,AttackType.RANGED));
		Unit u6 = new Unit("knight","knight",p,new UnitStats(100,25,40,1,AttackType.MELEE));
		Creature[][] members2 = Party.newEmptyPartyArray();
		members2[0][0] = u3;
		members2[1][0] = u6;
		members2[1][2] = hero;
		members2[0][1] = u4;
		members2[1][1] = u5;
		Party party = new Party(hero,p,members2);
		party.refresh();

		party.addItem(arrows);
		w.getTile(0,0).setIcon(party);
		w.getTile(1,1).setIcon(itemIcon); //place a floor item on this tile
		w.getTile(1,2).setIcon(itemIcon2);
		w.getTile(1,3).setIcon(itemIcon2);
		w.getTile(1,4).setIcon(itemIcon);
		w.getTile(1,6).setIcon(itemIcon2);
		w.getTile(8,8).setIcon(itemIcon3);

		new WorldController(w,p);
	}

	public static void ewan_main(String[] args){
		/*Loading items*/
		Buff[] buffsAmulet = new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,5) };
		PassiveItem amulet = new PassiveItem("amulet", "amulet", "An amulet that grants sickening gains.\n +5 Damage",buffsAmulet,Target.HERO, "liontalisman.xml");

		Buff[] buffsWeapon = new Buff[]{ Buff.newPermaBuff(Stat.DAMAGE,5), Buff.newTempBuff(Stat.ARMOUR, 10) };
		PassiveItem weapon = new PassiveItem("weapon", "weapon", "A powerful weapon crafted by the mighty Mizza +5 Damage",buffsWeapon,Target.HERO, null);

		Buff[] buffsArrows= new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,1) };
		PassiveItem arrows = new PassiveItem("poisonarrow", "poisonarrow", "Poisonous arrows whose feathers were made from the hairs of Mizza. All archers in party gain +1 damage",buffsArrows, Target.PARTY, null);


		ItemIcon amuletChest = new ItemIcon(amulet);

		ItemIcon weaponChest = new ItemIcon(weapon);

		ItemIcon arrowsChest = new ItemIcon(arrows);


		/*Loading the playey*/
		Player p = new Player("John The Baptist",1);
		World w = ewan_world();
		HeroStats stats_hero = new HeroStats(60,10,80,0,6,10,AttackType.MELEE);
		Hero hero = new Hero("ovelia","ovelia",p,stats_hero);

		/*load the units into the party*/
		Unit u3 = new Unit("knight","knight",p,new UnitStats(100,25,40,1,AttackType.MELEE));
		Unit u4 = new Unit("archer","archer",p,new UnitStats(60,15,70,0,AttackType.RANGED));
		Unit u5 = new Unit("archer","archer",p,new UnitStats(60,15,70,0,AttackType.RANGED));
		Unit u6 = new Unit("knight","knight",p,new UnitStats(100,25,40,1,AttackType.MELEE));
		Creature[][] members2 = Party.newEmptyPartyArray();
		members2[0][0] = u3;
		members2[1][0] = u6;
		members2[2][0] = hero;
		members2[0][1] = u4;
		members2[2][1] = u5;
		Party party = new Party(hero,p,members2);
		party.refresh();
		party.addItem(arrows);



		Tile[][] tiles = w.getTiles();
		for (int y = 1; y < 10; y++){
			for (int x = 1; x < 10; x++){
				double rand = Math.floor((Math.random() * 20) + 1);
				if(rand > 18 && (!(w.getTile(x,y) instanceof ImpassableTile))) {
					w.getTile(x,y).setIcon(weaponChest);
				}
				else if(rand > 17 && (!(w.getTile(x,y) instanceof ImpassableTile))) {
					w.getTile(x,y).setIcon(amuletChest);
				}
				else if(rand > 16 && (!(w.getTile(x,y) instanceof ImpassableTile))) {
					w.getTile(x,y).setIcon(arrowsChest);
				}
				else {

				}
			}
		}


		/*Lay the items down on the tiles*/
		w.getTile(0,0).setIcon(party);
		/**w.getTile(1,1).setIcon(itemIcon); //place a floor item on this tile
		w.getTile(1,2).setIcon(itemIcon2);
		w.getTile(1,3).setIcon(itemIcon2);
		w.getTile(1,4).setIcon(itemIcon);
		w.getTile(1,6).setIcon(itemIcon2);
		w.getTile(8,8).setIcon(itemIcon3);*/

		new WorldController(w,p);
	}





	/**
	 * This is a test for creating a world with custom tiles.
	 * @return World
	 */
	public static World ewan_world() {
		Player p = new Player("John The Baptist",4);



		// create tiles
		Tile[][] tiles = new Tile[10][10];
		for (int y = 0; y < 10; y++){
			for (int x = 0; x < 10; x++){
				double rand = Math.floor((Math.random() * 50) + 1);
				if(rand > 48) {
					tiles[x][y] = new ImpassableTile("tree",8,9);
				}
				else if(rand > 45) {
					tiles[x][y] = new ImpassableTile("bigTree",8,9);
				}
				else if(rand > 40 ) {
					tiles[x][y] = PassableTile.newDirtTile(x,y);
				}
				else {
					tiles[x][y] = PassableTile.newGrassTile(x,y);
				}
			}
		}

		// add a city
		CityTile[][] cityTiles = new CityTile[City.WIDTH][City.WIDTH];

		for (int i=4, a=0; i <= 6; i++, a++){
			for (int j=4, b=0; j <= 6; j++, b++){
				CityTile ct = new CityTile(i,j);
				tiles[i][j] = ct;
				cityTiles[a][b] = ct;
			}
		}

		City city = new City("Porirua","basic", p, cityTiles);
		Player[] players = new Player[]{ p };
		Set<City> cities = new HashSet<>();
		cities.add(city);
		return new World(tiles,players,cities);
	}

}

