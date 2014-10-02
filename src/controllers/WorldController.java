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
		
		
		
	}

	/**
	 * Player has clicked on something.
	 * @param me: details about the click.
	 * @param panel: what they clicked on (inventory, world, etc.)
	 */
	public void mousePressed(MouseEvent me, String panel){
		
		if (panel.equals("canvas")){
		
			Point pointIso = new Point(me.getX(),me.getY());
			Point pointCartesian = Geometry.isometricToCartesian(pointIso, camera);
			Tile clickedTile = world.getTile(pointCartesian);
		
			// selected the tile
			if (selected == null && SwingUtilities.isLeftMouseButton(me)){
				selected = pointCartesian;
				gui.updateInfo(clickedTile);
			}
			
			// deselected the tile
			else if (selected != null && SwingUtilities.isLeftMouseButton(me)){
				Tile selectedTile = world.getTile(selected);
				if (selectedTile == clickedTile) selected = null;
				gui.updateInfo(null);
			}
		
			// moved
			else if (selected != null && SwingUtilities.isRightMouseButton(me)){
				boolean moved = world.moveParty(player, selected, pointCartesian);
				if (moved) gui.updateInfo(clickedTile);
			}
		
		}
		
		System.out.println(selected);
		
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
	
	public static void main(String[] args){
		World w = TemporaryLoader.loadWorld("world_temporary.txt");
		Player p = new Player();
		new WorldController(w,p);
	}
	
}
