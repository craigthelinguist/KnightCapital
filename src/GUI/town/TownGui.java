package GUI.town;

import game.units.Creature;
import game.units.Hero;
import game.units.Unit;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import controllers.TownController;

import player.Player;
import world.icons.Party;

public class TownGui extends JFrame {

	private TownPanel panel;
	
	public TownGui(TownController townController) {
		
		
		
		panel = new TownPanel(townController);
		
		BoxLayout layout = new BoxLayout(this,BoxLayout.PAGE_AXIS);//this.add(panel);
		this.add(panel);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
}
