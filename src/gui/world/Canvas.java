package gui.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import renderer.WorldRenderer;
import controllers.WorldController;

/**
 * Displays the current scene in the World.
 * @author craigthelinguist
 */
public class Canvas extends JPanel{

	private WorldController controller;
	private GameFrame main;
			
	public Canvas(GameFrame gameframe){
		this.main = gameframe;
	}
	
	/**
	 * Set the controller for this GameFrame.
	 * @throws RuntimeException: if this GameFrame already has a controller.
	 * @param wc: controller to attach to this view.
	 */
	public void setController(WorldController wc){
		if (this.controller != null) throw new RuntimeException("setting controller for Canvas");
		controller = wc;
		CanvasListener listener = new CanvasListener();
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
	}
	
	@Override
	protected void paintComponent(Graphics graphics){
		if (controller == null) return;
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0,0,getWidth(),getHeight());
		Dimension resolution = this.getSize();
		WorldRenderer.render(controller, graphics, resolution);
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
