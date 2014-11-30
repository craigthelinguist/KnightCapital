package world.tiles;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import player.Player;

import tools.ImageManipulation;
import world.icons.Party;
import world.icons.WorldIcon;
import world.towns.City;

/**
 * A CityTile belongs to a city. They collectively make up the same city.
 * @author craigthelinguist
 *
 */
public class CityTile extends Tile {

	// city that this CityTile is apart of.
	private City city;

	/**
	 * Constructs a new City Tile
	 * @param x coordinate
	 * @param y coordinate
	 */
	public CityTile(int x, int y){
		super(x,y);
	}

	/**
	 * Set this city tile to be referenced to supplied city
	 * @param city to point to
	 */
	public void setCity(City newCity){
		if (city != null){
			throw new RuntimeException("you're setting a cityTile's city after it has already been set!");
		}
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
	public String asString() {
		return "city";
	}

	@Override
	public boolean canStandOn(Party party) {
		if (city.getEntryTile() != this) return false;
		Player partyOwner = party.getOwner();
		Player cityOwner = city.getOwner();
		if (partyOwner != cityOwner && city.isEmpty()) return true;
		if (partyOwner == cityOwner && !city.hasVisitors()) return true;
		return false;
	}
	
	@Override
	public boolean isPassable(Party party) {
		return canStandOn(party);
	}

}
