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
	private TownPartySlot[][] slots;
	
	protected TownPartyPanel(Party party, Dimension dimensions, TownController townController) {
		this.controller = townController;
		this.setPreferredSize(dimensions);

	
		GridBagConstraints grid = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		grid.fill = GridBagConstraints.BOTH;
		slots = new TownPartySlot[Party.PARTY_COLS][Party.PARTY_ROWS];
		for (int i = 0; i < slots.length; i++){
			for (int j = 0; j < slots[i].length; j++){
				System.out.printf("(%d,%d)\n",i,j);
				grid.gridx = i;
				grid.gridy = j;
				grid.fill = GridBagConstraints.BOTH;
				TownPartySlot slot = new TownPartySlot(party,townController,i,j);
				grid.insets = new Insets(15,15,10,10);
				slots[i][j] = slot;
				slots[i][j] = new TownPartySlot(party,townController,i,j);
				this.add(slot,grid);
			}
		}
		
	}

	@Override
	protected void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, Constants.PORTRAIT_DIMENSIONS.width, Constants.PORTRAIT_DIMENSIONS.height);
		for (int i = 0; i < slots.length; i++){
			for (int j = 0; j < slots[i].length; j++){
				slots[i][j].draw(g);
			}
		}
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
