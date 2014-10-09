package gui.town;

import game.units.Creature;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import tools.Constants;
import world.icons.Party;

public class TownPartySlot extends JPanel{

	// constants
	private final int PORTRAIT_WIDTH = Constants.PORTRAIT_DIMENSIONS.width;
	private final int PORTRAIT_HEIGHT = Constants.PORTRAIT_DIMENSIONS.height;
	
	// parameters
	private final int x;
	private final int y;
	private final TownController controller;
	private final Party party;
		
	// are you currently dragging mouse
	private boolean dragging;
	
	protected TownPartySlot(TownController townController, Party party, int x, int y){
		this.x = x;
		this.y = y;
		this.controller = townController;
		this.addMouseListener(new SlotListener());
		this.party = party;
		dragging = false;
	}
	
	protected void draw(Graphics g){
		Creature c = party.getMember(x, y);
		g.drawImage(c.getPortrait(),0,0,null);
	}
		
	private class SlotListener implements MouseMotionListener,MouseListener{

		@Override
		public void mouseDragged(MouseEvent arg0) {
			dragging = true;
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			dragging = false;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (dragging){
				// do shit
			}
			dragging = false;
		}

	}
	
}
