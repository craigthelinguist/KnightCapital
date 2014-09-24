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
import tools.Log;
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


	// This shouldn't be here is final - myles
	private static int rotation = 0;

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

		// At this point rotate is called every render cycle, but this isn't very efficient.
		tiles = rotateArray(tiles);

		for (int y = 0; y < tiles.length; y++){
			for (int x = 0; x < tiles[0].length; x++){
				BufferedImage bi = tiles[x][y].getImage();
				int isoX = origin_x + (TILE_WD/2)*x - (TILE_WD/2)*y;
				int isoY = origin_y + (TILE_HT/2)*x + (TILE_HT/2)*y;
				tiles[x][y].draw(graphics,isoX,isoY);
			}
		}

		for (int y = 0; y < tiles.length; y++){
			for (int x = 0; x < tiles[0].length; x++){
				if (!tiles[x][y].occupied()) continue;
				int isoX = origin_x + (TILE_WD/2)*x - (TILE_WD/2)*y;
				int isoY = origin_y + (TILE_HT/2)*x + (TILE_HT/2)*y;
				drawIcon(graphics,tiles[x][y],isoX,isoY);
			}
		}

		// Some basic debug info
		graphics.setColor(Color.BLACK);
		graphics.drawString("Knight Capital", 30, 30);
		graphics.drawString("Current Rotation: "+(rotation * 90), 30, 50);
		graphics.drawString("Current Origin: ("+origin_x+","+origin_y+")", 30, 70);
		graphics.drawString("Arrow keys to move camera", 30, 110);
		graphics.drawString("Press r to rotate", 30, 130);

	}

	/**
	 * Rotates the tiles in a clockwise direction
	 * Uses the field rotation to know how much to rotate by
	 * @param tiles
	 * @return rotates array
	 */
	private static AbstractTile[][] rotateArray(AbstractTile[][] tiles) {
		int width = tiles.length;
		int height = tiles[0].length;

		//Log.print("[WorldRenderer] Rotating tiles by "+(rotation*90));

		// New 2DArray for rotated tiles, not efficient but will do for now.
		AbstractTile[][] rotated = null;

		// Rotate 90 Clockwise for each rotation count. If 0 do nothing;
		for(int rotateCount = 0; rotateCount != rotation; rotateCount++) {
			rotated = new AbstractTile[height][width];

			// Rotate tiles by 90 cw
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					rotated[i][j] = tiles[j][10 - i - 1];
				}
			}

			tiles = rotated; // without this it would only ever rotate by 0 or 90
		}

		if(rotated != null) return rotated;
		else return tiles;
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

	/**
	 * Convert a point (from a mouse click) on screen in isometric projection,
	 * and return a new point converted to Cartesian coordinates.
	 * Used to identify tiles.
	 * @param Point to convert
	 * @return Converted Cartesian Point
	 */
	private static Point isoToCartesian(float isoX, float isoY){

		// Window widths and heights
		int tileHeight = GlobalConstants.TILE_HT;
		int tileWidth = GlobalConstants.TILE_WD;

		float yPos = isoY - origin_y;
		float xPos = isoX - origin_x;

		float offset = 0.13f; //this is super hacky, my bad FIXME

		// Calculate Cartesian X
		float cartX = (((2 * yPos) + xPos) / tileWidth) - 1;
		cartX -= ((int)cartX * offset);

		// Calculate Cartesian Y
		float cartY = (((2 * yPos) - xPos) / tileWidth);
		cartY -= ((int)cartY * offset);

		Log.print("[World Renderer] Converted isometric point ("+isoX+","+isoY+") to cartesian point ("+(int)cartX+","+(int)cartY+")");
		return new Point((int)cartX, (int)cartY);
	}

	// for testing purposes, creates a frame and lets you key around in it - Aaron
	private static int oveliaX = 3;
	private static int oveliaY = 0;
	public static void main(String[] args){
		Log.printTitle();

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
				// Rotation Binding
				else if (code == KeyEvent.VK_R) {
					if(rotation == 3) rotation = 0;
					else rotation = rotation + 1;
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
				// get mouse point x
				int x = arg0.getX();

				//get mouse point y
				int y = arg0.getY();

				//create point, converting from iso to cart
				Point p = isoToCartesian(x, y);

				//
				int arrayX = p.x;
				int arrayY = p.y;

				// if the point is within the world boundaries
				if (arrayX>=0 && arrayX<world.NUM_TILES_ACROSS
						&& arrayY>=0 && arrayY<world.NUM_TILES_DOWN){

					// remove player icon from previous position
					world.setIcon(null,oveliaX,oveliaY);

					// add player icon to next position
					world.setIcon(ovelia, arrayX, arrayY);

					// update icon position
					oveliaX = arrayX;
					oveliaY = arrayY;
				}
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
