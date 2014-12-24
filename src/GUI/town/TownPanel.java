	package GUI.town;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.reusable.PartyPanel;
import controllers.TownController;
import tools.Constants;
import tools.ImageLoader;
import world.icons.Party;

/**
 * Main panl for the Town GUI
 * @author Ewan Moshi && Craig Aaron
 *
 */
public class TownPanel extends JPanel{

	// constants
	private final static String FILEPATH = Constants.GUI_TOWN;
	private final static String BACKDROP = "city_splash";

	// controller and top-level view
	private TownController controller;
	private BufferedImage splash;
	private TownGui gui;

	// components
	private TownExchangePanel panel_exchange;
	private TownButtonPanel panel_buttons;	

	protected TownPanel(TownController townController, TownGui gui) {
		this.controller = townController;
		this.gui = gui;
		this.splash = ImageLoader.load(FILEPATH + BACKDROP, ".jpg");
		this.setPreferredSize(new Dimension(splash.getWidth(),splash.getHeight()));

		panel_exchange = new TownExchangePanel(townController, gui);
		panel_buttons = new TownButtonPanel(new Dimension(10,10),townController);

		BorderLayout bl = new BorderLayout();
		this.setLayout(bl);
		this.add(panel_exchange,bl.SOUTH);

		panel_buttons.setOpaque(false);
		this.add(panel_buttons,BorderLayout.WEST);

	}

	@Override
	protected void paintComponent(Graphics g){
		g.drawImage(splash,0,0,null);
		panel_exchange.repaint();
	}

}
