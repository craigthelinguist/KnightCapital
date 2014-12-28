package gui.world;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tools.ImageLoader;

public class MiniMap extends JPanel {

	private WorldPanel main;
	
	public MiniMap(WorldPanel worldPanel){
		this.main = worldPanel;
		this.setPreferredSize(new Dimension(235, 235));
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(10,25,25,10));
		
		JPanel interior = new JPanel();
		interior.setBackground(Color.GREEN);
		interior.setPreferredSize(new Dimension(200, 200));
		
		this.add(interior, BorderLayout.CENTER);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		g.setColor(Color.PINK);
		g.fillRect(0, 0, getWidth(), getHeight());
		//BufferedImage bi = ImageLoader.load("assets/gui_town/city_splash", "jpg");
		//g.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
	}
	
}
