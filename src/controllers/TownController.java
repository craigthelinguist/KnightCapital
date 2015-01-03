package controllers;

import game.effects.Buff;
import game.items.Item;
import game.items.PassiveItem;
import game.items.Target;
import game.units.creatures.Creature;
import game.units.creatures.Hero;
import game.units.creatures.Unit;
import game.units.stats.HeroStats;
import game.units.stats.Stat;
import game.units.stats.UnitStats;
import gui.town.TownPanel;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import player.Player;
import renderer.Camera;
import storage.generators.TemporaryLoader;
import tools.Geometry;
import tools.Log;
import world.World;
import world.icons.Party;
import world.tiles.CityTile;
import world.tiles.Tile;
import world.towns.City;

/**
 * This is the controller for the town session. It handles gui interactions and updates the town view
 * accordingly.
 * @author Aaron
 */
public class TownController{

	// state stuff
	private final World world;
	protected final City city;
	
	// gui stuff
	private TownPanel townPanel;

	/**
	 * Construct a new session in Town.
	 * @param city: the city that this session is describing.
	 * @param controller: the world session that created this session.
	 */
	public TownController(TownPanel townPanel, World world, City city){
		this.city = city;
		this.world = world;
		this.townPanel = townPanel;

		if (city.getGarrison() == null){
			Party g = new Party(null,city.getOwner(),Party.newEmptyPartyArray());
			city.setGarrison(g);
		}
		if (city.getVisitors() == null){
			Party v = new Party(null,city.getOwner(),Party.newEmptyPartyArray());
			city.setVisitors(v);
		}

	}

	/**
	 * Respond to a button press event from the gui.
	 * @param text: name of the button pressed.
	 */
	public void buttonPressed(String button) {

		if (button.equals("Leave")) townPanel.endTownView();
		else if (button.equals("Eject")) ejectVisitors();
		else if (button.equals("Train")) {}
		else if (button.equals("Production")) {}
			
	}

	/**
	 * Eject the visiting party onto the world map. If there is no visiting party or the visiting party
	 * has no space to be ejected onto the world map, this method does nothing.
	 */
	private void ejectVisitors(){

		// check there's a party to kick out
		Party visitors = city.getVisitors();
		if (visitors == null || visitors.isEmpty()) return;
		
		// find tile to put them on
		Point pt = getExitPoint(world);
		if (!world.pointWithinWorld(pt)) return;
		
		// eject party
		Tile tile = world.getTile(pt);
		if (!tile.canStandOn(visitors)) return;
		tile.setIcon(visitors);
		city.setVisitors(Party.newEmptyParty(city.getOwner()));
		visitors = city.getVisitors();
		townPanel.repaint();
		
	}
	
	/**
	 * Get the point in the world map's array that is the exit tile for the city.
	 * @return Point
	 */
	private Point getExitPoint(World world){
		Tile tile = city.getEntryTile();
		Point pt = tile.asPoint();
		pt.y = pt.y + 1;
		return pt;
	}

	/**
	 * Get the city this world controller is attached to.
	 * @return: city.
	 */
	public City getCity() {
		return this.city;
	}
}
