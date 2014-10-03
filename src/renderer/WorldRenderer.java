package renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import controllers.WorldController;

import tools.Geometry;
import tools.GlobalConstants;

import world.CityTile;
import world.Tile;
import world.WorldIcon;
import world.World;
import world.City;;

/**
 * 
 * @author whileym4st3r
 *
 */
public class WorldRenderer {

	private static final int TILE_WD = GlobalConstants.TILE_WD;
	private static final int TILE_HT = GlobalConstants.TILE_HT;
	
	// use the static methods
	private WorldRenderer(){}

	/**
	 * Draw the world on the given graphics object.
	 * @param world: the world to draw
	 * @param graphics: thing on which you're drawing
	 * @param resolution: size of screen being drawn to
	 */
	public static void render(WorldController controller, Graphics graphics, Dimension resolution){

		World world = controller.getWorld();
		Camera camera = controller.getCamera();
		Tile selected = controller.getSelectedTile();
		Tile[][] tiles = world.getTiles();

		// draw tiles
		for (int y = 0; y < tiles.length; y++){
			for (int x = 0; x < tiles[y].length; x++){
				
				// the position in tiles[][]
				Point ptCart = new Point(x,y);
				
				// rotate point according to perspective of camera, and draw it according to that rotation
				Point ptRotated = Geometry.rotatePoint(new Point(x,y), camera, world.dimensions);
				Point ptIso = Geometry.cartesianToIsometric(ptRotated, camera);
				
				if (!tileOnScreen(ptIso,resolution)) continue; // don't draw things that aren't visible
				Tile tile = world.getTile(ptCart);
				if (tile == selected) tile.drawHighlighted(graphics, ptIso.x, ptIso.y);
				else tile.draw(graphics, ptIso.x, ptIso.y);

			}
		}
		
		// draw icons
		for (int y = 0; y < tiles.length; y++){
			for (int x = 0; x < tiles[y].length; x++){
				
				// the position in tiles[][]
				Point ptCart = new Point(x,y);
				
				// rotate point according to perspective of camera, and draw it according to that rotation
				Point ptRotated = Geometry.rotatePoint(new Point(x,y), camera, world.dimensions);
				Point ptIso = Geometry.cartesianToIsometric(ptRotated, camera);
				
				if (!tileOnScreen(ptIso,resolution)) continue; // don't draw things that aren't visible
				Tile tile = world.getTile(ptCart);
				WorldIcon occupant = tile.occupant();
				if (occupant == null) continue;
				drawIcon(graphics,occupant,ptIso);
				
			}
		}
		
		// draw the cities
		drawCities(graphics,controller);
		
		// Some basic debug info
		graphics.setColor(Color.BLACK);
		graphics.drawString("Knight Capital", 30, 30);
		graphics.drawString("Current Rotation: "+(camera.getOrientation() * 90), 30, 50);
		graphics.drawString("Current Origin: ("+camera.getOriginX()+","+camera.getOriginY()+")", 30, 70);
		graphics.drawString("Arrow keys to move camera", 30, 110);
		graphics.drawString("Press r to rotate", 30, 130);
		

	}
	
	public static void drawPoint(Graphics graphics, Point p){
		drawPoint(graphics,p.x,p.y);
	}
	
	public static void drawPoint(Graphics graphics, int x, int y){
		drawPoint(graphics,x,y,3);
	}
	
	public static void drawPoint(Graphics graphics, int x, int y, int rad){
		graphics.fillRect(x-rad, y-rad, rad,rad);
	}

	/**
	 * Return true if the tile with origin pt would be visible on the screen represented by resolution.
	 * @param pt: a point in isometric space.
	 * @param resolution: screen bounds
	 * @return: boolean
	 */
	public static boolean tileOnScreen(Point pt, Dimension resolution){
		final int TILE_WD = GlobalConstants.TILE_WD;
		final int TILE_HT = GlobalConstants.TILE_HT;
		
		return	pt.x >= 0-TILE_WD
			&&	pt.x < resolution.width + TILE_WD
			&&	pt.y >= 0-TILE_HT
			&&	pt.y < resolution.height + TILE_HT;
	}
	
	/**
	 * Draw cities.
	 * Untested! - Aaron.
	 */
	public static void drawCities(Graphics graphics, WorldController controller){

		// preliminaries
		final World world = controller.getWorld();
		final Camera camera = controller.getCamera();
		final int TILE_WD = GlobalConstants.TILE_WD;
		final int TILE_HT = GlobalConstants.TILE_HT;
		
		// stores city and point
		class Tuple{
			City city;
			Point point;
			Tuple (City c, Point p){
				city = c; point = p;
			}
			public boolean equals(Object o){
				if (!(o instanceof Tuple)) return false;
				Tuple t = (Tuple)o;
				return t.city == city;
			}
		}
		
		List<Tuple> cities = new ArrayList<>();
		
		// iterate over tiles; get Cities
		Tile[][] tiles = world.getTiles();
		for (int y = 0; y < tiles.length; y++){
			for (int x = 0; x < tiles[y].length; x++){
				
				// if not a city tile we don't care
				Tile tile = tiles[x][y];
				if (!(tile instanceof CityTile)) continue;
				
				// extract the city from the tile
				CityTile ct = (CityTile)tile;
				City city = ct.city;
				
				// note that order of iteration means you will always reach the top-most tile
				// of a city first.
				Tuple tuple = new Tuple(city,new Point(x,y));
				if (!cities.contains(tuple)) cities.add(tuple);
				
			}
		}

		// order cities from back to front
		Comparator<Tuple> isometricSorter = new Comparator<Tuple>(){
			@Override
			public int compare(Tuple t1, Tuple t2) {
				Point p1 = t1.point; Point p2 = t2.point;
				return Geometry.taxicab(new Point(0,0), p1) - Geometry.taxicab(new Point(0,0), p2);
			}	
		};
		Collections.sort(cities, isometricSorter);
		
		// 
		//  ___________
		// |q    p     |
		// |     x     |
		// |   x   x   |
		// | x   x   x |
		// |   x   x   |
		// |     x     |
		// |___________|
		// 
		// our point is at p, for the purposes of drawing it has to be at q.
		// subtract the distance = (half tile width) * (number of tiles in the width of a city)
		int OFFSET_WD = TILE_WD/2 * City.WIDTH;
		for (Tuple tuple : cities){
			
			// get city and the corresponding pt at the top of the city in isometric space
			City city = tuple.city;
			Point ptCartesian = tuple.point;
			Point ptIso = Geometry.cartesianToIsometric(ptCartesian, camera);
			
			// we add TILE_WD/2 because ptIso will be at the top-left of the corresponding tile's bounding box:
			// we want it to be in the top-middle
			int newX = ptIso.x + TILE_WD/2 - OFFSET_WD;
			city.draw(graphics, newX, ptIso.y);
			
		}
		
		
	}
	
	public static void drawIcon(Graphics graphics, WorldIcon occupant, Point ptIso){
		int isoY = ptIso.y;
		int isoX = ptIso.x;
		final int TILE_HT = GlobalConstants.TILE_HT;
		final int TILE_WD = GlobalConstants.TILE_WD;
		final int ICON_WD = GlobalConstants.ICON_WD;
		final int ICON_HT = GlobalConstants.ICON_HT;
		int iconY = isoY - TILE_HT/4;
		int iconX = isoX + TILE_WD/2 - ICON_WD/2;
		occupant.draw(graphics,iconX,iconY);
	}
	
	/**
	 * Return a camera view of the centre of the given world that is north-oriented.
	 * @param world: the world you're viewing.
	 * @return: a camera looking at the middle of the world
	 */
	public static Camera getCentreOfWorld(World world){
		return new Camera(GlobalConstants.WINDOW_WD / 2, 0, Camera.NORTH);
	}
	
}
