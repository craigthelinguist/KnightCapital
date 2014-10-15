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
import tools.Constants;
import tools.Sorter;
import world.World;
import world.icons.DecorIcon;
import world.icons.ItemIcon;
import world.icons.WorldIcon;
import world.tiles.CityTile;
import world.tiles.ImpassableTile;
import world.tiles.Tile;
import world.towns.City;

/**
 * WorldRenderer provides static methods for
 * @author Myles, Aaron
 */
public class WorldRenderer {

	private static final int TILE_WD = Constants.TILE_WD;
	private static final int TILE_HT = Constants.TILE_HT;
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
		addIconsAndTilesToBuffer(graphics,controller,drawBuffer,resolution);
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
			else if (toDraw instanceof ItemIcon) drawItemIcon(graphics,(CartesianMapping<ItemIcon>) mapping,camera);
			else if (toDraw instanceof DecorIcon) drawDecorIcon(graphics,(CartesianMapping<DecorIcon>) mapping, camera);
			else if (toDraw instanceof WorldIcon) drawIcon(graphics,(CartesianMapping<WorldIcon>) mapping,camera);
			else if (toDraw instanceof Tile) drawTile(graphics,(CartesianMapping<Tile>) mapping,camera);
		}

		// Some basic debug info
		graphics.setColor(Color.BLACK);
		graphics.drawString("Knight Capital", 30, 30);
		graphics.drawString("Arrow keys to move camera", 30, 110);
		graphics.drawString("Press r & e to rotate cw & ccw", 30, 130);
		graphics.setColor(Color.RED);
		String gold = ""+controller.getPlayer().getGold();
		graphics.drawString("Gold: "+ gold, 30, 150);

	}

	/**
	 * Iterate over all tiles in the controller's world. Draws the tiles and adds any icons on them to the buffer.
	 * @param graphics: object on which to draw things
	 * @param controller: contains info about world to be drawn
	 * @param drawBuffer: list of things to be drawn later
	 */
	public static void addIconsAndTilesToBuffer(Graphics graphics, WorldController controller, List<CartesianMapping<?>> drawBuffer, Dimension dimensions){
		final World world = controller.getWorld();
		final Camera camera = controller.getCamera();

		Tile[][] tiles = controller.getWorld().getTiles();
		for (int y = 0; y < tiles.length; y++){
			for (int x = 0; x < tiles[y].length; x++){
				Point ptCart = new Point(x,y);

				// add tile to buffer)
				Tile tile = world.getTile(x,y);
				if (tile instanceof CityTile) continue;
				Point ptRotated = Geometry.rotateByCamera(ptCart, camera, world.dimensions);


				if (!Geometry.isPointOnScreen(ptRotated,controller.getCamera(),dimensions)) continue;


				int intensity = 0;
				if (tile == controller.getSelectedTile()) intensity = 55;
				else if (controller.isHighlighted(ptCart)) intensity = 25;

				CartesianMapping<Tile> tileToDraw = new CartesianMapping<>(-2,tile,ptRotated,intensity);

				if(tile instanceof ImpassableTile) {
					tileToDraw = new CartesianMapping<>(2, tile, ptRotated, intensity);
				}



				drawBuffer.add(tileToDraw);

				// add occupant to buffer
				if (tile.occupant() != null){
					WorldIcon occupant = tile.occupant();
					Point ptRotatedIcon = Geometry.copyPoint(ptRotated);
					CartesianMapping<WorldIcon> iconToDraw = new CartesianMapping<>(1,occupant,ptRotatedIcon,0);
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

		// get mappings and store the tiles to draw them from in a list
		for (City city : citySet){
			CityTile ct = null;
			ct = city.getLeftmostTile(controller.getCamera());
			Point cityOrigin = new Point(ct.X,ct.Y);
			Point rotatedPt = Geometry.rotateByCamera(cityOrigin, camera, world.dimensions);
			CartesianMapping<City> mapping = new CartesianMapping<City>(1,city,rotatedPt,0);
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
		// our point is at p, for the purposeparts of drawing it has to be at q.
		// we also need to shift the image up so the top of the image is in-line with the top of the topmost tile

		int imageHeight = city.getImageHeight();
		int necessaryHeight = TILE_HT*City.WIDTH;
		int offsetY = imageHeight - necessaryHeight;
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
	private static void drawIcon(Graphics graphics, CartesianMapping<WorldIcon> mapping, Camera camera){
		int isoY = mapping.point.y;
		int isoX = mapping.point.x;
		if (mapping.thing.getImage() == null) return;
		final int ICON_WD = mapping.thing.getImage().getWidth();
		final int ICON_HT = mapping.thing.getImage().getHeight();
		int iconY = isoY - TILE_HT/4;
		int iconX = isoX + TILE_WD/2 - ICON_WD/2;
		mapping.thing.draw(graphics,iconX,iconY);
	}

	private static void drawItemIcon(Graphics graphics, CartesianMapping<ItemIcon> mapping, Camera camera) {
		int isoY = mapping.point.y;
		int isoX = mapping.point.x;
		final int ICON_WD = mapping.thing.getImage().getWidth();
		final int ICON_HT = mapping.thing.getImage().getHeight();
		int iconY = isoY + ICON_HT/4;
		int iconX = isoX + TILE_WD/2 - ICON_WD/2;
		mapping.thing.draw(graphics,iconX,iconY);
	}

	/**
	 * Draws a decor icon.
	 * Assumes that the point is the point obtained by rotating the city's topmost tile (from the
	 * camera's perspective) along the camera's viewing perspective and then converting into isometric with no further
	 * manipulation.
	 * @param graphics: object on which to draw
	 * @param ptIso: an isometric point in space
	 * @param camera: viewing perspective
	 * @param occupant: the icon to be drawn
	 */
	private static void drawDecorIcon(Graphics graphics, CartesianMapping<DecorIcon> mapping, Camera camera) {
		Point ptIso = mapping.point;

		int ht = mapping.thing.getImage().getHeight();

		int dy = ht - Constants.TILE_HT;
		ptIso.y = ptIso.y - dy;

		mapping.thing.draw(graphics, ptIso.x, ptIso.y);
	}

	private static void drawTile(Graphics graphics, CartesianMapping<Tile> mapping, Camera camera) {
		Point ptIso = mapping.point;
		int ht = mapping.thing.getTileHeight();
		int dy = ht - Constants.TILE_HT;
		ptIso.y = ptIso.y - dy;
		if (mapping.intensity != 0) mapping.thing.drawHighlighted(graphics, ptIso.x, ptIso.y, mapping.intensity);
		else mapping.thing.draw(graphics, ptIso.x, ptIso.y);
	}

	/**
	 * Return a camera view of the centre of the given world that is north-oriented.
	 * @param world: the world you're viewing.
	 * @return: a camera looking at the middle of the world
	 */
	public static Camera getCentreOfWorld(World world){
		return new Camera(Constants.WINDOW_WD / 2, 0, Camera.NORTH);
	}

}
