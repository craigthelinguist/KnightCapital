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
import storage.generators.TemporaryLoader;
import tools.Geometry;
import world.World;
import world.icons.Party;
import world.tiles.CityTile;
import world.tiles.PassableTile;
import world.tiles.Tile;
import world.towns.City;

/**
@author Ewan Moshi, Aaron Craig
**/
public class WorldTests {

	private World world;
	private Player player1;
	private Player player2;
	private Set<City> cities;

	private static final int INCOME = City.CITY_INCOME;

	/**
	 * Make sure you get income each turn.
	 */
	@Test
	public void testIncome(){
		setup();
		Player current = world.getCurrentPlayer();
		int money1 = player1.getGold();
		int money2 = player2.getGold();
		int income1 = INCOME * numCities(player1);
		int income2 = INCOME * numCities(player2);
		world.endTurn();
		assertEquals(player1.getGold(), money1+income1);
		assertEquals(player2.getGold(), money2);
		world.endTurn();
		assertEquals(player1.getGold(), money1+income1);
		assertEquals(player2.getGold(), money2+income2);
	}

	/**
	 * Make sure it cycles through players.
	 */
	@Test
	public void testEndTurn(){
		setup();
		assertTrue(world.getCurrentPlayer() == player1);
		world.endTurn();
		assertFalse(world.getCurrentPlayer() == player1);
		assertTrue(world.getCurrentPlayer() == player2);
		world.endTurn();
		assertFalse(world.getCurrentPlayer() == player2);
		assertTrue(world.getCurrentPlayer() == player1);
	}

	private int numCities(Player player){
		int count = 0;
		for (City city : cities){
			if (city.ownedBy(player)) count++;
		}
		return count;
	}

	public void setup() {

		player1 = new Player("John the Baptist", 1);
		player2 = new Player("2pac", 2);

		Tile[][] tiles = new Tile[1][1];
		tiles[0][0] = PassableTile.newGrassTile(0, 0);
		cities = new HashSet<>();
		City c1 = new City("Porirua",null,player1,null);
		City c2 = new City("Newtown",null,player1,null);
		City c3 = new City("Kelburn",null,player2,null);
		cities.add(c1);
		cities.add(c2);
		cities.add(c3);


		Player[] players = new Player[]{ player1, player2 };
		world = new World(tiles,players,cities);

	}

}
