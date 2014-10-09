package GUI.town;

import java.awt.Dimension;

import javax.swing.JPanel;

import controllers.TownController;

public class TownButtonPanel extends JPanel{

	private TownController controller;
	
	protected TownButtonPanel(Dimension dimensions, TownController townController) {
		this.controller = townController;
		this.setPreferredSize(dimensions);
	}

}
