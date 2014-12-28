package gui.world;

import gui.reusable.CustomButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import world.tiles.Tile;

public class GamePanel extends JPanel {

	// main
	private WorldPanel main;
	
	// components
	private InfoPanel info;
	private MiniMap minimap;
	
	public GamePanel(WorldPanel gameframe){
		main = gameframe;
		this.setPreferredSize(new Dimension(main.getWidth(), 200));
		
		
		this.setBackground(Color.YELLOW);
		this.setOpaque(false);
		
		this.setLayout(new BorderLayout());
		
		this.info = new InfoPanel(main);
		this.add(info, BorderLayout.LINE_END);
		
		this.minimap = new MiniMap(main);
		this.add(minimap, BorderLayout.LINE_START);
		
		
	}
	
	/**
	 * Update the info displayed in the InfoPanel.
	 * @param tile: tile whose info should be displayed.
	 */
	public void updateInfo(Tile tile){
		info.updateInfo(tile);
	}
		
}
