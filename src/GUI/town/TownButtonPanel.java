package GUI.town;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;
import GUI.world.CustomButton;
import controllers.TownController;

/**
 * A panel that holds all the buttons on the town gui.
 * @author Ewan Moshi
 *
 */
public class TownButtonPanel extends JPanel{

	private TownController controller;
	private CustomButton button_leave;

	protected TownButtonPanel(Dimension dimensions, TownController townController) {
		this.controller = townController;
		this.setPreferredSize(dimensions);
		this.setLayout(new FlowLayout());
		/*Declare and initialize the images for the buttons */
		BufferedImage closeTownDefault = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "closeButton.png");
		BufferedImage closeTownPressed = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "closeButtonClicked.png");
		BufferedImage closeTownHover = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "closeButtonHover.png");
		button_leave = new CustomButton(closeTownDefault, closeTownPressed, closeTownHover);

		//this.add(panel_exchange);
		JLabel townName = new JLabel(controller.getCity().getName()+" City");
		townName.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20)); //set font of JLabel to franklin gothic medium
		townName.setForeground(new Color(225,179,55));
		townName.setOpaque(false);



		button_leave.setPreferredSize(new Dimension(80,50));
		button_leave.addActionListener(new ActionListener(){



			@Override
			public void actionPerformed(ActionEvent event) {
				controller.buttonPressed("leave");
			}

		});
		this.add(button_leave);
		this.add(townName);
		this.setPreferredSize(new Dimension(200,100));

	}

}
