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
import java.util.Set;

import controllers.WorldController;

import tools.CartesianMapping;
import tools.Geometry;
import tools.GlobalConstants;
import tools.Sorter;

import world.CityTile;
import world.Tile;
import world.WorldIcon;
import world.World;
import world.City;;

/**
 * WorldRenderer provides static methods for
 * @author Myles, Aaron
 */
public class WorldRenderer {

	private static final int TILE_WD = GlobalConstants.TILE_WD;
	private static final int TILE_HT = GlobalConstants.TILE_HT;
	private static final int HALF_TILE_WD = TILE_WD/2;
	private static final int HALF_TILE_HT = TILE_HT/2;
	
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
				Point ptRotated = Geometry.rotateByCamera(new Point(x,y), camera, world.dimensions);
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
				Point ptRotated = Geometry.rotateByCamera(new Point(x,y), camera, world.dimensions);
				Point ptIso = Geometry.cartesianToIsometric(ptRotated, camera);
				
				if (!tileOnScreen(ptIso,resolution)) continue; // don't draw things that aren't visible
				Tile tile = world.getTile(ptCart);
				WorldIcon occupant = tile.occupant();
				if (occupant == null) continue;
				drawIcon(graphics,controller.getCamera(),occupant,ptIso);
				
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
	
	private static class Tuple<T>{

	}
	
	/**
	 * Draw cities.
	 * Untested! - Aaron.
	 */
	public static void drawCities(Graphics graphics, WorldController controller){

		// preliminaries
		final World world = controller.getWorld();
		final Camera camera = controller.getCamera();
		Set<? extends City> citySet = world.getCities();
		int orientation = camera.getOrientation();
		
		// get cities and store the tiles to draw them from in a list
		List<CartesianMapping<City>> cities = new ArrayList<>();
		for (City city : citySet){
			CityTile ct = null;
			if (orientation == Camera.EAST) ct = city.getLeftmostTile();
			else if (orientation == Camera.WEST) ct = city.getRightmostTile();
			else if (orientation == Camera.NORTH) ct = city.getTopmostTile(); 
			else if (orientation == Camera.SOUTH) ct = city.getBottommostTile(); 
			else throw new RuntimeException("unknown orientation for camera: " + orientation);
			Point cityOrigin = new Point(ct.X,ct.Y);
			Point rotatedPt = Geometry.rotateByCamera(cityOrigin, camera, world.dimensions);
			CartesianMapping<City> tuple = new CartesianMapping<City>(city,rotatedPt);
			cities.add(tuple);
		}
		
		// sort the cities to get the drawing order
		if (orientation == Camera.NORTH) Sorter.sortTopToBottom(cities);
		else if (orientation == Camera.EAST) Sorter.sortRightToLeft(cities);
		else if (orientation == Camera.WEST) Sorter.sortLeftToRight(cities);
		else if (orientation == Camera.SOUTH) Sorter.sortBottomToTop(cities);
		
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
		final int OFFSET = HALF_TILE_WD*City.WIDTH;
		for (CartesianMapping<City> mapping : cities){
			City city = mapping.thing;
			Point point = Geometry.cartesianToIsometric(mapping.point, camera);
			point.x = point.x + HALF_TILE_WD - OFFSET;
			city.draw(graphics, point.x, point.y);
		}
	
	}
	
	public static void drawIcon(Graphics graphics, Camera camera, WorldIcon occupant, Point ptIso){
		int isoY = ptIso.y;
		int isoX = ptIso.x;
		final int TILE_HT = GlobalConstants.TILE_HT;
		final int TILE_WD = GlobalConstants.TILE_WD;
		final int ICON_WD = GlobalConstants.ICON_WD;
		final int ICON_HT = GlobalConstants.ICON_HT;
		int iconY = isoY - TILE_HT/4;
		int iconX = isoX + TILE_WD/2 - ICON_WD/2;

		occupant.setAnimationName(getAnimationName(occupant.getAnimationName(),camera));
		
		
		occupant.draw(graphics,iconX,iconY);
	}

	/**
	 * Given a viewing perspective, returns the suffix of the animation name that should be viewed.
	 * @param cam: camera whose perspective is being viewed.
	 * @return: suffix of the animation that should be drawn for this viewing angles
	 */
	
	// TODO: not entirely perfect, character doesn't always face correct direction
	private static String getAnimationName(String animName, Camera cam){
		
		final String[] dirs = new String[]{ "north", "east", "south", "west" };
		int playerDir = -1;
		animName = animName.toLowerCase();
		for (int i = 0; i < dirs.length; i++){
			if (animName.contains(dirs[i])){
				playerDir = i;
				break;
			}
		}
		int cameraDir = -1;
		switch(cam.getOrientation()){
		case Camera.NORTH:
			cameraDir = 0; break;
		case Camera.EAST:
			cameraDir = 1; break;
		case Camera.SOUTH:
			cameraDir = 2; break;
		case Camera.WEST:
			cameraDir = 3; break;
		}
		
		int dir = (playerDir+cameraDir)%4;
		return dirs[dir];
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
