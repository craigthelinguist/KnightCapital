package gui.town;

import gui.reusable.CustomButton;

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
import world.towns.City;
import controllers.TownController;

/**
 * A panel that holds all the buttons on the town gui.
 * @author Ewan Moshi && Aaron Craig
 *
 */
public class ButtonPanel extends JPanel{
	
	private JButton[] buttons;
	
	protected ButtonPanel() {
		
		// set layout, appearance
		this.setEnabled(false);
		this.setLayout(new FlowLayout());
		this.setOpaque(false);
		
		// add components
		buttons = makeButtons();
		for (JButton button : buttons){
			this.add(button);
		}
		
	}
	
	private JButton[] makeButtons() {
		return new JButton[]{
			new JButton("Leave"),
			new JButton("Eject"),
			new JButton("Train"),
			new JButton("Production")
		};
	}

	private class ButtonListener implements ActionListener{

		private TownController controller;
		
		public ButtonListener(TownController tc){
			controller = tc;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton)e.getSource();
			controller.buttonPressed(button.getText());
		}
		
	}
	
	/**
	 * Attach a TownController to this panel and activate it.
	 * @param tc: TownController this panel should interact with.
	 * @throws IllegalStateException : if this panel is already enabled.
	 */
	protected void setController(TownController tc){
		if (this.isEnabled())
			throw new IllegalStateException("Setting controller for TownButtonPanel which already has a controller!");
		ButtonListener bl = new ButtonListener(tc);
		for (JButton button : buttons){
			button.addActionListener(bl);
		}
		this.setEnabled(true);
	}
	
}
