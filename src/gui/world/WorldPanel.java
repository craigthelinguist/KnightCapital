
package gui.world;

import gui.escape.EscapeDialog;
import gui.world.GameDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.GameWindow;
import player.Player;
import renderer.WorldRenderer;
import world.tiles.Tile;
import controllers.WorldController;

public class WorldPanel extends JPanel{
	
	// current escape dialog
	private JDialog currentDialog = null;

	// controller
	private WorldController controller;
	private GameWindow window;
	
	private GamePanel gamePanel;
	private MenuPanel menuPanel;
	
	public WorldPanel(GameWindow window){
		this.setEnabled(false);
		
		this.window = window;
		
		// set up components
		this.setLayout(new BorderLayout());
	
		this.gamePanel = new GamePanel(this);
		this.add(gamePanel, BorderLayout.SOUTH);
		
		this.menuPanel = new MenuPanel(this);
		this.add(menuPanel, BorderLayout.NORTH);
		
		// set up key dispatcher
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new WorldKeyDispatcher());
		CanvasListener mouselistener = new CanvasListener();
		this.addMouseListener(mouselistener);
		this.addMouseMotionListener(mouselistener);	
	}

	/**
	 * Set the controller for this GameFrame.
	 * @throws RuntimeException: if this GameFrame already has a controller.
	 * @param wc: controller to attach to this view.
	 */
	public void setController(WorldController wc){
		if (this.controller != null) throw new RuntimeException("setting controller for MainFrame");
		controller = wc;
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

	
	@Override
	protected void paintComponent(Graphics graphics){
		if (controller == null) return;
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0,0,getWidth(),getHeight());
		Dimension resolution = this.getSize();
		WorldRenderer.render(controller, graphics, resolution);
		gamePanel.repaint();
	}
	
	/**
	 * Redraw the canvas.
	 */
	public void redraw() {
		this.repaint();
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
	
	/**
	 * Update InfoPanel to display information about the specified tile and its contents.
	 * @param tile: whose info you'll display
	 */
	public void updateInfo(Tile tile){
		gamePanel.updateInfo(tile);
	}

	/**
	 * Responds to mouse clicks.
	 * @author craigthelinguist
	 */
	class CanvasListener implements MouseListener, MouseMotionListener{

		private boolean click = false;
		private Point lastDrag = null;
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if (!click) return;
			if (lastDrag != null){
				controller.mouseDragged(lastDrag, new Point(e.getX(), e.getY()));
			}
			lastDrag = new Point(e.getX(), e.getY());
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (controller != null){
				controller.mouseMoved(e);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			controller.mousePressed(e);	
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			this.click = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			this.click = false;
			this.lastDrag = null;
		}
		
	}
	
}
