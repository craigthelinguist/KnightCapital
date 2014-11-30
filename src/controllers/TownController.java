package controllers;

import game.effects.Buff;
import game.items.Item;
import game.items.PassiveItem;
import game.items.Target;
import game.units.Creature;
import game.units.Hero;
import game.units.HeroStats;
import game.units.Stat;
import game.units.Unit;
import game.units.UnitStats;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import GUI.town.TownGui;
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
	protected final City city;
	protected Item[][] items;

	// gui stuff
	private TownGui gui;
	private boolean active = true; //

	// super
	private WorldController worldController;

	/**
	 * Construct a new session in Town.
	 * @param city: the city that this session is describing.
	 * @param controller: the world session that created this session.
	 */
	public TownController(City city, WorldController controller){
		this.city = city;


		if (city.getGarrison() == null){
			Party g = new Party(null,city.getOwner(),Party.newEmptyPartyArray());
			city.setGarrison(g);
		}
		if (city.getVisitors() == null){
			Party v = new Party(null,city.getOwner(),Party.newEmptyPartyArray());
			city.setVisitors(v);
		}

		this.gui = new TownGui(this);
		this.worldController = controller;
	}

	/**
	 * Return the party garrisoned in the city.
	 * @return: party
	 */
	public Party getGarrison(){
		return city.getGarrison();
	}

	/**
	 * Return the party visiting this city.
	 * @return: party
	 */
	public Party getVisitors(){
		return city.getVisitors();
	}

	/**
	 * Respond to a button press event from the gui.
	 * @param text: name of the button pressed.
	 */
	public void buttonPressed(String text) {

		text = text.toLowerCase();

		// end the town session; reactivate world controller
		if (text.equals("leave")){
			gui.dispose();
			Point exit = getExitPoint();
			worldController.endTownView(exit);
			active = false;
		}

		// if there is a visiting party, eject them to the world map
		else if(text.equals("exit city")) {

			// check there's a party to kick out
			Party visitors = city.getVisitors();
			if (visitors == null || visitors.isEmpty()) return;

			// find tile to put them on
			World world = worldController.getWorld();
			Point pt = getExitPoint();

			// check tile exists, kick them onto world map
			if (pt.x >= 0 && pt.y >= 0 && pt.x < world.NUM_TILES_ACROSS && pt.y < world.NUM_TILES_DOWN){
				Tile tile = world.getTile(pt);
				if (!tile.canStandOn(visitors)) return;
				tile.setIcon(visitors);
				city.setVisitors(Party.newEmptyParty(city.getOwner()));
				visitors = city.getVisitors();
				this.gui.repaint();
			}
		}

	}

	/**
	 * Get the point in the world map's array that is the exit tile for the city.
	 * @return: a point.
	 */
	private Point getExitPoint(){
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
