package GUI;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import GUI.EscapeDialog.EscapeDialog;
import controllers.WorldController;
import tools.GlobalConstants;
import tools.ImageLoader;
import world.tiles.Tile;

/**
 * This is the main game frame. Contains a canvas and a JLayeredPanel at the bottom of the frame.
 * Everything is drawn to the canvas. //TODO (write some more info here later)
 *
 * @author Ewan Moshi
 *
 */

public class MainFrame extends JFrame  {


	private LayeredPanel layeredPanel;
	private Canvas canvas;
	private WorldController controller;
	private boolean closeDialogEnabled = false;

	public MainFrame() {
		/* These two statements make the frame full screen. (Commented out for now) */
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.setUndecorated(false); //true means borderless window


		//this.setSize(1300,200);
		this.setLayout(new BorderLayout());
		//this.setVisible(true);

		//Initialize the canvas and add it in the center
		canvas = new Canvas();
		this.add(canvas,BorderLayout.CENTER);

		//Initialize the layered panel and add it to the south
		layeredPanel = new LayeredPanel(this);
		this.add(layeredPanel,BorderLayout.SOUTH);

		// set up key dispatcher
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new WorldKeyDispatcher());


		//setupSlots();
		this.setResizable(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void setController(WorldController wc){
		controller = wc;
		canvas.setController(wc);
	}

	/**
	 * Enable close dialog. While close dialog is enabled, the only key events that should fire are those in the
	 * close dialog. 
	 */
	public void enableCloseDialog(){
		this.closeDialogEnabled = true;
	}

	public void disableCloseDialog(){
		this.closeDialogEnabled = false;
	}

	/**
	 * Update what displays in the layered panel to reflect the information
	 * in the supplied tile.
	 * @author Aaron
	 * @param tile: whose info you'll display
	 */
	public void updateInfo(Tile tile){
		layeredPanel.updateInfo(tile);
	}

	
	/**
	 * All button presses should be sent up to the controller.
	 * @author Aaron
	 * @param b2: the button that fired an event.
	 */
	protected void buttonPressed(JButton b2) {
		if (closeDialogEnabled) return;
		controller.buttonPressed(b2);
	}

	/**
	public void redraw(){
		canvas.repaint();
	}
	 * All key presses should be sent up to the controller.
	 * @author Aaron
	 * @author Dennis Ritchie :^)
	 */
	private class WorldKeyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (closeDialogEnabled) return false;

			if (e.getID() == KeyEvent.KEY_RELEASED)
			{
				controller.keyPressed(e);
			}
		    if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		    {
				EscapeDialog dialog = new EscapeDialog(MainFrame.this);
		    }
			return false;
		}
	}

	public void redraw(){
		canvas.repaint();
	}

	/**public ImageIcon createInventoryIcon(String name) {
		  try {
			    ImageIcon invImg = ImageIO.read(getClass().getResource("assets/GUIAssets/" +name+".png"));
			    return invImg;

			  } catch (IOException ex) {
			  }
		    return null;
	}*/

	  /**public void setupSlots() {
		  slotFrame = new JFrame();
		  slotFrame.setLayout(null);

	        slot1 = new ItemSlotInformation("sdfsdfsdfsdfsdfsdfsd");
	        slot1.setVisible(true);
	        slot1.setLocation(50,50);
	        slotFrame.add(slot1);

	        slot2 = new ItemSlotInformation("sdfsdfsdfsdfsdfsdfsd");
	        slot2.setVisible(true);
	        slot2.setLocation(100,50);
	        slotFrame.add(slot2);

	        slot3 = new ItemSlotInformation("sdfsdfsdfsdfsdfsdfsd");
	        slot3.setVisible(true);
	        slot3.setLocation(150,50);
	        slotFrame.add(slot3);

	        slot4 = new ItemSlotInformation("sdfsdfsdfsdfsdfsdfsd");
	        slot4.setVisible(true);
	        this.add(slot4);

	        slot5 = new ItemSlotInformation("123");
	        slot5.setVisible(true);
	        this.add(slot5);

	        slot6 = new ItemSlotInformation("hello");
	        slot6.setVisible(true);
	        slot6.setLocation(500,50);
	        this.add(slot6);

	        slotFrame.setResizable(true);
			//this.pack();
			slotFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			slotFrame.setVisible(true);
			slotFrame.pack();

	  }

	  public void slotVisibibleTrue(int i) {
		  switch(i) {
		  	case 1:
		  		slot1.setVisible(true);
		  		break;
	  		case 2:
	  			slot2.setVisible(true);
	  			break;
			case 3:
				slot3.setVisible(true);
				break;
			case 4:
				slot4.setVisible(true);
				break;
			case 5:
				slot5.setVisible(true);
				break;
			case 6:
				slot6.setVisible(true);
				break;
			default:
				break;
		  }
	  }

	  public void slotVisibibleFalse(int i) {
		  switch(i) {
		  	case 1:
		  		slot1.setVisible(false);
		  		break;
	  		case 2:
	  			slot2.setVisible(false);
	  			break;
			case 3:
				slot3.setVisible(false);
				break;
			case 4:
				slot4.setVisible(false);
				break;
			case 5:
				slot5.setVisible(false);
				break;
			case 6:
				slot6.setVisible(false);
				break;
			default:
				break;
		  }
	  }*/

	/* Main method to test the MainFrame */
	public static void main(String[] args) {
		new MainFrame();
	}

	/**
	 * Create a new game dialog attached to this frame.
	 * @param msg: the message to display.
	 */
	public void makeGameDialog(String msg) {
		new GameDialog(this,msg);
	}


}
