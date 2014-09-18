package renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import storage.TemporaryLoader;
import tools.GlobalConstants;
import world.AbstractTile;
import world.World;

public class WorldRenderer {
	
	private static int origin_x = 0;
	private static int origin_y = 0;
	
	/**
	 * Draw the world on the given graphics object.
	 * @param world
	 */
	public static void render(World world, Graphics graphics, Dimension resolution){
		final int TILE_WD = GlobalConstants.TILE_WD;
		final int TILE_HT = GlobalConstants.TILE_HT;
		AbstractTile[][] tiles = world.getTiles();		
		for (int y = 0; y < tiles.length; y++){
			for (int x = 0; x < tiles[y].length; x++){
				BufferedImage bi = tiles[x][y].getImage();
				int isoX = origin_x + (TILE_WD/2)*x - (TILE_WD/2)*y;
				int isoY = origin_y + (TILE_HT/2)*x + (TILE_HT/2)*y;
				graphics.drawImage(bi,isoX,isoY,null);
			}
		}
	}

	// for testing purposes - Aaron
	public static void main(String[] args){
		final World world = TemporaryLoader.loadWorld("world_temporary.txt");
		final JFrame frame = new JFrame();
		final JPanel panel = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				WorldRenderer.render(world,g,getSize());
				this.repaint();
			}
		};

		panel.setPreferredSize(new Dimension(1000,800));
		origin_x = panel.getPreferredSize().width/2;
		origin_y = panel.getPreferredSize().height/2;
		System.out.println(panel.getWidth() + "," + panel.getHeight());
		
		frame.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				int code = arg0.getKeyCode();
				if (code == KeyEvent.VK_LEFT){
					origin_x+=4;
				}
				else if (code == KeyEvent.VK_RIGHT){
					origin_x-=4;
				}
				else if (code == KeyEvent.VK_DOWN){
					origin_y-=4;
				}
				else if (code == KeyEvent.VK_UP){
					origin_y+=4;
				}
				System.out.println(origin_x + "," + origin_y);
				panel.repaint();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	
	
}
