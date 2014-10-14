package tests;

import static org.junit.Assert.*;
import game.units.AttackType;
import game.units.Creature;
import game.units.Hero;
import game.units.HeroStats;
import game.units.Unit;
import game.units.UnitStats;

import java.awt.Point;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;

import org.junit.Test;

import player.Player;
import controllers.WorldController;
import renderer.Camera;
import storage.TemporaryLoader;
import tools.Geometry;
import world.World;
import world.icons.Party;
import world.tiles.CityTile;
import world.tiles.PassableTile;
import world.tiles.Tile;
import world.towns.City;

/**
@author Ewan Moshi
**/
public class IncomeTest {

	private WorldController controller;
	private World w;
	private Player p;
	private Set<City> cities;

	/**
	 * Create a player with standard gold and then change his gold. Check if
	 * it changed correctly.
	 */
	@Test
	public void Test_ChangingGold() {
		setupWorld();
		int goldb4 = p.getGold();
		p.increaseGold(500); //increase gold by 500
		assertTrue(p.getGold() == goldb4 + 500); //check that the gold is increased amount + his starting gold
	}

	/**
	 * Player gains 100 gold per turn for every city they own.
	 * This tests if player is gaining correct amount per city.
	 * This test, player has no cities owned so they should not gain any gold.
	 */
	@Test
	public void Test_No_Gold_Increase() {
		setupWorld();
		int goldb4 = p.getGold();
		/*Iterate over all the cities in the world and set their owner to null*/
		for(City city : w.getCities()) {
			if(city.getOwner() != null) {
				city.changeOwner(null);
			}
		}
		w.endTurn();
		//check that the gold has not increased
		assertTrue(p.getGold() == goldb4);
	}

	/**
	 * Player gains 100 gold per turn for every city they own.
	 * This tests if player is gaining correct amount per city.
	 */
	@Test
	public void Test_Gold_Increase_From_City() {
		setupWorld();
		w.endTurn();
		assertTrue(p.getGold() != 10); //check that the gold has not increased
		assertTrue(p.getGold() == 110); //check that the player gained 100 gold from 1 city.
	}

	/**
	 * Player gains 100 gold per turn for every city they own.
	 * This tests if player is gaining correct amount per city.
	 */
	@Test
	public void Test_Gold_Increase_From_Cities() {
		setupWorld();


		/**
		 * This creates a custom world with more than one city and test income gain on more than one city.
		 *
		 */
		cities = new HashSet<>();
		cities.add(new City("Porirua","basic",p,null));
		cities.add(new City("KreTown","basic",p,null));
		cities.add(new City("Tawa","basic",p,null));

		// world data
		Player[] players = new Player[]{ p };

		// create tiles
		Tile[][] tiles = null;

		tiles = new Tile[10][10];
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++) {
				tiles[x][y] = PassableTile.newGrassTile(x, y);
			}
		}


		// World(Tile[][] tiles_, Player[] playersArray, Set<City> citySet){
		w = new World(tiles,players,cities);


		w.endTurn();


		assertTrue(p.getGold() != 10); //check that the gold has not increased
		System.out.println(p.getGold());
		assertTrue(p.getGold() == 310); //check that the player gained 100 gold from 1 city.
	}


	public void setupWorld() {
		/*Loading the player*/
		p = new Player("John The Baptist",4);

		w = TemporaryLoader.loadWorld("world_temporary.txt",p);


		HeroStats stats_hero = new HeroStats(60,10,80,0,6,8,AttackType.MELEE);
		Hero hero = new Hero("ovelia","ovelia",p,stats_hero);

		hero.setMovePts(10);

		Unit u3 = new Unit("knight","knight",p,new UnitStats(100,25,40,1,AttackType.MELEE));
		Unit u4 = new Unit("archer","archer",p,new UnitStats(60,15,70,0,AttackType.RANGED));
		Unit u5 = new Unit("archer","archer",p,new UnitStats(60,15,70,0,AttackType.RANGED));
		Unit u6 = new Unit("knight","knight",p,new UnitStats(100,25,40,1,AttackType.MELEE));
		Creature[][] members2 = Party.newEmptyParty();
		members2[0][0] = u3;
		members2[1][0] = u6;
		members2[0][2] = hero;
		members2[0][1] = u4;
		members2[1][2] = u5;
		Party party = new Party(hero,p,members2);
		party.refresh();


		w.getTile(0,0).setIcon(party);
		controller = WorldController.getTestWorldControllerNoGui();
	}

}
