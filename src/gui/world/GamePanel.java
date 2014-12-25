package gui.world;

import gui.reusable.CustomButton;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import world.tiles.Tile;

public class GamePanel extends JPanel {

	// main
	private GameFrame main;
	
	// components
	private InfoPanel info;
	// minimap
	
	public GamePanel(GameFrame gameframe){
		main = gameframe;
		this.setPreferredSize(new Dimension(main.getWidth(), 200));
		this.info = new InfoPanel(main);
		this.add(info);
	}
	
	/**
	 * Update the info displayed in the InfoPanel.
	 * @param tile: tile whose info should be displayed.
	 */
	public void updateInfo(Tile tile){
		info.updateInfo(tile);
	}
		
}
