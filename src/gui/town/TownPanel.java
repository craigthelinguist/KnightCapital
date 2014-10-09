package gui.town;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;

public class TownPanel extends JPanel{
	
	// constants
	private final static String FILEPATH = Constants.GUI_TOWN;
	private final static String BACKDROP = "city_splash";
	
	// controller and top-level view
	private TownController controller;
	private BufferedImage splash;

	// components
	private TownPartyPanel panel_visitor;
	private TownPartyPanel panel_garrison;
	private TownButtonPanel panel_buttons;
	
	protected TownPanel(TownController townController) {
		this.controller = townController;
		this.splash = ImageLoader.load(FILEPATH + BACKDROP, ".jpg");
		this.setPreferredSize(new Dimension(splash.getWidth(),splash.getHeight()));

		Dimension childDimensions = new Dimension(getWidth()/3,getHeight()/3);
		panel_visitor = new TownPartyPanel(controller.visitors, childDimensions,townController);
		panel_garrison = new TownPartyPanel(controller.garrison, childDimensions,townController);
		panel_buttons = new TownButtonPanel(childDimensions,townController);
		
		this.add(panel_visitor);
		this.add(panel_garrison);
		this.add(panel_buttons);
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
		g.drawImage(splash,0,0,null);
	}
	
}
