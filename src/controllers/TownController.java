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



public class TownController{

	// state stuff
	protected final City city;
	protected Item[][] items;

	// gui stuff
	private TownGui gui;
	private boolean active = true;

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

	public Party getGarrison(){
		return city.getGarrison();
	}

	public Party getVisitors(){
		return city.getVisitors();
	}

	public boolean active(){
		return active;
	}

	public void buttonPressed(String text) {
		if (!active) return;
		text = text.toLowerCase();
		if (text.equals("leave")){
			gui.dispose();
			Point exit = getExitPoint();
			worldController.endTownView(exit);
			active = false;
		}
		else if(text.equals("exit city")) {

			// check there's a party to kick out
			Party visitors = city.getVisitors();
			if (visitors == null || visitors.isEmpty()) return;

			// find tile to put them on
			Camera camera = worldController.getCamera();
			World world = worldController.getWorld();

			Point pt = getExitPoint();

			// check tile exists, kick them onto world map
			if (pt.x >= 0 && pt.y >= 0 && pt.x < world.NUM_TILES_ACROSS && pt.y < world.NUM_TILES_DOWN){
				Tile tile = world.getTile(pt);
				if (!tile.passable()) return;
				tile.setIcon(visitors);
				city.setVisitors(this.newEmptyParty());
				visitors = city.getVisitors();
				this.gui.repaint();
			}

		}

	}

	private Point getExitPoint(){
		Tile tile = city.getEntryTile();
		Point pt = tile.asPoint();
		pt.y = pt.y + 1;
		return pt;
	}

	private Party newEmptyParty(){
		return new Party(null, city.getOwner(), Party.newEmptyPartyArray());
	}

	protected void mouseMoved(Point from, Point to){

	}

	protected void mouseEvent(){

	}

	public City getCity() {
		return this.city;
	}
}
