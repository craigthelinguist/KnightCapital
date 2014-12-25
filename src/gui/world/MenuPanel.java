package gui.world;

import gui.escape.EscapeDialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {
	
	private GameFrame main;
	
	// components
	private JLabel labelDay;
	private JLabel labelGold;
	
	public MenuPanel(GameFrame gameframe){
		
		// fields
		main = gameframe;
		
		// layout
		this.setLayout(new FlowLayout());
		
		// create components
		JButton buttonMenu = menuButton();
		JButton buttonChatLog = chatButton();
		JButton buttonObjectives = objectivesButton();
		JButton buttonPlayers = playersButton();
		this.labelDay = dayLabel(1);
		this.labelGold = goldLabel(100);
		
		// add components
		this.setPreferredSize(new Dimension(main.getWidth(), 40));
		this.add(buttonMenu);
		this.add(buttonChatLog);
		this.add(buttonObjectives);
		this.add(buttonPlayers);
		this.add(labelDay);
		this.add(labelGold);
		
		// formatting
		this.setVisible(true);
	}

	/**
	 * Create and return a button. When you click the button it brings up a new EscapeDialog, where you can save, load, exit, etc.
	 * @return JButtno
	 */
	private JButton menuButton(){
		JButton button = new JButton("Menu");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new EscapeDialog(main);
			}
		});
		return button;
	}
	
	/**
	 * Create and return a button. When you click the button it brings up a chat log displaying the chat history of the scenario.
	 * @return JButton
	 */
	private JButton chatButton(){
		JButton button = new JButton("Chat Log");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		return button;
	}
	
	/**
	 * Create and return a button. When you click the button it brings up a menu that displays your objectives for the current
	 * scenario.
	 * @return JButton
	 */
	private JButton objectivesButton(){
		JButton button = new JButton("Objectives");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		return button;
	}
	
	/**
	 * Create and return a button. When you click the button it brings up a menu that displays info about the players in this
	 * scenario.
	 * @return JButton
	 */
	private JButton playersButton(){
		JButton button = new JButton("Players");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		return button;
	}
	
	/**
	 * Create and return a JLabel. The label displays what day it is.
	 * @return
	 */
	private JLabel dayLabel(int day){
		return new JLabel("Day: " + day);
	}
	
	/**
	 * Create and return a JLabel. The label displays how much gold the player has.
	 * @return JLabel
	 */
	private JLabel goldLabel(int gold){
		return new JLabel("Gold: " + gold);
	}
	
	/**
	 * Update the amount of gold being displayed.
	 * @param amount: new amount of gold to be displayed.
	 */
	public void updateGold(int amount){
		this.labelGold.setText("Gold: " + amount);
	}
	
	/**
	 * Update the day being displayed.
	 * @param day: new day number to be displayed.
	 */
	public void updateDay(int day){
		this.labelDay.setText("Day: " + day);
	}
	
}
