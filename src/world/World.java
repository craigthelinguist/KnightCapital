package world;

import game.items.Item;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import controllers.WorldController;
import GUI.world.GameDialog;
import GUI.world.InventoryPanel;
import GUI.world.MainFrame;
import player.Player;
import renderer.Camera;
import tools.Geometry;
import tools.Constants;
import tools.Log;
import world.icons.ItemIcon;
import world.icons.Party;
import world.icons.WorldIcon;
import world.tiles.CityTile;
import world.tiles.Tile;
import world.towns.City;

/**
 * The world is the part of the game where the players click around the map, and move their parties.
 * It is a collection of tiles and info about them (the cities, heroes, units, items populating those
 * tiles).
 * @author Aaron
 */
@XStreamAlias("DATA")
public class World {

	// assumes the world is rectangular
	public final Dimension dimensions;
	public final int NUM_TILES_ACROSS;
	public final int NUM_TILES_DOWN;
	public final int WORLD_WD;
	public final int WORLD_HT;

	// set of cities in this world
	private final Set<City> cities;
	private final Player[] players;
	private int currentPlayer;

	private Tile[][] tiles;
	private int currentDay;

	public World(Tile[][] tiles_, Player[] playersArray, Set<City> citySet){
		tiles = tiles_;
		NUM_TILES_ACROSS = tiles.length;
		NUM_TILES_DOWN = tiles[0].length;
		dimensions = new Dimension(NUM_TILES_ACROSS,NUM_TILES_DOWN);
		WORLD_WD = NUM_TILES_ACROSS*(Constants.TILE_WD/2);
		WORLD_HT = NUM_TILES_DOWN*(Constants.TILE_HT/2);
		cities = citySet;
		players = playersArray;
		currentPlayer = 0;
		currentDay = 1;
	}


	/**
	 * End the turn for the current player. If all players have cycled through, then the day has
	 * ended so the game world should be updated.
	 * @return: true if the day ended (all players had their turn), false otherwise.
	 */
	public void endTurn(){


		// if you've cycled through all players, it is a new day
		currentPlayer = (currentPlayer+1)%players.length;
		if (currentPlayer == 0){
			currentDay++;
		}

		/*Iterate over the cities in the world, if the city belongs to the player that's ending his turn. Increase his gold by 10. Do this for every city the player owns.*/
		for(City c : this.getCities()) {
			if (c.ownedBy(players[currentPlayer])){
				int income = c.getIncome();
				players[currentPlayer].increaseGold(income);
			}

		}

		// if you have cycled through all players, it is a new day.
		// cycle through all parties owned by that player and remove buffs and stuff
		for (int i = 0; i < tiles.length; i++){
			for (int j = 0; j < tiles[i].length; j++){
				Tile tile = tiles[i][j];
				WorldIcon wi = tile.occupant();
				if (wi == null) continue;
				if (wi instanceof Party){
					Party party = (Party)wi;
					if (party.ownedBy(players[currentPlayer])){
						party.refresh();
					}
				}
			}
		}

	}

	/**
	 * Get the specified tile in the world (in cartesian coordinates).
	 * @param x: how far across tile is
	 * @param y: how far up tile is
	 * @return the Tile, or null if the position described is not part of this world.
	 */
	public Tile getTile(int x, int y){
		if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[x].length){
			return null;
		}
		else return tiles[x][y];
	}

	/**
	 * Get the specified tile in the world (in cartesian coordinates).
	 * @param p: a point (x,y) in the world
	 * @return the Tile, or null if the position described is OUT OF THIS WORLD
	 */
	public Tile getTile(Point p){
		if (p == null) return null;
		else return getTile(p.x,p.y);
	}

	/**
	 * Return a read-only view of the cities in this world.
	 * @return: a read-only set.
	 */
	public Set<? extends City> getCities(){
		return cities;
	}

	public Tile[][] getTiles(){
		return tiles;
	}

	public void setIcon(WorldIcon i, int x, int y){
		tiles[x][y].setIcon(i);
	}

	/**
	 * Return the current day.
	 * @return: an integer.
	 */
	public int getDay() {
		return currentDay;
	}

	/**
	 * Given two points on the world grid, moves the party which is owned by player
	 * and standing on the tile at location to the tile at destination.
	 * @param location: tile to move from.
	 * @param destination: tile to move to.
	 * @return: true if the party was successfully moved.
	 */
	public boolean moveParty(Player player, Point location, Point destination) {

		// check points given describe tiles in this world.
		Tile tileLocation = getTile(location.x,location.y);
		Tile tileDestination = getTile(destination.x,destination.y);
		if (tileLocation == null || tileDestination == null) return false;

		// check there is a party at location and it is owned by the specified player
		WorldIcon icon = tileLocation.occupant();
		if (!(icon instanceof Party)) {System.out.println("1st"); return false; }
		Party party = (Party)icon;
		if (!(party.ownedBy(player))){System.out.println("2nd"); return false;  }

		// attempt to move party from location to destination
		Set<Point> validMoves = this.getValidMoves(party, tileLocation);
		if (!validMoves.contains(destination)) return false;
		
		//if (!pathExists(location,destination)) {System.out.println("3rd");return false;}

		// Check if an item is at destination, if so, pick up item and move player there
		if(tileDestination.occupant() instanceof ItemIcon) {
			ItemIcon itemIcon = (ItemIcon)tileDestination.occupant();
			if(!party.addItem(itemIcon.item)){ System.out.println("4th"); return false;}
		}

		else if (tileDestination instanceof CityTile){
			City city = ((CityTile)(tileDestination)).getCity();
			city.setVisitors(party);
			tileLocation.setIcon(null);
			if (city.getOwner() != party.getOwner()){
				city.changeOwner(party.getOwner());
			}
		}

		// do it in this order or you disappear if you move to tile you're already
		// standing on :^)
		tileLocation.setIcon(null);
		tileDestination.setIcon(party);
		int dist = Geometry.taxicab(location, destination);

		// update party move points
		party.decreaseMovePoints(dist);

		return true;
	}

	/**
	 * Return a list of points neighbouring the given point, if those points exist.
	 * @param p: point whose neighbours you'll return.
	 * @return: a list of points, if those points exist.
	 */
	private LinkedList<Point> findNeighbours(Point p){
		LinkedList<Point> points = new LinkedList<>();
		if (p.x + 1 < tiles.length) points.add(new Point(p.x+1,p.y));
		if (p.x - 1 >= 0) points.add(new Point(p.x-1,p.y));
		if (p.y + 1 < tiles[p.x].length) points.add(new Point(p.x,p.y+1));
		if (p.y - 1 >= 0) points.add(new Point(p.x,p.y-1));
		return points;
	}

	/**
	 * Rotate everything in the world 90 degrees.
	 * @param clockwise: whether you should rotate left or right.
	 */
	public void rotateOccupants(boolean clockwise) {
		
		// rotate tiles and occupants
		for (int i = 0; i < tiles.length; i++){
			for (int j = 0; j < tiles[i].length; j++){
				Tile tile = tiles[i][j];
				tile.rotate(clockwise);
				WorldIcon icon = tile.occupant();
				if (icon != null) icon.rotate(clockwise);
			}
		}

		// rotate cities
		for (City city : cities){
			city.rotate(clockwise);
		}

	}

	public Set<Point> getValidMoves(Party party, Tile start) {

		// a wrapper class for the nodes in the queue
		class Node{

			final Point point;
			final int distance;

			public Node(Point p, int d){
				point = p;
				distance = d;
			}

			public int hashCode(){
				return point.hashCode();
			}

		}

		int movePoints = party.getMovePoints();
		if (movePoints == 0) return new HashSet<Point>();
		Point point = new Point(start.X,start.Y);
		Node node = new Node(point,0);
		Set<Point> visited = new HashSet<>();
		Set<Point> validMoves = new HashSet<>();
		Queue<Node> queue = new ArrayDeque<>();
		queue.offer(node);

		while (!queue.isEmpty()){
			node = queue.poll();
			point = node.point;
			visited.add(point);
			Tile tile = tiles[point.x][point.y];
			
			if (!tile.isPassable(party)) continue;
			if (tile.canStandOn(party)) validMoves.add(point);
			
			LinkedList<Point> neighbours = findNeighbours(point);
			int newDist = node.distance + 1;

			for (Point neighbour : neighbours){
				if (newDist > movePoints || visited.contains(neighbour)) continue;
				else queue.offer(new Node(neighbour,newDist));
			}
		}
		
		return validMoves;
	}

	public Player getCurrentPlayer(){
		return players[currentPlayer];
	}

	public Player[] getPlayers() {
		return this.players;
	}

}
