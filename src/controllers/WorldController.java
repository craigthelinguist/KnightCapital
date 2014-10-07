package controllers;

import game.units.Hero;

import java.awt.Point;
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
import tools.Geometry;
import tools.GlobalConstants;
import world.Party;
import world.Tile;
import world.World;
import world.WorldIcon;
import GUI.MainFrame;

/**
 * A WorldController. This is the glue between the model (World) and the view (gui, renderer).
 * @author Aaron
 */
public class WorldController {

	//boolean server or client, true if server, false if client.
	private boolean serverOrClient = true;

	//server and client.
	private Server server;
	private Client client;

	// gui and renderer: the view
	private MainFrame gui;

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
	}

	/**
	 * Player has clicked on something.
	 * @param me: details about the click.
	 * @param panel: what they clicked on (inventory, world, etc.)
	 */
	public void mousePressed(MouseEvent me, String panel){

		if (panel.equals("canvas")){

			Point ptIso = new Point(me.getX(),me.getY());
			Point ptCartesian = Geometry.isometricToCartesian(ptIso, camera, world.dimensions);
			Tile clickedTile = world.getTile(ptCartesian);
			Tile selectedTile = world.getTile(selected);

			// selected the tile
			if (selectedTile != clickedTile && SwingUtilities.isLeftMouseButton(me)){
				selected = ptCartesian;
				highlightTiles(clickedTile);
				gui.updateInfo(clickedTile);
				gui.redraw();
			}

			// deselected the tile
			else if (selected != null && SwingUtilities.isLeftMouseButton(me)){
				if (selectedTile == clickedTile) selected = null;
				resetHighlightedTiles();
				gui.updateInfo(null);
				gui.redraw();
			}

			// moved
			else if (selected != null && SwingUtilities.isRightMouseButton(me)){
				boolean moved = world.moveParty(player, selected, ptCartesian);
				if (moved){
					selected = ptCartesian;
					highlightTiles(clickedTile);
					gui.updateInfo(clickedTile);
					gui.redraw();
				}
			}

		}


	}

	/**
	 * Player has clicked a button.
	 * @param button: the button they clicked.
	 */
	public void buttonPressed(JButton button){

	}

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

	/**
	 * For testing purposes
	 * @param args
	 */
	public static void main(String[] args){
		World w = TemporaryLoader.loadWorld("world_temporary.txt");
		Player p = new Player();
		Party party = new Party(GlobalConstants.ICONS+"ovelia", p);
		Hero hero = new Hero();
		hero.setMovePts(10);
		party.setLeader(hero);
		party.refreshMovePoints();
		w.getTile(0,0).setIcon(party);
		new WorldController(w,p);
	}

}
