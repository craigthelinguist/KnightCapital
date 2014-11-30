package controllers;

import game.units.AttackType;
import game.units.Creature;
import game.units.Hero;
import game.units.HeroStats;
import game.units.Unit;
import game.units.UnitStats;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

import player.Player;
import renderer.Camera;
import renderer.WorldRenderer;
import storage.generators.TemporaryLoader;
import tools.Geometry;
import world.World;
import world.icons.Party;
import world.icons.WorldIcon;
import world.tiles.Tile;
import GUI.world.MainFrame;

/**
 * Battle
 * This is created whenever a player right clicks on an enemy who is within their move limit.
 *
 * Battle is turn based, the player with the unit with the highest speed goes first, and then the next fastest
 * regardless of which party it's in.
 *
 * 		Rules
 * 		-Melee units can only attack the front line until it is non-existant.
 * 		-Ranged units can attack anyone.
 * 		-AOE units attack everyone.
 * 		-Battles continue to the death
 * 		-No rush 20 min
 *
 */
public class BattleController{

	// gui shit
	private MainFrame frame;

	// world facade for battle
	private World world;

	// Our two battling partys
	private Party party1;
	private Party party2;

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

	// key bindings
	private static final int ROTATE_CW = KeyEvent.VK_R;
	private static final int ROTATE_CCW = KeyEvent.VK_E;
	private static final int PAN_UP = KeyEvent.VK_UP;
	private static final int PAN_DOWN = KeyEvent.VK_DOWN;
	private static final int PAN_RIGHT = KeyEvent.VK_RIGHT;
	private static final int PAN_LEFT = KeyEvent.VK_LEFT;

	/**
	 * Constructs a new battle controller for a new battle instance.
	 * @param party 1 & 2 : the two party about to battle.
	 * @param
	 */
	public BattleController(World w, MainFrame mf, Party p1, Party p2) {
		// set partys
		party1 = p1;
		party2 = p2;

		world = w; // set world


		camera = WorldRenderer.getCentreOfWorld(w);
		frame = mf;
		frame.setController(this);

		selected = null;
		highlightedTiles = new HashSet<>();

	}

	private void run() {

		// Main Loop for battle. Will run until one party is defeated
		while(!party1.isDead() || !party2.isDead()) {
			// Determine next creatures turn.

			// Take turn for that creature

			// update battle renderer
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
			world.rotateOccupants(true);
			frame.redraw();
		}
		else if (code == ROTATE_CCW){
			camera.rotateCounterClockwise();
			world.rotateOccupants(false);
			frame.redraw();
		}
		else if (code == PAN_UP){
			camera.panUp();
			frame.redraw();
		}
		else if (code == PAN_DOWN){
			camera.panDown();
			frame.redraw();
		}
		else if (code == PAN_RIGHT){
			camera.panRight();
			frame.redraw();
		}
		else if (code == PAN_LEFT){
			camera.panLeft();
			frame.redraw();
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


		// deselected the tile
		if (selected != null && SwingUtilities.isLeftMouseButton(me) && selectedTile == clickedTile){
			System.out.println("deselect");
			deselect();
			frame.updateInfo(null);
			frame.redraw();
			this.lastMouse = System.currentTimeMillis();
		}

		// selected the tile
		else if (selectedTile != clickedTile && SwingUtilities.isLeftMouseButton(me)){
			selected = ptCartesian;
			highlightTiles(clickedTile);
			frame.updateInfo(clickedTile);
			frame.redraw();
			this.lastMouse = System.currentTimeMillis();
		}

		// moved
		else if (selected != null && SwingUtilities.isRightMouseButton(me)){

			boolean moved = world.moveParty(player, selected, ptCartesian);
			if (moved){
				selected = ptCartesian;
				highlightTiles(clickedTile);
				frame.updateInfo(clickedTile);
				frame.redraw();
				this.lastMouse = System.currentTimeMillis();
			}
		}

	}


	/**
	 * The mouse has moved from lastDrag -> point.
	 * @param lastDrag: point mouse started at
	 * @param point: point mouse moved to.
	 */
	public void mouseDragged(Point lastDrag, Point point) {
		int x = point.x - lastDrag.x;
		int y = point.y - lastDrag.y;
		camera.pan(x,y);
		frame.redraw();
	}

	/**
	 * Respond to a mouse moved event.
	 * @param me: the event.
	 */
	public void mouseMoved(MouseEvent me){

		Point ptIso = new Point(me.getX(),me.getY());
		Point ptCartesian = Geometry.isometricToCartesian(ptIso, camera, world.dimensions);
		Tile tileHover = world.getTile(ptCartesian);
		if (tileHover == null) this.hover = ptCartesian;
		else this.hover = null;

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
	 * Return true if this point is being highlighted by the world controller
	 * @param p: a point in Cartesian space
	 * @return: true if this point is being highlighted by the world controller
	 */
	public boolean isHighlighted(Point p){
		return highlightedTiles.contains(p);
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
	 * Get the world attached to this controller.
	 * @return: world
	 */
	public World getWorld(){
		return world;
	}

	/**
	 * Get the current viewing perspective of this controller.
	 * @return: camera
	 */
	public Camera getCamera(){
		return camera;
	}

	/**
	 * Get the currently selected tile, if it exists.
	 * @return: currenty selected tile, or null if there isn't one.
	 */
	public Tile getSelectedTile(){
		return world.getTile(selected);
	}

	/**
	 * Get the width of the gui dimensions.
	 * @return: dimension object
	 */
	public Dimension getVisualDimensions() {
		if (frame == null) return Toolkit.getDefaultToolkit().getScreenSize();
		return frame.getSize();
	}

}
