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

import world.World;
import world.icons.WorldIcon;
import world.tiles.CityTile;
import world.tiles.Tile;
import world.towns.City;

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

		// preliminaries
		Camera camera = controller.getCamera();
		
		// draw tiles and construct buffer
		List<CartesianMapping<?>> drawBuffer = new ArrayList<>();
		drawTilesAndAddIconsToBuffer(graphics,controller,drawBuffer);
		addCitiesToBuffer(controller,drawBuffer);

		// sort buffer's contents by the perspective
		Sorter.sortTopToBottom(drawBuffer);
		
		// convert buffer contents into isometric points
		for (int i = 0; i < drawBuffer.size(); i++){
			CartesianMapping<?> mapping = drawBuffer.get(i);
			Point ptIso = Geometry.cartesianToIsometric(mapping.point, camera);
			mapping.point.x = ptIso.x;
			mapping.point.y = ptIso.y;
		}
		
		// draw contents of buffer
		for (CartesianMapping<?> mapping : drawBuffer){
			Object toDraw = mapping.thing;
			if (toDraw instanceof City) drawCity(graphics,mapping.point,camera,(City)toDraw);
			else if (toDraw instanceof WorldIcon) drawIcon(graphics,mapping.point,camera,(WorldIcon)toDraw);
		}
		
		// Some basic debug info
		graphics.setColor(Color.BLACK);
		graphics.drawString("Knight Capital", 30, 30);
		graphics.drawString("Current Rotation: "+(camera.getOrientation() * 90), 30, 50);
		graphics.drawString("Current Origin: ("+camera.getOriginX()+","+camera.getOriginY()+")", 30, 70);
		graphics.drawString("Arrow keys to move camera", 30, 110);
		graphics.drawString("Press r & e to rotate cw & ccw", 30, 130);
		

	}
	
	/**
	 * Return true if the tile with origin pt would be visible on the screen represented by resolution.
	 * @param pt: a point in isometric space.
	 * @param resolution: screen bounds
	 * @return: boolean
	 */
	public static boolean isPointOnScreen(Point pt, Dimension resolution){
		final int TILE_WD = GlobalConstants.TILE_WD;
		final int TILE_HT = GlobalConstants.TILE_HT;
		
		return	pt.x >= 0-TILE_WD
			&&	pt.x < resolution.width + TILE_WD
			&&	pt.y >= 0-TILE_HT
			&&	pt.y < resolution.height + TILE_HT;
	}

	/**
	 * Iterate over all tiles in the controller's world. Draws the tiles and adds any icons on them to the buffer.
	 * @param graphics: object on which to draw things
	 * @param controller: contains info about world to be drawn
	 * @param drawBuffer: list of things to be drawn later
	 */
	public static void drawTilesAndAddIconsToBuffer(Graphics graphics, WorldController controller, List<CartesianMapping<?>> drawBuffer){
		final World world = controller.getWorld();
		final Camera camera = controller.getCamera();
		
		Tile[][] tiles = controller.getWorld().getTiles();
		for (int y = 0; y < tiles.length; y++){
			for (int x = 0; x < tiles[y].length; x++){
				Point ptCart = new Point(x,y);
				if (!isPointOnScreen(ptCart,world.dimensions)) continue;
				
				// draw the tile
				Tile tile = world.getTile(x,y);
				Point ptRotated = Geometry.rotateByCamera(ptCart, camera, world.dimensions);
				Point ptIso = Geometry.cartesianToIsometric(ptRotated, camera);
				if (tile == controller.getSelectedTile()){
					tile.drawHighlighted(graphics, ptIso.x, ptIso.y, 55);
				}
				else if (controller.isHighlighted(ptCart)){
					tile.drawHighlighted(graphics, ptIso.x, ptIso.y, 25);
				}
				else{
					tile.draw(graphics, ptIso.x, ptIso.y);
				}
				
				// add occupant to buffer, if it exists
				WorldIcon occupant = tile.occupant();
				if (occupant != null){
					CartesianMapping<WorldIcon> iconToDraw = new CartesianMapping<>(occupant,ptRotated);
					drawBuffer.add(iconToDraw);
				}
			}
		}
	}
	
	/**
	 * Adds all cities in the given controller's world to the draw buffer.
	 * @param controller: contains info about world to be drawn
	 * @param drawBuffer: list of things to be drawn later
	 */
	public static void addCitiesToBuffer(WorldController controller, List<CartesianMapping<?>> drawBuffer){

		// preliminaries
		final World world = controller.getWorld();
		final Camera camera = controller.getCamera();
		Set<? extends City> citySet = world.getCities();
		int orientation = camera.getOrientation();
		
		// get mappings and store the tiles to draw them from in a list
		for (City city : citySet){
			CityTile ct = null;
			ct = city.getLeftmostTile(controller.getCamera());
			Point cityOrigin = new Point(ct.X,ct.Y);
			Point rotatedPt = Geometry.rotateByCamera(cityOrigin, camera, world.dimensions);
			CartesianMapping<City> mapping = new CartesianMapping<City>(city,rotatedPt);
			drawBuffer.add(mapping);
		}
	
	}

	/**
	 * Draw the given city. Assumes that the point is the point obtained by rotating the city's leftmost tile (from the
	 * camera's perspective) along the camera's viewing perspective and then converting into isometric with no further
	 * manipulation.
	 * @param graphics: object on which to draw
	 * @param ptIso: an isometric point in space
	 * @param camera: viewing perspective
	 * @param city: the city to be drawn
	 */
	private static void drawCity(Graphics graphics, Point ptIso, Camera camera, City city){
		
		//  ___________
		// |q          |
		// |     x     |
		// |   x   x   |
		// |px   x   x |
		// |   x   x   |
		// |     x     |
		// |___________|
		// our point is at p, for the purposes of drawing it has to be at q.
		// we also need to shift the image up so the top of the image is in-line with the top of the topmost tile

		int imageHeight = city.getImageHeight();
		int necessaryHeight = TILE_HT*City.WIDTH;
		int offsetY = imageHeight-necessaryHeight;
		if (offsetY > 0) ptIso.y = ptIso.y - offsetY;
		int OFFSET = HALF_TILE_HT*City.WIDTH;
		ptIso.y = ptIso.y - OFFSET;
		ptIso.y = ptIso.y + HALF_TILE_HT;
		city.draw(graphics, ptIso.x, ptIso.y);
	}
	
	/**
	 * Draw the given WorldIcon. Assumes that the point is the point obtained by rotating the city's topmost tile (from the
	 * camera's perspective) along the camera's viewing perspective and then converting into isometric with no further
	 * manipulation.
	 * @param graphics: object on which to draw
	 * @param ptIso: an isometric point in space
	 * @param camera: viewing perspective
	 * @param occupant: the icon to be drawn
	 */
	private static void drawIcon(Graphics graphics, Point ptIso, Camera camera, WorldIcon occupant){
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
