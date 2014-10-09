package gui.town;

import game.units.Creature;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import tools.Constants;
import world.icons.Party;

public class TownPartyPanel extends JPanel {


	// data this townPartyPanel displays
	private TownController controller;
	private Party party;
	private TownPartySlot[][] slots;
	
	protected TownPartyPanel(Party party, Dimension dimensions, TownController townController) {
		this.controller = townController;
		this.setPreferredSize(dimensions);
		
		slots = new TownPartySlot[Party.PARTY_COLS][Party.PARTY_ROWS];
		for (int i = 0; i < slots.length; i++){
			for (int j = 0; j < slots[i].length; j++){
				slots[i][j] = new TownPartySlot(party,townController,i,j);
			}
		}
		
	}

	@Override
	protected void paintComponent(Graphics g){
		for (int i = 0; i < slots.length; i++){
			for (int j = 0; j < slots[i].length; j++){
				slots[i][j].draw(g);
			}
		}
	}
}
