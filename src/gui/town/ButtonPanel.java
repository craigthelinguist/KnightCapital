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

	private TownController controller;
	private TownPanel townPanel;
	
	protected ButtonPanel(TownPanel gui, City city) {
		
		// fields
		this.townPanel = gui;
		
		// set layout, appearance
		this.setLayout(new FlowLayout());
		this.setOpaque(false);
		
		// add components
		this.add(leaveButton());
		this.add(ejectButton());
		this.add(trainButton());
		this.add(productionButton());
		
	}

	/**
	 * Make and return a JButton. When clicked, takes you to a more detailed view that
	 * lets you construct buildings, research technologies, and produce items.
	 * @return JButton
	 */
	private JButton productionButton(){
		JButton button = new JButton("Production");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				
			}
		});
		return button;
	}

	/**
	 * Make and return a JButton. When clicked prompts you with a dialog that asks you
	 * to select a unit to train.
	 * @return JButton
	 */
	private JButton trainButton() {
		JButton button = new JButton("Train");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				
			}
		});
		return button;
	}

	/**
	 * Make and return a JButton. When clicked will eject the visiting party if
	 * there is space.
	 * @return JButton
	 */
	private JButton ejectButton() {
		JButton button = new JButton("Eject");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				
			}
		});
		return button;
	}

	/**
	 * Make and return a JButton. When clicked will leave the Town scene and return
	 * to the World scene.
	 * @return JButton
	 */
	private JButton leaveButton() {
		JButton button = new JButton("Leave");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				ButtonPanel.this.townPanel.endTownView();
			}
		});
		return button;
	}

	/**
	 * Attach a TownController to this panel and activate it.
	 * @param tc: TownController this panel should interact with.
	 */
	protected void setController(TownController tc){
		if (this.controller != null)
			throw new RuntimeException("Setting controller for TownButtonPanel which already has a controller!");
		this.controller = tc;
		this.setEnabled(true);
	}
	
}
