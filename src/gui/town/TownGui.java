package gui.town;

import game.units.Creature;
import game.units.Hero;
import game.units.Unit;

import java.awt.Dimension;

import javax.swing.JFrame;

import player.Player;
import world.icons.Party;

public class TownGui extends JFrame {

	private TownPanel panel;
	
	protected TownGui(TownController townController) {
		panel = new TownPanel(townController);
		this.add(panel);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	public static void main(String[] args){
	
		new TownGui(null);
	}
	
}
