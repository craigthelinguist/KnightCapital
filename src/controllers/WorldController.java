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
		
		if (panel.equals("world")){
			Point pointIso = new Point(me.getX(),me.getY());
			Point pointCartesian = Geometry.isometricToCartesian(pointIso, camera);
			Tile clickedTile = world.getTile(pointCartesian.x, pointCartesian.y);
			if (selected == null){
				
				// player left-clicked on a tile
				if (SwingUtilities.isLeftMouseButton(me)){
					selected = pointCartesian;					
				}
				
				// TODO: send message to GUI telling it to display info about that tile
				
			}
			else{
				
				// deselect
				if (pointCartesian == selected){
					selected = null;
				}
				// move if they right-clicked
				else if (SwingUtilities.isRightMouseButton(me)){
					Tile selectedTile = world.getTile(selected.x, selected.y);
					if (world.moveParty(player,selected,pointCartesian)){
						// check if the move was successful
					}
				
				
				}
			}
			
			
		}
		else if (panel.equals("inventory")){
			
		}
		else if (panel.equals("minimap")){
		}
		
	}
	
	/**
	 * Player has clicked a button.
	 * @param button: the button they clicked.
	 */
	public void buttonPressed(JButton button){
		
	}
	
	public static void main(String[] args){
		MainFrame mf = new MainFrame();
		World w = TemporaryLoader.loadWorld("world_temporary.txt");
		Player p = new Player();
		Camera cam = new Camera(GlobalConstants.WINDOW_WD / 2, 0, Camera.NORTH);
		new WorldController(w,p);
	}
	
}
