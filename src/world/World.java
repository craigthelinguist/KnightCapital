package world;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import player.Player;
import renderer.Camera;

import tools.Geometry;
import tools.Constants;
import tools.Log;
import world.icons.Party;
import world.icons.WorldIcon;
import world.tiles.Tile;
import world.towns.City;

/**
 * The world is the part of the game where the players click around the map, and move their parties.
 * It is a collection of tiles and info about them (the cities, heroes, units, items populating those
 * tiles).
 * @author Aaron
 */
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

		// if you've cycled through all players, it is a new day
		currentPlayer = (currentPlayer+1)%players.length;
		if (currentPlayer == 0){
			currentDay++;
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
		if (!(icon instanceof Party)) return false;
		Party party = (Party)icon;
		if (!(party.ownedBy(player))) return false;

		// attempt to move party from location to destination
		if (!pathExists(location,destination)) return false;

		// do it in this order or you disappear if you move to tile you're already
		// standing on :)
		tileLocation.setIcon(null);
		tileDestination.setIcon(party);
		int dist = Geometry.taxicab(location, destination);

		// update party
		party.decreaseMovePoints(dist);
		boolean east = destination.x - destination.y > location.x - location.y;
		boolean north = destination.x + destination.y < location.x + location.y;
		if (east && north) party.setAnimationName("north");
		else if (east && !north) party.setAnimationName("east");
		else if (!east && north) party.setAnimationName("west");
		else if (!east && !north) party.setAnimationName("south");

		return true;
	}

	/**
	 * Check if there's a path from the tile at one point to the tile at another point. Does not move
	 * or update the state of the world - only says whether the path exists.
	 * @param start: start of the path.
	 * @param goal: end of the path.
	 * @return: true if the party on the tile at start can move to the tile at goal.
	 */
	private boolean pathExists(Point start, final Point goal){

		// preliminaries
		Party party = (Party)(getTile(start.x,start.y).occupant());
		int movePts = party.getMovePoints();
		if (movePts <= 0) return false;

		// a wrapper class for the nodes in the fringe
		class Node implements Comparable<Node>{
			final Point point;
			final int costToHere;
			final int weight;

			public Node(Point p, int cost){
				point = p;
				costToHere = cost;
				weight = costToHere + heuristic(goal);
			}

			// taxicab distance is the heuristic since we're using a discrete grid
			int heuristic(Point other){
				return Geometry.taxicab(point, other);
			}

			@Override
			public int compareTo(Node other) {
				return weight - other.weight;
			}

		}

		// initialise the fringe and declare variables
		Node node = new Node(start,0);
		HashSet<Point> visited = new HashSet<>();
		PriorityQueue<Node> fringe = new PriorityQueue<>();
		fringe.offer(node);
		Point point; Tile tile;


		while (!fringe.isEmpty()){

			// get next node, check its feasibility
			node = fringe.poll();
			if (node.costToHere > movePts) continue;
			point = node.point;
			if (visited.contains(point)) continue;
			tile = getTile(point);
			if (!tile.passable(party) && point != start) continue;

			// mark as visited
			visited.add(point);

			// if you're at the goal, stop
			if (node.point.equals(goal)) return true;

			// otherwise push neighbours onto fringe
			LinkedList<Point> neighbours = findNeighbours(point);
			int cost = node.costToHere + 1;
			for (Point pt : neighbours){
				Node nd = new Node(pt,cost);
				fringe.offer(nd);
			}

		}

		return false;

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

	public void updateSprites(boolean clockwise) {
		for (int i = 0; i < tiles.length; i++){
			for (int j = 0; j < tiles[i].length; j++){
				Tile tile = tiles[i][j];
				WorldIcon wi = tile.occupant();
				if (wi != null){
					String name = wi.getAnimationName();
					int playerDir;
					if (name.contains("north")) playerDir = Camera.NORTH;
					else if (name.contains("east")) playerDir = Camera.EAST;
					else if (name.contains("south")) playerDir = Camera.SOUTH;
					else if (name.contains("west")) playerDir = Camera.WEST;
					else throw new RuntimeException("Unknown animation name: " + name);

					if (clockwise) playerDir = (playerDir+1)%4;
					else if (playerDir == 0) playerDir = 3;
					else playerDir = playerDir - 1;

					if (playerDir == Camera.NORTH) wi.setAnimationName("north");
					else if (playerDir == Camera.EAST) wi.setAnimationName("east");
					else if (playerDir == Camera.SOUTH) wi.setAnimationName("south");
					else if (playerDir == Camera.WEST) wi.setAnimationName("west");
				}
			}
		}

		for (City city : cities){

			String name = city.getAnimationName();
			int cityDir;
			if (name.contains("north")) cityDir = Camera.NORTH;
			else if (name.contains("east")) cityDir = Camera.EAST;
			else if (name.contains("south")) cityDir = Camera.SOUTH;
			else if (name.contains("west")) cityDir = Camera.WEST;
			else throw new RuntimeException("Unknown animation name: " + name);

			if (clockwise) cityDir = (cityDir+1)%4;
			else if (cityDir == 0) cityDir = 3;
			else cityDir = cityDir - 1;

			if (cityDir == Camera.NORTH) city.setAnimationName("north");
			else if (cityDir == Camera.EAST) city.setAnimationName("east");
			else if (cityDir == Camera.SOUTH) city.setAnimationName("south");
			else if (cityDir == Camera.WEST) city.setAnimationName("west");

		}


		// TODO Auto-generated method stub

	}

	/**
	 * Return the set of valid moves for the given party on the selected tile.
	 * @param party: the party that is moving.
	 * @param start: the tile they're starting from.
	 */
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
		Queue<Node> queue = new ArrayDeque<>();
		queue.offer(node);

		while (!queue.isEmpty()){
			node = queue.poll();
			point = node.point;
			visited.add(point);
			LinkedList<Point> neighbours = findNeighbours(point);
			int newDist = node.distance + 1;
			for (Point neighbour : neighbours){
				if (newDist > movePoints || visited.contains(neighbour)) continue;
				else queue.offer(new Node(neighbour,newDist));
			}
		}

		return visited;
	}


}
