package controllers;

import game.units.Creature;
import game.units.Hero;
import game.units.Unit;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import GUI.town.TownGui;

import player.Player;

import storage.TemporaryLoader;
import tools.Log;


import world.World;
import world.icons.Party;
import world.tiles.CityTile;
import world.towns.City;



public class TownController implements Controller {

	// state stuff
	protected final City city;
	protected Party garrison;
	protected Party visitors;
	
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
		
		this.gui = new TownGui(this);
		
	}
	
	public Party getGarrison(){
		return garrison;
	}
	
	public Party getVisitors(){
		return visitors;
	}
	
	public static void main(String[] args){

		Player player = new Player("Pondy",1);
		Unit u1 = new Unit("knight",player);
		Unit u2 = new Unit("knight",player);
		Hero h1 = new Hero("ovelia",player);
		Creature[][] members = Party.newEmptyParty();
		members[0][0] = u1;
		members[1][0] = h1;
		members[2][0] = u2;
		Party party = new Party(h1,player,members);
		CityTile[][] tiles = new CityTile[3][3];
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				tiles[i][j] = new CityTile(i,j);
			}
		}
		
		City city = new City("basic",player,tiles);
		city.setGarrison(party);

		Player p = new Player("John",1);
		TownController tc = new TownController(city,WorldController.testWorldControllerNoGui());

	}

	@Override
	public void buttonPressed(JButton button, Object[] info) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent me, Object[] info) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent ke, Object[] info) {
		// TODO Auto-generated method stub
		
	}
	
}
