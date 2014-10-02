package controllers;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import GUI.MainFrame;

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

/**
 * A WorldController. This is the glue between the model (World) and the view (gui, renderer).
 * @author craigthelinguist
 */
public class WorldController {

	// gui and renderer: the view
	private MainFrame gui;
	
	// world this controller is for: the model
	private final World world;

	// player this controller belongs to
	private Player player;
	
	// current tile and perspective
	private Point selected;
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
	}
	
	/**
	 * Player has pushed a key
	 * @param ke: details about the key event
	 */
	public void keyPressed(KeyEvent ke){
		
		int code = ke.getKeyCode();
		
		if (code == ROTATE_CW){
			camera.rotateClockwise();
		}
		else if (code == ROTATE_CCW){
			camera.rotateCounterClockwise();
		}
		else if (code == PAN_UP){
			camera.panUp();
		}
		else if (code == PAN_DOWN){
			camera.panDown();
		}
		else if (code == PAN_RIGHT){
			camera.panRight();
		}
		else if (code == PAN_LEFT){
			camera.panLeft();
		}
		gui.redraw();
		
	}

	/**
	 * Player has clicked on something.
	 * @param me: details about the click.
	 * @param panel: what they clicked on (inventory, world, etc.)
	 */
	public void mousePressed(MouseEvent me, String panel){
		
		if (panel.equals("canvas")){
		

			Point ptIso = new Point(me.getX(),me.getY());
			Point ptRotated = Geometry.isometricToCartesian(ptIso, camera);
			Point ptOriginal = Geometry.recoverOriginalPoint(ptRotated, camera, world);
			
			Tile clickedTile = world.getTile(ptOriginal);
			Tile selectedTile = world.getTile(selected);
			
			// selected the tile
			if (selectedTile != clickedTile && SwingUtilities.isLeftMouseButton(me)){
				selected = ptOriginal;
				gui.updateInfo(clickedTile);
			}
			
			// deselected the tile
			else if (selected != null && SwingUtilities.isLeftMouseButton(me)){
				if (selectedTile == clickedTile) selected = null;
				gui.updateInfo(null);
			}
		
			// moved
			else if (selected != null && SwingUtilities.isRightMouseButton(me)){
				boolean moved = world.moveParty(player, selected, ptOriginal);
				if (moved) gui.updateInfo(clickedTile);
			}
		
		}
		
		gui.redraw();
		
	}
	
	/**
	 * Player has clicked a button.
	 * @param button: the button they clicked.
	 */
	public void buttonPressed(JButton button){
		
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
	
	public static void main(String[] args){
		World w = TemporaryLoader.loadWorld("world_temporary.txt");
		Player p = new Player();
		Party party = new Party("icon_ovelia.png",p);
		w.getTile(0,0).setIcon(party);
		new WorldController(w,p);
	}
	
}
