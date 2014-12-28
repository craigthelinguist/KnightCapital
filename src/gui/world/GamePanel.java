package gui.world;

import gui.reusable.CustomButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import tools.ImageLoader;
import world.tiles.Tile;

public class GamePanel extends JPanel {

	// main
	private WorldPanel main;
	
	// components
	private InfoPanel info;
	private MiniMap minimap;
	
	public GamePanel(WorldPanel gameframe, InterfaceTheme theme){
		main = gameframe;
		
		this.setBackground(Color.YELLOW);
		this.setOpaque(false);
		
		this.setLayout(new BorderLayout());
		
		JPanel infoWrapper = new JPanel();
		infoWrapper.setLayout(new BorderLayout());
		infoWrapper.setOpaque(false);
		
		this.info = new InfoPanel(main, theme.getInfopanel());
		infoWrapper.add(info, BorderLayout.SOUTH);
		this.add(infoWrapper, BorderLayout.LINE_END);
		
		
		this.minimap = new MiniMap(main, theme.getMinimap());
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
