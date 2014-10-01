package world;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

import player.Player;

import tools.Geometry;
import tools.GlobalConstants;
import tools.Log;

/**
 * The world is the part of the game where the players click around the map, and move their parties.
 * It is a collection of tiles and info about them (the cities, heroes, units, items populating those
 * tiles).
 */
public class World {

	// assumes the world is rectangular
	private Tile[][] tiles;
	public final int NUM_TILES_ACROSS;
	public final int NUM_TILES_DOWN;
	public final int WORLD_WD;
	public final int WORLD_HT;

	public World(Tile[][] tiles_){
		tiles = tiles_;
		NUM_TILES_ACROSS = tiles.length;
		NUM_TILES_DOWN = tiles[0].length;
		WORLD_WD = NUM_TILES_ACROSS*(GlobalConstants.TILE_WD/2);
		WORLD_HT = NUM_TILES_DOWN*(GlobalConstants.TILE_HT/2);
	}

	/**
	 * Get the specified tile in the world (in cartesian coordinates).
	 * @param x: how far across tile is
	 * @param y: how far up tile is
	 * @return the Tile, or null if the position described is not part of this world.
	 */
	public Tile getTile(int x, int y){
		if (x < 0 || x >= tiles.length || y < 0 || y >= tiles[x].length){
			Log.print("clicked out of world boundaries");
			return null;
		}
		else return tiles[x][y];
	}
	
	/**
	 * Get the specified tile in the world (in cartesian coordinates).
	 * @param p: a point (x,y) in the world
	 * @return the Tile, or null if the position described is not part of this world.
	 */
	public Tile getTile(Point p){
		return getTile(p.x,p.y);
	}
	
	/* I don't like making the data structure storing tiles directly accessible
	 * because we might move to a quadtree or something and seems like it'd be easier
	 * to accidentally modify stuff you shouldn't if u can get references to things through here - Aaron */
	public Tile[][] getTiles(){
		return tiles;
	}
	
	public void setIcon(WorldIcon i, int x, int y){
		tiles[x][y].setIcon(i);
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
		tileDestination.setIcon(tileLocation.occupant());
		tileLocation.setIcon(null);
		int dist = Geometry.taxicab(location, destination);
		party.decreaseMovePoints(dist);
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
			Point point;
			int costToHere;
			int weight;
			
			public Node(Point p, int cost){
				point = p;
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
			int costToHere = node.costToHere + 1;
			for (Point pt : neighbours){
				Node nd = new Node(pt,costToHere);
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
	
}
