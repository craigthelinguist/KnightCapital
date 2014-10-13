package GUI.world;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;

/**
 * This Class contains all the buttons for the main panel
 *
 * @author Ewan Moshi
 *
 */
public class ButtonsPanel extends JPanel  {

	// the top-level view
	private MainFrame gui;

	private CustomButton inventoryButton;
	private CustomButton newGame;

	/*Place holder buttons*/
	private JButton b1;
	private JButton b2;
	private JButton b3;

	public ButtonsPanel(MainFrame frame) {

		gui = frame;

		/*set the size of this panel to be size of the image*/
		this.setOpaque(false);

		/*Initialize the layout and the insets*/
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,1,5,1); //top, left, bottom, right padding (in that order)

		/*Declare and initialize the images for the button */
		BufferedImage inventoryDefaultIcon = ImageLoader.load(Constants.GUI_FILEPATH + "inventoryDefaultTemp.png");
		BufferedImage inventoryPressedIcon = ImageLoader.load(Constants.GUI_FILEPATH + "inventoryHoverTemp.png");
		inventoryButton = new CustomButton(inventoryDefaultIcon, inventoryPressedIcon,null);
		/*Place these buttons on a different panel and add that panel on this layeredPane(PanelMaster)*/
		c.gridx = 0;
		c.gridy = 0;
		this.add(inventoryButton,c);



		/*Declare and initialize the images for the button */
		BufferedImage newGameDefault = ImageLoader.load(Constants.GUI_BUTTONS + "newGameButton.png");
		BufferedImage newGamePressed = ImageLoader.load(Constants.GUI_BUTTONS + "newGameButtonClicked.png");
		BufferedImage newGameHover = ImageLoader.load(Constants.GUI_BUTTONS + "newGameButtonHover.png");
		newGame = new CustomButton(newGameDefault, newGamePressed, newGameHover);
		c.gridx = 0;
		c.gridy = 1;
		this.add(newGame,c);

		b2 = new JButton("End Turn Placeholder");
		c.gridx = 0;
		c.gridy = 2;
		this.add(b2,c);

		setupActionListeners();

	}

	private void setupActionListeners(){

		b2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.buttonPressed(b2);
			}


		});

	}


}
