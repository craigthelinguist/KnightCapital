package gui.world;

import gui.reusable.DeadListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tools.ImageLoader;

public class MiniMap extends JPanel {

	private WorldPanel main;
	private BufferedImage background;
	
	public MiniMap(WorldPanel worldPanel, BufferedImage background){
		this.main = worldPanel;
		this.background = background;
		this.setPreferredSize(new Dimension(235, 235));
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(10,25,25,10));
		
		JPanel interior = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
				g.setColor(Color.BLACK);
				g.fillRect(0,0,getWidth(),getHeight());
			}
		};
		interior.setPreferredSize(new Dimension(200, 200));
		
		this.add(interior, BorderLayout.CENTER);
		this.addMouseMotionListener(DeadListener.get());
		this.addMouseListener(DeadListener.get());
	}
	
	@Override
	public void paint(Graphics g){
		this.paintChildren(g);
		g.drawImage(this.background, 0,0, getWidth(), getHeight(), this);
	}
	
}
