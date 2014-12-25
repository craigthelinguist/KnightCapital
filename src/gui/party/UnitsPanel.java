package gui.party;

import game.units.creatures.Creature;
import game.units.creatures.Hero;
import game.units.creatures.Unit;
import game.units.stats.HeroStats;
import game.units.stats.UnitStats;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import player.Player;
import tools.Constants;
import world.icons.Party;

/**
 * This panel is responsible for displaying the current status of your units,
 * as well as allowing the user to move and set the positions of each of the units
 * in the party (to make a battle formation?)
 *
 * If the user does not own this party, they should only be able to see the units, and
 * not their stats.
 *
 * If the user does own the party, they can see a coloured bar for each of the 3 stats
 * a unit has.
 *
 * @author myles, Ewan Moshi
 *
 */
public class UnitsPanel extends JPanel{

	// the parent of this panel
	private PartyDialog master;

	// data this townPartyPanel displays
	private Party party;
	private Point dragging;

	private Creature selected;

	private final int PORTRAIT_WIDTH = Constants.PORTRAIT_DIMENSIONS.width;
	private final int PORTRAIT_HEIGHT = Constants.PORTRAIT_DIMENSIONS.height;
	private final int PARTY_ROWS = Party.PARTY_ROWS;
	private final int PARTY_COLS = Party.PARTY_COLS;
	private final int WIDTH = Constants.PORTRAIT_DIMENSIONS.width*Party.PARTY_COLS+1;
	private final int HEIGHT = Constants.PORTRAIT_DIMENSIONS.height*Party.PARTY_ROWS+1;

	/**
	 * Contructs a new party panel with draggable units
	 * @param master Party Dialog
	 * @param dimensions for panel
	 */
	public UnitsPanel(PartyDialog master, Dimension d) {;
		this.setOpaque(false);
		this.setPreferredSize(d); // give it the d here
		this.master = master;
		this.party = master.getParty();
		dragging = null;

		// listeners
		this.addMouseListener(new SlotListener());
		this.addMouseMotionListener(new SlotListener());
	}

	/**
	 * Get the party represented in this townPartyPanel.
	 * @return: a party
	 */
	public Party getParty(){
		return party;
	}

	private void redraw(){
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
		else if (pt.x >= PARTY_COLS) return false;
		else if (pt.y < 0) return false;
		else if (pt.y >=  PARTY_ROWS) return false;
		else return true;
	}

	public void updateSelected() {
		this.master.updateSelectedWithUnit(this.selected);
	}

	@Override
	protected void paintComponent(Graphics g){
		//g.setColor(Color.WHITE);
		//g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(Color.BLACK);
		for (int x = 0; x < PARTY_COLS; x++){
			for (int y = 0; y < PARTY_ROWS; y++){
				int xDraw = x * PORTRAIT_WIDTH;
				int yDraw = y * PORTRAIT_HEIGHT;
				g.drawRect(xDraw, yDraw, PORTRAIT_WIDTH, PORTRAIT_HEIGHT);
				if (party == null) continue; // nothing to draw
				if (dragging != null && dragging.equals(new Point(x,y))) continue; // this is being dragged
				Creature member = party.getMember(x,y);
				if (member == null) continue;
				BufferedImage portrait = member.getPortrait();
				g.drawImage(portrait,xDraw,yDraw,null);
			}
		}

		// TODO: draw the thing being dragged
		if (dragging != null){
			Point mousePoint = MouseInfo.getPointerInfo().getLocation();
			Point location = this.getLocationOnScreen();
			mousePoint = new Point(mousePoint.x-location.x,mousePoint.y-location.y);
			Creature member = party.getMember(dragging.x,dragging.y);
			if (member != null){
				BufferedImage portrait = member.getPortrait();
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
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			dragging = null;
			UnitsPanel.this.repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Point pt = asArrayIndices(e.getX(),e.getY());
			if (!validPoint(pt)) return;
			if (party != null && party.getMember(pt.x, pt.y) != null){
				selected = party.getMember(pt.x, pt.y);
				updateSelected();
				System.out.println("[PartyDialog][UnitsPanel]Selected Unit is at "+pt.x + " "+pt.y);
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

