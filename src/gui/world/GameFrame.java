package gui.world;

import gui.world.Canvas;
import gui.world.GameDialog;

import java.awt.BorderLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import player.Player;
import world.tiles.Tile;
import controllers.WorldController;

public class GameFrame extends JFrame{
	
	// state
	private boolean closeDialogEnabled = false;
	private boolean active = true;
	
	// components
	private Canvas canvas;
	private GamePanel gamePanel;
	private MenuPanel menuPanel;

	// controller
	private WorldController controller;

	public GameFrame(){
		
		// set up layout and display
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.setUndecorated(true); //true means borderless window
		this.setLayout(new BorderLayout());
		
		// set up components
		canvas = new Canvas(this);
		this.add(canvas, BorderLayout.CENTER);
		gamePanel = new GamePanel(this);
		this.add(gamePanel, BorderLayout.SOUTH);
		menuPanel = new MenuPanel(this);
		this.add(menuPanel, BorderLayout.NORTH);
		
		// set up key dispatcher
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new WorldKeyDispatcher());

		// finish up
		this.setResizable(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Return the canvas attached to this GameFrame.
	 * @return GameCanvas object.
	 */
	public Canvas getCanvas(){
		return this.canvas;
	}
	
	/**
	 * Set the controller for this GameFrame.
	 * @throws RuntimeException: if this GameFrame already has a controller.
	 * @param wc: controller to attach to this view.
	 */
	public void setController(WorldController wc){
		if (this.controller != null) throw new RuntimeException("setting controller for MainFrame");
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
	
	/**
	 * Disable close dialog. While close dialog is disabled, the only key events that should fire are those
	 * in MainFrame.
	 */
	public void disableCloseDialog(){
		this.closeDialogEnabled = false;
	}

	/**
	 * Update InfoPanel to display information about the specified tile and its contents.
	 * @param tile: whose info you'll display
	 */
	public void updateInfo(Tile tile){
		gamePanel.updateInfo(tile);
	}
	
	/**
	 * All button presses should be sent up to the controller. Nothing happens if CloseDialog is enabled.
	 * @param button: name of the button event that fired
	 */
	protected void buttonPressed(String button) {
		if (closeDialogEnabled) return;
		controller.buttonPressed(button);
	}
	

	/**
	 * Create a new game dialog attached to this frame.
	 * @param msg: the message to display.
	 */
	public void makeGameDialog(String msg) {
		new GameDialog(this,msg);
	}

	/**
	 * Return the player to whom this view belongs.
	 * @return Player.
	 */
	public Player getPlayer(){
		return controller.getPlayer();
	}
	
	/**
	 * Deactive this MainFrame and make it invisible.
	 */
	public void suspend() {
		this.setVisible(false);
		this.active = false;
	}

	/**
	 * Activate this MainFrame and make it visible.
	 */
	public void awake(){
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.active = true;
		this.closeDialogEnabled = false;
		this.setVisible(true);
	}

	
	private class WorldKeyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {

			System.out.println("key press");


			if (closeDialogEnabled || !active){
				return false;
			}


			if (e.getID() == KeyEvent.KEY_PRESSED){
				controller.keyPressed(e);
			}

			return false;
		}
	}


	public void redraw() {
		canvas.repaint();
		// TODO Auto-generated method stub
		
	}
	
}
