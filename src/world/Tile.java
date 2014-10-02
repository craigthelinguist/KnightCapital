package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;


/**
 * Represents one cell on the World. Different implementations of Tile might have different rules (e.g.:
 * parties can stand on it, tile belongs to a city, so on).
 * @author: craigthelinguist
 */
public abstract class Tile {

	protected BufferedImage image;
	protected WorldIcon occupant = null;
	
	public BufferedImage getImage(){
		return image;
	}

	/**
	 * Check if you can stand on this tile.
	 * @return: true if the specified party is allowed to stand on this tile.
	 */
	public abstract boolean passable(Party party);
	
	public boolean occupied(){
		return occupant != null;
	}
	
	public WorldIcon occupant(){
		return occupant;
	}

	public void draw(Graphics g, int x, int y){
		g.drawImage(image, x, y, null);
	}
	
	public void setIcon(WorldIcon i){
		occupant = i;
	}

	/**
	 * Draws this tile, but a little lighter.
	 * @param graphics
	 * @param x
	 * @param y
	 */
	public void drawHighlighted(Graphics graphics, int x, int y) {
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		BufferedImage bi = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		for (int i = 0; i < bi.getWidth(); i++){
			for (int j = 0; j < bi.getHeight(); j++){
				
				int pixel = bi.getRGB(i, j);
				int alpha = (pixel>>24) & 0xff;
				Color color = new Color(pixel);
				Color lightened = lighten(color);
				bi.setRGB(i, j, lightened.getRGB());
				
			}
		}
		graphics.drawImage(bi, x, y, null);
	}
	
	private Color lighten(Color col){
		int increment = 30;
		int r = Math.min(255,col.getRed()+increment);
		int g = Math.min(255,col.getGreen()+increment);
		int b = Math.min(255,col.getBlue()+increment);
		return new Color(r,g,b);
	}
	
}
