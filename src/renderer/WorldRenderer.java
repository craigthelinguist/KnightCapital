package renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import storage.TemporaryLoader;
import world.World;

public class WorldRenderer {
	
	/**
	 * Draw the world on the given graphics object.
	 * @param world
	 */
	public static void render(World world, Graphics graphics){
	
		
		
	}

	// for testing purposes - Aaron
	public static void main(String[] args){
		final World world = TemporaryLoader.loadWorld("world_temporary.txt");
		final JFrame frame = new JFrame();
		JPanel panel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				WorldRenderer.render(world,g);
				this.repaint();
			}
		};
		panel.setPreferredSize(new Dimension(1000,800));
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	
	
}
