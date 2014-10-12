package GUI.town;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import GUI.reusable.PartyPanel;
import controllers.TownController;
import tools.Constants;
import tools.ImageLoader;
import world.icons.Party;

public class TownPanel extends JPanel{

	// constants
	private final static String FILEPATH = Constants.GUI_TOWN;
	private final static String BACKDROP = "city_splash";

	// controller and top-level view
	private TownController controller;
	private BufferedImage splash;

	// components


	private TownExchangePanel panel_exchange;
	private TownButtonPanel panel_buttons;

	protected TownPanel(TownController townController) {
		this.controller = townController;
		this.splash = ImageLoader.load(FILEPATH + BACKDROP, ".jpg");
		this.setPreferredSize(new Dimension(splash.getWidth(),splash.getHeight()));

		Dimension childDimensions = new Dimension(getWidth()/3,getHeight()/3);
		panel_exchange = new TownExchangePanel(townController);
		//panel_buttons = new TownButtonPanel(childDimensions,townController);

		BorderLayout bl = new BorderLayout();
		this.setLayout(bl);
		this.add(panel_exchange,bl.SOUTH);

		//this.setLayout(new FlowLayout(FlowLayout.LEFT));

		//this.add(panel_exchange);
		//this.add(panel_buttons);

		System.out.println(panel_exchange.getPreferredSize());
		System.out.println(panel_exchange.getSize());

	}

	@Override
	protected void paintComponent(Graphics g){
		g.drawImage(splash,0,0,null);
		panel_exchange.repaint();
	}

}
