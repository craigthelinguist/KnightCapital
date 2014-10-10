
package controllers;

import game.effects.Buff;
import game.items.FloorItem;
import game.items.Item;
import game.items.PassiveItem;
import game.units.Creature;
import game.units.Hero;
import game.units.Stat;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import networking.Client;
import networking.Server;
import player.Player;
import renderer.Camera;
import renderer.WorldRenderer;
import storage.TemporaryLoader;
import storage.XMLReader;
import tools.Geometry;
import tools.Constants;
import world.World;
import world.icons.ItemIcon;
import world.icons.Party;
import world.icons.WorldIcon;
import world.tiles.CityTile;
import world.tiles.Tile;
import world.towns.City;
import GUI.MainFrame;
import GUI.PartyDialog.PartyDialog;

/**
 * A WorldController. This is the glue between the model (World) and the view (gui, renderer).
 * @author Aaron
 */
public class WorldController{

	//boolean server or client, true if server, false if client.
	private boolean serverOrClient = true;

	//server and client.
	private Server server;
	private Client client;

	// gui and renderer: the view
	private MainFrame gui;

	// town controller when you have town view up
	private TownController townController;

	// world this controller is for: the model
	private final World world;

	// player this controller belongs to
	private Player player;

	// current tile the player has clicked
	private Point selected;

	// highlighted tiles that are showing where the player's selected party can move to
	private Set<Point> highlightedTiles;

	// the camera that the player is viewing from
	private Camera camera;

	// whether this worldController is currently doing anything
	private boolean active = true;
	private long lastMouse = 0;

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

		if(serverOrClient){
			try {
				server = new Server();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {

			client = new Client();

		}


	}

	/**
	 * Player has pushed a key
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
		else if(code == KeyEvent.VK_I){
			System.out.println("xxx");

			PartyDialog partyDialog = new PartyDialog(this.gui, world.getTile(selected));;




		}
	}

	/**
	 * Player has clicked on something.
	 * @param me: details about the click.
	 * @param panel: what they clicked on (inventory, world, etc.)
	 */
	public void mousePressed(MouseEvent me){

			Point ptIso = new Point(me.getX(),me.getY());
			Point ptCartesian = Geometry.isometricToCartesian(ptIso, camera, world.dimensions);
			Tile clickedTile = world.getTile(ptCartesian);
			Tile selectedTile = world.getTile(selected);

			// double clicked a city
			if (SwingUtilities.isLeftMouseButton(me) && selectedTile != null
				&& clickedTile instanceof CityTile && selectedTile instanceof CityTile
				 && System.currentTimeMillis() - this.lastMouse < 700){
				{
					CityTile c1 = (CityTile)clickedTile;
					CityTile c2 = (CityTile)selectedTile;
					if (c1.getCity() == c2.getCity()){
						startTownView(c1.getCity());
					}
					this.lastMouse = System.currentTimeMillis();
				}
			}

			// deselected the tile
			else if (selected != null && SwingUtilities.isLeftMouseButton(me) && selectedTile == clickedTile){
				System.out.println("deselect");
				deselect();
				gui.updateInfo(null);
				gui.redraw();
				this.lastMouse = System.currentTimeMillis();
			}

			// selected the tile
			else if (selectedTile != clickedTile && SwingUtilities.isLeftMouseButton(me)){
				selected = ptCartesian;
				highlightTiles(clickedTile);
				gui.updateInfo(clickedTile);
				gui.redraw();
				this.lastMouse = System.currentTimeMillis();
			}

			// moved
			else if (selected != null && SwingUtilities.isRightMouseButton(me)){
				boolean moved = world.moveParty(player, selected, ptCartesian);
				if (moved){
					selected = ptCartesian;
					highlightTiles(clickedTile);
					gui.updateInfo(clickedTile);
					gui.redraw();
					this.lastMouse = System.currentTimeMillis();
				}
			}

	}


	public void mouseMoved(Point lastDrag, Point point) {

		int x = point.x - lastDrag.x;
		int y = point.y - lastDrag.y;

		int cameraPan = 10;
		camera.pan(x,y);

		gui.redraw();

		// TODO Auto-generated method stub

	}

	/**
	 * Player has clicked a button.
	 * @param button: the button they clicked.
	 */
	public void buttonPressed(JButton button){

		if (button.getText().equals("End Turn Placeholder")){
			world.endTurn();
			deselect();
			gui.updateInfo(null);
			gui.redraw();
			gui.makeGameDialog("Day " + world.getDay());
		}

	}

	/**
	 * Deselect the current tile. Un-highlight everything.
	 */
	private void deselect(){
		selected = null;
		resetHighlightedTiles();
	}

	/**
	 * Resets the set of highlighted tiles.
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
	 * Return true if this point is being highlighted by the world controller
	 * @param p: a point in Cartesian space
	 * @return: true if this point is being highlighted by the world controller
	 */
	public boolean isHighlighted(Point p){
		return highlightedTiles.contains(p);
	}

	public void endTownView(){
		awake();
		this.townController = null;
		deselect();
		gui.updateInfo(null);
		gui.redraw();
	}

	public void startTownView(City city){
		suspend();
		this.townController = new TownController(city,this);
	}

	public void awake(){
		gui.awake();
		this.active = true;
	}

	public void suspend(){
		this.active = false;
		gui.suspend();
	}

	public World getWorld(){
		return world;
	}

	public Camera getCamera(){
		return camera;
	}

	public Tile getSelectedTile(){
		return world.getTile(selected);
	}

	public void notifier(){

		if(client!=null)client.notifyThread();
		if(client == null)System.out.println("still not initiated");
	}

	public Dimension getVisualDimensions() {
		if (gui == null) return Toolkit.getDefaultToolkit().getScreenSize();
		return gui.getSize();
	}


	public static void main(String[] args){
		aaron_main(args);
	}

	public static void aaron_main(String[] args){
		/*Loading items*/
		Buff[] buffs = new Buff[]{ new Buff(Stat.DAMAGE,1,true) };
		PassiveItem amulet = new PassiveItem(buffs, "amulet","An amulet that grants sickening gains.\n +5 Damage");
		ItemIcon floorItem = new ItemIcon("Uknown Item", amulet);

		/*Loading the playey*/
		Player p = new Player("John The Baptist",4);
		World w = TemporaryLoader.loadWorld("world_temporary.txt",p);
		Hero hero = new Hero("ovelia",p);
		hero.setMovePts(10);
		Creature[][] members = Party.newEmptyParty();
		members[0][0] = hero;
		Party party = new Party(hero, p, members);
		party.refresh();
		w.getTile(0,0).setIcon(party);

		w.getTile(1,1).setIcon(floorItem); //place a floor item on this tile

		new WorldController(w,p);
	}


	public static void selemon_main(){
		Player p = new Player("John The Baptist",4);
		World w = TemporaryLoader.loadWorld("world_temporary.txt",p);
		Hero hero = new Hero("ovelia",p);

		Creature[][] members = Party.newEmptyParty();
		members[0][0] = hero;
		Party party = new Party(hero, p, members);

		//hero.setMovePts(10);
		XMLReader read = new XMLReader(Constants.ASSETS+"Levels.xml", "levelTwo", hero);
		read.readLevel();
		System.out.println(hero.getMovePoints());

		party.setLeader(hero);
		party.refresh();
		hero.setMovePts(8);
		w.getTile(0,0).setIcon(party);
		new WorldController(w,p);
	}

	/**
	 * When u don't want a gui use this for testing purposes only
	 */
	@Deprecated
	private WorldController(World w, Player p, boolean af){
		world = w;
		player = p;
		camera = WorldRenderer.getCentreOfWorld(w);
		selected = null;
		highlightedTiles = new HashSet<>();
	}

	/**
	 * To test other classes, if they need a WorldController.
	 * @return
	 */
	public static WorldController getTestWorldControllerNoGui(){
		Player p = new Player("John The Baptist",4);
		World w = TemporaryLoader.loadWorld("world_temporary.txt",p);
		Hero hero = new Hero("ovelia",p);
		Creature[][] members = Party.newEmptyParty();
		members[0][0] = hero;
		Party party = new Party(hero, p, members);
		hero.setMovePts(10);
		return new WorldController(w,p,true);

	}

}

