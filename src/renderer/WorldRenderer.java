package renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import storage.TemporaryLoader;
import tools.GlobalConstants;
import tools.ImageLoader;
import world.AbstractTile;
import world.Party;
import world.WorldIcon;
import world.World;

public class WorldRenderer {
	
	// use the static methods
	private WorldRenderer(){}
	
	// perhaps in the real thing this should be stored somewhere else - Aaron
	private static int origin_x = 0;
	private static int origin_y = 0;
	
	/**
	 * Draw the world on the given graphics object.
	 * @param world: the world to draw
	 * @param graphics: thing on which you're drawing
	 * @param resolution: size of screen being drawn to
	 */
	public static void render(World world, Graphics graphics, Dimension resolution){
		
		final int TILE_WD = GlobalConstants.TILE_WD;
		final int TILE_HT = GlobalConstants.TILE_HT;
		AbstractTile[][] tiles = world.getTiles();		
		for (int y = 0; y < world.NUM_TILES_ACROSS; y++){
			for (int x = 0; x < world.NUM_TILES_DOWN; x++){
				BufferedImage bi = tiles[x][y].getImage();
				int isoX = origin_x + (TILE_WD/2)*x - (TILE_WD/2)*y;
				int isoY = origin_y + (TILE_HT/2)*x + (TILE_HT/2)*y;
				tiles[x][y].draw(graphics,isoX,isoY);
			}
		}
		
		for (int y = 0; y < world.NUM_TILES_ACROSS; y++){
			for (int x = 0; x < world.NUM_TILES_DOWN; x++){
				if (!tiles[x][y].occupied()) continue;
				int isoX = origin_x + (TILE_WD/2)*x - (TILE_WD/2)*y;
				int isoY = origin_y + (TILE_HT/2)*x + (TILE_HT/2)*y;
				drawIcon(graphics,tiles[x][y],isoX,isoY);
			}
		}
		
		
	}
	
	private static void drawIcon(Graphics graphics, AbstractTile tile, int isoX, int isoY){
		final int TILE_HT = GlobalConstants.TILE_HT;
		final int TILE_WD = GlobalConstants.TILE_WD;
		final int ICON_WD = GlobalConstants.ICON_WD;
		final int ICON_HT = GlobalConstants.ICON_HT;
		WorldIcon occupant = tile.occupant();	
		int iconY = isoY - TILE_HT/4;
		int iconX = isoX + TILE_WD/2 - ICON_WD/2;
		occupant.draw(graphics,iconX,iconY);
	}
	
	private static Point isoToCartesian(Point p){
		int isoX = p.x;
		int isoY = p.y;
		final int HT = GlobalConstants.TILE_HT/2;
		final int WD = GlobalConstants.TILE_WD/2;
		
		int x = ((HT)*(isoX-origin_x) + (WD)*(isoY-origin_y))/(2*HT*WD)-1;
		int y = ((WD)*(isoY-origin_y) + (HT)*(origin_x-isoX))/(2*HT*WD);
		
		return new Point(x,y);
	}
	
	// for testing purposes, creates a frame and lets you key around in it - Aaron
	private static int oveliaX = 3;
	private static int oveliaY = 0;
	public static void main(String[] args){
		
		
		final World world = TemporaryLoader.loadWorld("world_temporary.txt");
		final Party ovelia = new Party("icon_ovelia.png");
		world.setIcon(ovelia,oveliaX,oveliaY);
		
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
					origin_x += 10;
				}
				else if (code == KeyEvent.VK_RIGHT){
					origin_x -= 10;
				}
				else if (code == KeyEvent.VK_DOWN){
					origin_y -= 10;
				}
				else if (code == KeyEvent.VK_UP){
					origin_y += 10;
				}
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
		
		frame.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int x = arg0.getX();
				int y = arg0.getY();
				Point p = isoToCartesian(new Point(x,y));
				int arrayX = p.x;
				int arrayY = p.y;
				if (arrayX>=0 && arrayX<world.NUM_TILES_ACROSS
						&& arrayY>=0 && arrayY<world.NUM_TILES_DOWN){
					world.setIcon(null,oveliaX,oveliaY);
					world.setIcon(ovelia, arrayX, arrayY);
					oveliaX = arrayX; oveliaY = arrayY;
				}
				System.out.printf("(%d,%d)\n", p.x, p.y);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		frame.add(panel);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	
	
}
