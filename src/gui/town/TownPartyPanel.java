package gui.town;

import java.awt.Dimension;

import javax.swing.JPanel;

import tools.Constants;
import world.icons.Party;

public class TownPartyPanel extends JPanel {

	// constants
	private final int PORTRAIT_WIDTH = Constants.PORTRAIT_DIMENSIONS.width;
	private final int PORTRAIT_HEIGHT = Constants.PORTRAIT_DIMENSIONS.height;
	
	// data this townPartyPanel displays
	private TownController controller;
	private Party party;
	
	protected TownPartyPanel(TownController townController) {
		this.controller = townController;
		this.party = party;
		this.setPreferredSize(new Dimension(PORTRAIT_WIDTH*2,PORTRAIT_HEIGHT*3));
	}
	
	

}
