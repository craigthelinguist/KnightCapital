package controllers;

import game.effects.Buff;
import game.items.PassiveItem;
import game.units.Creature;
import game.units.Hero;
import game.units.Stat;
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
		
		if (city.getGarrison() == null){
			Party g = new Party(null,city.getOwner(),Party.newEmptyParty());
			city.setGarrison(g);
		}
		if (city.getVisitors() == null){
			Party v = new Party(null,city.getOwner(),Party.newEmptyParty());
			city.setVisitors(v);
		}
		
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

		// party
		Player player = new Player("Pondy",1);
		Unit u1 = new Unit("knight",player,10,3,3);
		Unit u2 = new Unit("knight",player,10,3,3);
		Hero h1 = new Hero("ovelia",player,50,10,10);
		Creature[][] members = Party.newEmptyParty();
		members[0][0] = u1;
		members[1][0] = h1;
		members[2][0] = u2;
		Party party = new Party(h1,player,members);

		//party
		Hero h2 = new Hero("dark_knight",player,50,10,10);
		Unit u3 = new Unit("knight",player,10,3,3);
		Unit u4 = new Unit("archer",player,10,3,3);
		Unit u5 = new Unit("archer",player,10,3,3);
		Unit u6 = new Unit("knight",player,10,3,3);
		Creature[][] members2 = Party.newEmptyParty();
		members2[0][0] = u3;
		members2[1][0] = u6;
		members2[2][0] = h2;
		members2[0][1] = u4;
		members2[2][1] = u5;
		Party party2 = new Party(h2,player,members2);
		
		// items
		Buff[] buffsWeapon = new Buff[]{ new Buff(Stat.DAMAGE,5,true), new Buff(Stat.ARMOUR, 10, true) };
		PassiveItem weapon = new PassiveItem(buffsWeapon, "weapon", "Weapon","A powerful weapon crafted by the mighty Mizza +5 Damage");
		Buff[] buffsArrows= new Buff[]{ new Buff(Stat.DAMAGE,1,true) };
		PassiveItem arrows = new PassiveItem(buffsArrows, "poisonarrow", "Poison Arrows","Poisonous arrows whose feathers were made from the hairs of Mizza. All archers in party gain +1 damage");
		party.addItem(weapon);
		party.addItem(arrows);
		
		// city
		CityTile[][] tiles = new CityTile[3][3];
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				tiles[i][j] = new CityTile(i,j);
			}
		}
		City city = new City("basic",player,tiles);
		city.setGarrison(party);
		city.setVisitors(party2);
		
		// player + controller
		Player p = new Player("John",1);
		TownController tc = new TownController(city,WorldController.getTestWorldControllerNoGui());

	}

	public City getCity() {
		return this.city;
	}
}
