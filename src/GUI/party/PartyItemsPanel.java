package GUI.party;

import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Responsible for displaying the items that may be equipped to the selected party
 * @author myles
 *
 */
public class PartyItemsPanel extends JPanel{

	// the parent of this
	private PartyDialog master;

	public PartyItemsPanel(PartyDialog master, Dimension d) {
		super();
		this.setPreferredSize(d);
		this.master = master;
	}
}
