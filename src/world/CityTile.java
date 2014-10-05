package world;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import tools.ImageManipulation;

/**
 * A CityTile belongs to a city. They collectively make up the same city.
 * @author craigthelinguist
 *
 */
public class CityTile extends Tile {

	// city that this CityTile is apart of.
	private City city;

	public CityTile(int x, int y){
		super(x,y);
	}
	
	public void setCity(City newCity){
		city = newCity;
	}
	
	/**
	 * Get the city this tile is apart of.
	 * @return: a city.
	 */
	public City getCity(){
		return city;
	}
	
	@Override
	public void draw(Graphics g, int x, int y){
		// do nothing - call draw on the city
	}
	
	@Override
	public void drawHighlighted(Graphics graphics, int x, int y, int intensity) {
		// do nothing - call draw on the city.
	}
	
	@Override
	public WorldIcon occupant(){
		return null;
	}
	
	@Override
	public boolean passable(Party party) {
		// TODO: this should return true if the tile is the entrance to the city.
		// that way world will put the party inside the city garrison.
		return false;
	}
		
}
