package GUI.world;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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

	private CustomButton endTurnButton;

	JLabel goldLabel = new JLabel();
	JLabel gold = new JLabel();

	public ButtonsPanel(MainFrame frame) {

		gui = frame;

		/*set the size of this panel to be size of the image*/
		this.setOpaque(false);

		/*Initialize the layout and the insets*/
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,1,5,1); //top, left, bottom, right padding (in that order)


		/*gold = new JLabel("Gold:");
		c.gridx = 0;
		c.gridy = 0;
		this.add(inventoryButton,c);*/

		/*Declare and initialize the images for the buttons */
		BufferedImage endTurn = ImageLoader.load(Constants.GUI_BUTTONS + "endTurn.png");
		BufferedImage endTurnPressed = ImageLoader.load(Constants.GUI_BUTTONS + "endTurnClicked.png");
		BufferedImage endTurnHover = ImageLoader.load(Constants.GUI_BUTTONS + "endTurnHover.png");
		endTurnButton = new CustomButton(endTurn, endTurnPressed, endTurnHover);
		c.gridx = 0;
		c.gridy = 0;
		this.add(endTurnButton,c);


		setupActionListeners();

	}

	/**
	 * Set up the action listeners for the buttons.
	 */
	private void setupActionListeners(){

		endTurnButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.buttonPressed("EndTurn");
			}


		});

	}


}
