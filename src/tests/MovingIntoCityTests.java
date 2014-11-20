package tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import game.units.AttackType;
import game.units.Creature;
import game.units.Hero;
import game.units.HeroStats;
import player.Player;
import renderer.Camera;
import world.World;
import world.icons.Party;
import world.tiles.CityTile;
import world.tiles.PassableTile;
import world.tiles.Tile;
import world.tiles.TileFactory;
import world.towns.City;

public class MovingIntoCityTests {

	private Tile[][] tiles;
	private Player player1; // person trying to move into the city
	private Player player2; // person who owns the city
	private City city;
	private World world;
	private Party movers; // party of people outside the city
	private Point moversLocation; // position of party outsid ethe city
	private Point cityEntrance; // position of tile containing entrance to city

	/**
	 * Test that you can move into your own city.
	 */
	@Test
	public void moveIntoYourEmptyCity(){
		init();
		movers.setOwner(player2);
		city.changeOwner(player2);
		boolean moved = world.moveParty(player2, moversLocation, cityEntrance);
		assertTrue(moved);
		assertFalse(tiles[moversLocation.x][moversLocation.y].occupant() == movers);
		assertTrue(city.getVisitors() == movers);
	}

	/**
	 * Test that you cannot move into your own occupied city.
	 */
	@Test
	public void moveIntoYourOccupiedCity(){
		init();
		fillVisitors();
		Party visitors = city.getVisitors();
		movers.setOwner(player2);
		city.changeOwner(player2);
		boolean moved = world.moveParty(player2, moversLocation, cityEntrance);
		assertFalse(moved);
		assertTrue(tiles[moversLocation.x][moversLocation.y].occupant() == movers);
		assertFalse(movers == city.getVisitors());
		assertTrue(visitors == city.getVisitors());
	}

	/**
	 * Moving into an enemies occupied city changes its owner.
	 */
	@Test
	public void moveIntoEnemyEmptyCity(){
		init();
		movers.setOwner(player1);
		city.changeOwner(player2);
		boolean moved = world.moveParty(player1, moversLocation, cityEntrance);
		assertTrue(moved);
		assertFalse(tiles[moversLocation.x][moversLocation.y].occupant() == movers);
		assertTrue(city.getVisitors() == movers);
		assertFalse(city.getOwner() == player2);
		assertTrue(city.getOwner() == player1);
	}

	/**
	 * Fill the city with visitors.
	 */
	private void fillVisitors(){
		HeroStats stats = new HeroStats(10,10,10,10,10,10,AttackType.RANGED);
		Hero hero = new Hero("Ovelia","ovelia",player2,stats);
		Creature[][] members = Party.newEmptyPartyArray();
		members[0][0] = hero;
		Party party = new Party(hero,player2,members);
		city.setVisitors(party);
	}

	/**
	 * Fill the city with a garrison.
	 */
	private void fillGarrison(){
		HeroStats stats = new HeroStats(10,10,10,10,10,10,AttackType.RANGED);
		Hero hero = new Hero("Ovelia","ovelia",player2,stats);
		Creature[][] members = Party.newEmptyPartyArray();
		members[0][0] = hero;
		Party party = new Party(hero,player2,members);
		city.setGarrison(party);
	}


	/**
	 * Initialise simulation.
	 */
	private void init(){

		player1 = new Player("Whiley Master", 1);
		player2 = new Player("Gaben Angus", 2);
		tiles = new Tile[4][4];

		// populate world with passable grass tiles
		for (int i = 0; i < tiles.length; i++){
			for (int j = 0; j < tiles[i].length; j++){
				tiles[i][j] = TileFactory.newGrassTile(i, j);
			}
		}

		// populate world with city tiles
		CityTile[][] citytiles = new CityTile[3][3];
		for (int y = 0; y < 3; y++){
			for (int x = 1; x < tiles[y].length; x++){
				CityTile ct = new CityTile(x,y);
				tiles[x][y] = ct;
				citytiles[x-1][y] = ct;
			}
		}

		// p-town is home
		city = new City("Porirua", "basic", player2, citytiles);

		// create party outside the city
		HeroStats stats = new HeroStats(10,10,10,10,10,10,AttackType.RANGED);
		Hero hero = new Hero("Ovelia","ovelia",player2,stats);
		Creature[][] members = Party.newEmptyPartyArray();
		members[0][0] = hero;
		movers = new Party(hero,player1,members);
		movers.refresh();
		moversLocation = new Point(1,3);
		cityEntrance = new Point(2,2);
		tiles[moversLocation.x][moversLocation.y].setIcon(movers);

		/*
		for (int y = 0; y < 4; y++){
			System.out.println();
			for (int x = 0; x < 4; x++){

				Tile tile = tiles[x][y];
				if (tile instanceof PassableTile){
					if (tile.occupant() != null) System.out.print("P");
					else System.out.print("T");
				}
				else if (tile instanceof CityTile){
					if (city.getEntryTile() == tile) System.out.print("E");
					else System.out.print("C");
				}

			}
		}*/


		// create world
		Set<City> cities = new HashSet<>();
		cities.add(city);
		Player[] players = new Player[]{ player1, player2 };
		world = new World(tiles,players,cities);

	}

	/**
	 *
	 * [.][C][C][C]
	 * [.][C][C][C]
	 * [.][C][E][C]
	 * [.][P][.][.]
	 *
	 * 		.	passable tile
	 *		C	city tile
	 *		E	entry to city
	 *		P	party outside the city
	 */
}
