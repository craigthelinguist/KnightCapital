package GUI.town;

import game.units.creatures.Creature;
import game.units.creatures.Hero;
import game.units.creatures.Unit;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import controllers.TownController;

import player.Player;
import world.icons.Party;

/**
 * The town gui frame.
 * @author Ewan Moshi && Aaron Craig
 *
 */
public class TownGui extends JFrame {

	private TownPanel panel;

	public TownGui(TownController townController) {
		panel = new TownPanel(townController,this);
		BoxLayout layout = new BoxLayout(this,BoxLayout.PAGE_AXIS);
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}

}
