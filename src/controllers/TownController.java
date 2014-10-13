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
import storage.TemporaryLoader;
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
			Party g = new Party(null,city.getOwner(),Party.newEmptyParty());
			city.setGarrison(g);
		}
		if (city.getVisitors() == null){
			Party v = new Party(null,city.getOwner(),Party.newEmptyParty());
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
			worldController.endTownView();
			active = false;
		}
		else if(text.equals("exit city")) {
			
			// check there's a party to kick out
			Party visitors = city.getVisitors();
			if (visitors == null || visitors.isEmpty()) return;
			
			// find tile to put them on
			Camera camera = worldController.getCamera();
			World world = worldController.getWorld();
			Tile tile = city.getEntryTile();
			System.out.println("CITY ENTRY: ("+tile.X+","+tile.Y+")");
			Point pt = tile.asPoint();
			pt = new Point(pt.x,pt.y+1);
			pt = Geometry.rotateByCamera(pt, camera, worldController.getVisualDimensions());
			
			// check tile exists, kick them onto world map
			if (pt.x >= 0 && pt.y >= 0 && pt.x < world.NUM_TILES_ACROSS && pt.y < world.NUM_TILES_DOWN){
				tile = world.getTile(pt);
				if (!tile.passable(visitors)) return;
				System.out.println("CITY EXIT: ("+tile.X+","+tile.Y+")");
				tile.setIcon(visitors);
				city.setVisitors(this.newEmptyParty());
				visitors = city.getVisitors();
				this.gui.repaint();
			}
			
		}

	}

	private Party newEmptyParty(){
		return new Party(null, city.getOwner(), Party.newEmptyParty());
	}
	
	protected void mouseMoved(Point from, Point to){

	}

	protected void mouseEvent(){

	}


	public static void main(String[] args){

		// party
		Player player = new Player("Pondy",1);
		Unit u1 = new Unit("knight",player, new UnitStats(100,25,40,0));
		Unit u2 = new Unit("knight",player, new UnitStats(100,25,40,0));
		Hero h1 = new Hero("ovelia",player, new HeroStats(80,10,80,0,8,8));
		Creature[][] members = Party.newEmptyParty();
		members[0][0] = u1;
		members[1][0] = h1;
		members[2][0] = u2;
		Party party = new Party(h1,player,members);

		//party

		Hero h2 = new Hero("dark_knight",player,new HeroStats(140,35,55,5,8,6));
		Unit u3 = new Unit("knight",player,new UnitStats(100,25,40,1));
		Unit u4 = new Unit("archer",player,new UnitStats(60,15,70,0));
		Unit u5 = new Unit("archer",player,new UnitStats(60,15,70,0));
		Unit u6 = new Unit("knight",player,new UnitStats(100,25,40,1));
		Creature[][] members2 = Party.newEmptyParty();
		members2[0][0] = u3;
		members2[1][0] = u6;
		members2[2][0] = h2;
		members2[0][1] = u4;
		members2[2][1] = u5;
		Party party2 = new Party(h2,player,members2);

		// items
		Buff[] buffsWeapon = new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,5), Buff.newTempBuff(Stat.ARMOUR, 10) };
		PassiveItem weapon = new PassiveItem("Weapon", "weapon", "A powerful weapon crafted by the mighty Mizza +5 Damage",buffsWeapon, Target.HERO);
		Buff[] buffsArrows= new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,1) };
		PassiveItem arrows = new PassiveItem("Poison Arrows", "poisonarrow", "Poisonous arrows whose feathers were made from the hairs of Mizza. All archers in party gain +1 damage",buffsArrows, Target.PARTY);
		party.addItem(weapon);
		party.addItem(arrows);

		// city
		CityTile[][] tiles = new CityTile[3][3];
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				tiles[i][j] = new CityTile(i,j);
			}
		}
		City city = new City("Porirua","basic",player,tiles);
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
