package gui.town;

import game.units.Creature;
import game.units.Hero;
import game.units.Unit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import player.Player;

import tools.Constants;
import world.icons.Party;

public class TownPartyPanel extends JPanel {


	// data this townPartyPanel displays
	private TownController controller;
	private Party party;
	private TownPartySlot slots[][];
	
	protected TownPartyPanel(Party party, Dimension dimensions, TownController townController) {
		this.controller = townController;
		this.setPreferredSize(dimensions);

	
		GridBagConstraints grid = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
	
		slots = new TownPartySlot[Party.PARTY_COLS][Party.PARTY_ROWS];
		for (int y = 0; y < Party.PARTY_ROWS; y++){
			for (int x = 0; x < Party.PARTY_COLS; x++){
				grid.gridx = x;
				grid.gridy = y;
				TownPartySlot slot = new TownPartySlot(party,townController,x,y);
				this.add(slot,grid);
				slots[x][y] = slot;
			}
		}
		
	}

	@Override
	protected void paintComponent(Graphics g){

	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		Dimension size = new Dimension(800,800);
		Player player = new Player("Biggie Smalls",2);
		Hero hero = new Hero("ovelia",player);
		Creature[][] members = Party.newEmptyParty();
		members[0][0] = hero;
		members[0][1] = new Unit("knight",player);
		members[1][1] = new Unit("knight",player);
		Party party = new Party(hero, player, members);
		TownPartyPanel tpp = new TownPartyPanel(party,size,null);
		panel.add(tpp);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
}
