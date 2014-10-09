package gui.town;

import java.awt.Dimension;

import javax.swing.JPanel;

public class TownButtonPanel extends JPanel{

	private TownController controller;
	
	protected TownButtonPanel(Dimension dimensions, TownController townController) {
		this.controller = townController;
		this.setPreferredSize(dimensions);
	}

}
