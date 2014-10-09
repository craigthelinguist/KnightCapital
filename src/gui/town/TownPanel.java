package gui.town;

import java.awt.Dimension;
import java.awt.Graphics;
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

	protected TownPanel(TownController townController) {
		this.controller = townController;
		this.splash = ImageLoader.load(FILEPATH + BACKDROP, ".jpg");
		this.setPreferredSize(new Dimension(splash.getWidth(),splash.getHeight()));
	}
	
	@Override
	protected void paintComponent(Graphics g){
		g.drawImage(splash,0,0,null);
	}
	
}
