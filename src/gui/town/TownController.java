package gui.town;

import java.awt.Dimension;

import player.Player;

import storage.TemporaryLoader;

import controllers.WorldController;

import world.World;
import world.icons.Party;
import world.towns.City;



public class TownController {

	// state stuff
	private final City city;
	private Party garrison;
	private Party visitors;
	
	// gui stuff
	private TownGui gui;
	
	/**
	 * Construct a new session in Town.
	 * @param city: the city that this session is describing.
	 * @param controller: the world session that created this session.
	 */
	public TownController(City city, WorldController controller){
		this.city = city;
		this.garrison = city.getGarrison();
		this.visitors = city.getVisitors();
		
		Dimension dimensions = controller.getVisualDimensions();
		this.gui = new TownGui(this);
		
	}
	
	public static void main(String[] args){
		Player p = new Player("John",1);
		new TownController(null,null);
	}
	
}
