
package gui.world;

import gui.escape.EscapeDialog;
import gui.world.Canvas;
import gui.world.GameDialog;

import java.awt.BorderLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.GameWindow;
import player.Player;
import world.tiles.Tile;
import controllers.WorldController;

public class WorldPanel extends JPanel{
	
	// current escape dialog
	private JDialog currentDialog = null;
	
	// components
	private Canvas canvas;
	private MenuPanel menuPanel;

	// controller
	private WorldController controller;
	private GameWindow window;
	
	public WorldPanel(GameWindow window){
		this.setEnabled(false);
		
		this.window = window;
		
		// set up layout and display
		this.setLayout(new BorderLayout());
		
		// set up components
		canvas = new Canvas(this);
		this.add(canvas, BorderLayout.CENTER);
		menuPanel = new MenuPanel(this);
		this.add(menuPanel, BorderLayout.NORTH);
		
		// set up key dispatcher
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new WorldKeyDispatcher());
		
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
	 * Update InfoPanel to display information about the specified tile and its contents.
	 * @param tile: whose info you'll display
	 */
	public void updateInfo(Tile tile){
		canvas.updateInfo(tile);;
	}
	
	/**
	 * All button presses should be sent up to the controller. Nothing happens if CloseDialog is enabled.
	 * @param button: name of the button event that fired
	 */
	protected void buttonPressed(String button) {
		if (currentDialog != null) return;
		controller.buttonPressed(button);
	}

	/**
	 * Create a new game dialog attached to this frame.
	 * @param msg: the message to display.
	 */
	public void startGameDialog(String msg) {
		new GameDialog(this,msg);
	}

	/**
	 * Start a new EscapeDialog.
	 */
	public void startEscapeDialog(){
		new EscapeDialog(this);
	}
	
	/**
	 * End the current EscapeDialog.
	 */
	public void endCurrentDialog(){
		this.currentDialog.dispose();
		this.currentDialog = null;
	}
	
	/**
	 * Set the current dialog of this WorldPanel. This should be done by the Dialog itself when the
	 * it gets constructed.
	 * @param d: the dialog
	 */
	public void setCurrentDialog(JDialog d){
		this.currentDialog = d;
	}
	
	/**
	 * Get the window that this WorldPanel is attached to.
	 * @return GameWindow, a subclass of JFrame.
	 */
	public JFrame getWindow(){
		return this.window;
	}

	/**
	 * Return the player to whom this view belongs.
	 * @return Player
	 */
	public Player getPlayer(){
		return controller.getPlayer();
	}
		
	private class WorldKeyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent ke) {
			
			// only respond to key presses
			if (ke.getID() != KeyEvent.KEY_PRESSED) return false;
			
			// if a dialog is up and you upsh escape, kill the dialog
			int code = ke.getKeyCode();
			if (currentDialog != null){
				if (code == KeyEvent.VK_ESCAPE){
					currentDialog.dispose();
					currentDialog = null;
					return false;
				}
			}
			
			// otherwise pass on event to controller.
			controller.keyPressed(ke);
			return false;
		}
	}

	/**
	 * Redraw the canvas.
	 */
	public void redraw() {
		canvas.repaint();
	}

	/**
	 * Update the day being displayed in the menuPanel.
	 * @param day: new day to display.
	 */
	public void updateDay(int day) {
		menuPanel.updateDay(day);
	}
	
	/**
	 * Update the gold being displayed in the menuPanel.
	 * @param gold: gold to be displayed.
	 */
	public void updateGold(int gold){
		menuPanel.updateGold(gold);
	}
	
}
