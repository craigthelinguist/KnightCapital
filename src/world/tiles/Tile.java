package world.tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

import renderer.AnimationMap;
import tools.Constants;
import tools.ImageLoader;
import tools.ImageManipulation;
import world.icons.Party;
import world.icons.WorldIcon;


/**
 * Represents one cell on the World. Different implementations of Tile might have different rules (e.g.:
 * parties can stand on it, tile belongs to a city, so on).
 * @author: craigthelinguist
 */
public abstract class Tile {

	private AnimationMap animations;
	protected WorldIcon occupant = null;
	public final int X;
	public final int Y;
	private String imageName;

	protected Tile(int x, int y){
		X = x;
		Y = y;
		animations = new AnimationMap();
	}
	
	protected Tile(String imgName, int x, int y){
		X = x; Y = y;
		imageName = imgName;
		animations = new AnimationMap();
		String tile_filepath = Constants.ASSETS_TILES + imgName;
		animations.addImage("world",tile_filepath,ImageLoader.load(tile_filepath));
		String portrait_filepath = Constants.PORTRAITS + imgName;
		animations.addImage("portrait",portrait_filepath,ImageLoader.load(portrait_filepath));
	}

	/**
	 * Return the image representing this tile on the world map.
	 * @return a buffered image
	 */
	public BufferedImage getImage(){
		return animations.getImage("world");
	}

	/**
	 * Return the portrait representing this tile in the info panel.
	 * @return a buffered image
	 */
	public BufferedImage getPortrait(){
		return animations.getImage("portrait");
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
		g.drawImage(animations.getImage("world"), x, y, null);
	}

	public void setIcon(WorldIcon i){
		occupant = i;
	}

	public void addPortrait(String filepath, BufferedImage bi){
		this.animations.addImage("portrait", filepath, bi);
	}

	public void addImage(String filepath, BufferedImage bi){
		this.animations.addImage("world", filepath, bi);
	}

	/**
	 * Draws this tile, but a little lighter.
	 * @param graphics
	 * @param x
	 * @param y
	 */
	public void drawHighlighted(Graphics graphics, int x, int y, int intensity) {
		BufferedImage lighterImage = ImageManipulation.lighten(animations.getImage("world"), intensity);
		graphics.drawImage(lighterImage,x,y,null);
	}

}
