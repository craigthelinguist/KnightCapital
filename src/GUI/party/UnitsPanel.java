package GUI.party;

import java.awt.Dimension;

import javax.swing.JPanel;

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
 * @author myles
 *
 */
public class UnitsPanel extends JPanel{

	public UnitsPanel(Dimension d) {
		super();
		this.setPreferredSize(d); // give it the d here
	}

}
