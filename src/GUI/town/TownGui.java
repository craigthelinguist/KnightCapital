package GUI.town;

import game.units.Creature;
import game.units.Hero;
import game.units.Unit;

import java.awt.Dimension;

import javax.swing.JFrame;

import controllers.TownController;

import player.Player;
import world.icons.Party;

public class TownGui extends JFrame {

	private TownPanel panel;
	
	public TownGui(TownController townController) {
		panel = new TownPanel(townController, townController.getVisitors(), townController.getGarrison());
		this.add(panel);
		
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
}
