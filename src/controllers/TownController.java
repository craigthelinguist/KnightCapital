package controllers;

import game.units.Creature;
import game.units.Hero;
import game.units.Unit;

import java.awt.Dimension;
import java.awt.Point;
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



public class TownController{

	// state stuff
	protected final City city;
	protected Party garrison;
	protected Party visitors;
	
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
		this.garrison = city.getGarrison();
		this.visitors = city.getVisitors();
		this.gui = new TownGui(this);
		this.worldController = controller;
	}
	
	public Party getGarrison(){
		
		return garrison;
	}
	
	public Party getVisitors(){
		return visitors;
	}

	public boolean active(){
		return active;
	}

	public void buttonPressed(String text) {
		
		if (!active) return;
		text = text.toLowerCase();
		if (text.equals("leave")){
			gui.dispose();
			worldController.endTownView();
			active = false;
		}
		
	}
	
	protected void mouseMoved(Point from, Point to){
		
	}
	
	protected void mouseEvent(){
		
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
		
		Hero h2 = new Hero("dark_knight",player);
		Unit u3 = new Unit("knight",player);
		Unit u4 = new Unit("archer",player);
		Unit u5 = new Unit("archer",player);
		Unit u6 = new Unit("knight",player);
		Creature[][] members2 = Party.newEmptyParty();
		members2[0][0] = u3;
		members2[1][0] = u6;
		members2[2][0] = h2;
		members2[0][1] = u4;
		members2[2][1] = u5;
		Party party2 = new Party(h2,player,members2);
		
		City city = new City("basic",player,tiles);
		city.setGarrison(party);
		city.setVisitors(party2);
		
		Player p = new Player("John",1);
		TownController tc = new TownController(city,WorldController.getTestWorldControllerNoGui());

	}
}
