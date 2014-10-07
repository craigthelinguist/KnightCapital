package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import game.units.Hero;

import org.junit.*;

import player.Player;

import world.World;
import world.icons.Party;
import world.tiles.ImpassableTile;
import world.tiles.PassableTile;
import world.tiles.Tile;

/**
 * Tests pathfinding in the World class.
 * @author craigthelinguist
 *
 */
public class PathfindingTests {

	World world;
	Player player;
	Hero hero;
	Party party;
	Point start = new Point(0,0);
	
	/**
	 * Movement from one tile to the same tile.
	 */
	@Test
	public void validSameTile(){
		init();
		assertTrue("initial conditions should be true at start", initialConditions());
		int movesb4 = party.getMovePoints();
		assertTrue("should be able to move to tile you're standing on", world.getTile(start).occupied());
		assertTrue("you're on same tile so init conditions should be same", initialConditions());
		assertTrue("still on same tile so shouldn't have used any move pts", movesb4 == party.getMovePoints());
	}
	
	/**
	 * Movement to a valid tile.
	 */
	@Test
	public void validOneTileAway(){
		init();
		assertTrue("initial conditions should be true at start", initialConditions());
		int movesb4 = party.getMovePoints();
		Point goal = new Point(1,0);
		assertTrue("should be able to move 1 tile away to a passable tile", world.moveParty(player, start, goal));
		assertEquals("you should have lost 1 move point for moving 1 tile away",party.getMovePoints(),movesb4-1);
		assertFalse("have moved so init conditions should no longer be true", initialConditions());
		assertTrue("goal tile should now be occupied", world.getTile(goal).occupied());
		assertTrue("your party should now be occupant in goal tile", world.getTile(goal).occupant() == party);
	}
	
	/**
	 * Movement to an impassable tile.
	 */
	@Test
	public void invalidOneTileAway(){
		init();
		assertTrue("initial conditions should be true at start", initialConditions());
		int movesb4 = party.getMovePoints();
		Point goal = new Point(0,1);
		assertFalse("shouldn't be able to move to impassable tile", world.moveParty(player, start, goal));
		assertEquals("shouldn't have lost move point", party.getMovePoints(), movesb4);
		assertTrue("haven't moved so init conditions should still be true", initialConditions());
		assertFalse("invalid move so goal tile shouldn't be occupied", world.getTile(goal).occupied());
	}
	
	/**
	 * This is a movement to a passable tile that is surrounded by impassable tiles.
	 */
	@Test
	public void invalidInaccessibleGoal(){
		init();
		assertTrue("initial conditions should be true at start", initialConditions());
		int movesb4 = party.getMovePoints();
		Point goal = new Point(0,2);
		assertFalse("tile isn't accessible", world.moveParty(player, start, goal));
		assertEquals("shouldn't have lost move point", party.getMovePoints(), movesb4);
		assertTrue("haven't moved so init conditions should still be true", initialConditions());
		assertFalse("invalid move so goal tile shouldn't be occupied", world.getTile(goal).occupied());
	}
	
	/**
	 * valid move to a far away tile
	 */
	@Test
	public void validFarAway(){
		init();
		assertTrue("initial conditions should be true at start", initialConditions());
		int movesb4 = party.getMovePoints();
		Point goal = new Point(2,2);
		assertTrue("should be able to move to a passable tile", world.moveParty(player, start, goal));
		assertEquals("you should have lost 4 move point for moving 4 tiles away",movesb4-4,party.getMovePoints());
		assertFalse("have moved so init conditions should no longer be true", initialConditions());
		assertTrue("goal tile should now be occupied", world.getTile(goal).occupied());
		assertTrue("your party should now be occupant in goal tile", world.getTile(goal).occupant() == party);
	}
	
	@Test
	public void invalidFarAway(){
		init();
		assertTrue("initial conditions should be true at start", initialConditions());
		int movesb4 = party.getMovePoints();
		Point goal = new Point(3,3);
		assertFalse("tile is too far away so shouldn't be able to move", world.moveParty(player, start, goal));
		assertEquals("shouldn't have lost move point for invalid move", party.getMovePoints(), movesb4);
		assertTrue("haven't moved so init conditions should still be true", initialConditions());
		assertFalse("invalid move so goal tile shouldn't be occupied", world.getTile(goal).occupied());
	}
	
	public boolean initialConditions(){
		if (!(party.ledBy(hero))) return false;
		if (!(party.getMovePoints() > 0)) return false;
		if (!(world.getTile(start).occupied())) return false;
		if (!(world.getTile(start).occupant() == party)) return false;
		return true;
	}

	public void init(){
		Tile[][] tiles = new Tile[4][4];
		tiles[0][0] = PassableTile.newGrassTile(0,0);
		tiles[0][1] = ImpassableTile.newVoidTile(0,1);
		tiles[0][2] = PassableTile.newGrassTile(0,2);
		tiles[0][3] = ImpassableTile.newVoidTile(0,3);
		tiles[1][0] = PassableTile.newGrassTile(1,0);
		tiles[1][1] = ImpassableTile.newVoidTile(1,1);
		tiles[1][2] = ImpassableTile.newVoidTile(1,2);
		tiles[1][3] = ImpassableTile.newVoidTile(1,3);
		tiles[2][0] = PassableTile.newGrassTile(2,0);
		tiles[2][1] = PassableTile.newGrassTile(2,1);
		tiles[2][2] = PassableTile.newGrassTile(2,2);
		tiles[2][3] = PassableTile.newGrassTile(2,3);
		tiles[3][0] = PassableTile.newGrassTile(3,0);
		tiles[3][1] = PassableTile.newGrassTile(3,1);
		tiles[3][2] = PassableTile.newGrassTile(3,2);
		tiles[3][3] = PassableTile.newGrassTile(3,3);
		player = new Player("Ben Dover");
		world = new World(tiles,null,null);
		party = new Party("icon_ovelia.png",null);
		hero = new Hero();
		hero.setMovePts(4);
		party.setLeader(hero);
		party.setOwner(player);
		party.refresh();
		world.setIcon(party, 0, 0);
	}
	
	/**
	 * [P][G][G][G]
	 * [X][X][G][G]
	 * [G][X][G][G]
	 * [X][X][G][G]
	 */
}
