package GUI.party;

import game.items.Item;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.Constants;
import world.icons.Party;

/**
 * Responsible for displaying the items that may be equipped to the selected party
 * @author myles, Ewan Moshi
 *
 */
public class PartyItemsPanel extends JPanel{

	// data that this Ordering Panel Displays
	private PartyDialog master;
	private Party party;
	private Point dragging;

	private Item selected;

	private final int PORTRAIT_WIDTH = Constants.INVENTORY_DIMENSIONS.width;
	private final int PORTRAIT_HEIGHT = Constants.INVENTORY_DIMENSIONS.height;
	private final int INVENTORY_ROWS = Party.INVENTORY_ROWS;
	private final int INVENTORY_COLS = Party.INVENTORY_COLS;
	private final int WIDTH = Constants.INVENTORY_DIMENSIONS.width*Party.PARTY_COLS+1;
	private final int HEIGHT = Constants.INVENTORY_DIMENSIONS.height*Party.PARTY_ROWS+1;

	public PartyItemsPanel(PartyDialog master, Dimension d) {
		this.setOpaque(false);
		this.master = master;
		this.party = master.getParty();
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		dragging = null;


		// listeners
		if (master.isOwner()){
		this.addMouseListener(new SlotListener());
		this.addMouseMotionListener(new SlotListener());
		}
	}

	private void redraw() {
		this.repaint();
	}

	/**
	 * Return the given point (x,y) as a point in array.
	 * @param x: x position of point
	 * @param y: y position of point
	 * @return: a point (x,y)
	 */
	private Point asArrayIndices(int x, int y){
		x /= PORTRAIT_WIDTH;
		y /= PORTRAIT_HEIGHT;
		return new Point(x,y);
	}

	/**
	 * Return true if this is a valid point in the array.
	 * @param pt
	 * @return
	 */
	private boolean validPoint(Point pt) {
		if (pt.x < 0) return false;
		else if (pt.x >= INVENTORY_COLS) return false;
		else if (pt.y < 0) return false;
		else if (pt.y >= INVENTORY_ROWS) return false;
		else return true;
	}

	/**
	 * Update the master dialogs selected item panel with the last selected item
	 */
	private void updateSelectedItem() {
		master.updateSelectedWithItem(selected);
	}

	@Override
	protected void paintComponent(Graphics g){
		
		if (!master.isOwner()) return;
		
		//g.setColor(Color.WHITE);
		//g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(Color.BLACK);
		for (int x = 0; x < INVENTORY_COLS; x++){
			for (int y = 0; y < INVENTORY_ROWS; y++){
				int xDraw = x * PORTRAIT_WIDTH;
				int yDraw = y * PORTRAIT_HEIGHT;
				g.drawRect(xDraw, yDraw, PORTRAIT_WIDTH, PORTRAIT_HEIGHT);
				if (party == null) continue; // nothing to draw
				if (dragging != null && dragging.equals(new Point(x,y))) continue; // this is being dragged
				Item item = party.getItem(x,y);

				if (item == null) continue;
				System.out.println("drawing at not dragged");
				BufferedImage portrait = item.getPortrait();
				g.drawImage(portrait,xDraw,yDraw,null);
			}
		}

		if (dragging != null){
			Point mousePoint = MouseInfo.getPointerInfo().getLocation();
			Point location = this.getLocationOnScreen();
			mousePoint = new Point(mousePoint.x-location.x,mousePoint.y-location.y);
			Item item = party.getItem(dragging.x,dragging.y);
			if (item != null){
				System.out.println("drawing at dragging");
				BufferedImage portrait = item.getPortrait();
				g.drawImage(portrait, mousePoint.x-PORTRAIT_WIDTH/2, mousePoint.y-PORTRAIT_WIDTH/2, null);
			}
		}
	}

	private class SlotListener implements MouseMotionListener,MouseListener{

		@Override
		public void mouseDragged(MouseEvent arg0) {
			if (dragging != null){
				repaint();
			}
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
			dragging = null;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Point pt = asArrayIndices(e.getX(),e.getY());
			if (party != null && party.getItem(pt.x, pt.y) != null){
				System.out.println("[PartyDialog][PartyItems] Selected item at "+ pt.x + " " + pt.y);
				selected = party.getItem(pt.x, pt.y);
				updateSelectedItem();
				dragging = asArrayIndices(e.getX(),e.getY());

			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			Point release = asArrayIndices(e.getX(),e.getY());
			if (dragging == null || !validPoint(dragging) || release == null || !validPoint(release)){
				dragging = null;
				return;
			}
			else{
				party.swap(dragging,release);
				dragging = null;
				repaint();
			}
		}
	}
}
