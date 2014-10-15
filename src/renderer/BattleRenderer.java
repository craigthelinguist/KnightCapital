package renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tools.CartesianMapping;
import tools.Constants;
import tools.Geometry;
import tools.Sorter;
import world.World;
import world.icons.ItemIcon;
import world.icons.WorldIcon;
import world.tiles.CityTile;
import world.tiles.Tile;
import world.towns.City;
import controllers.BattleController;
import controllers.WorldController;

/**
 * Battle Renderer!
 * It's like a world renderer! But for battles!
 * @author myles
 */
public class BattleRenderer {

	private static final int TILE_WD = Constants.TILE_WD;
	private static final int TILE_HT = Constants.TILE_HT;
	private static final int HALF_TILE_WD = TILE_WD/2;
	private static final int HALF_TILE_HT = TILE_HT/2;

	// keep it static
	private BattleRenderer(){}

	/**
	 * Draw the world on a given graphics object
	 */
	public static void render(BattleController control, Graphics g, Dimension res){

		Camera cam = control.getCamera();

		List<CartesianMapping<?>> drawBuffer = new ArrayList<>();
		addTilesAndIconsToBuffer(g, control, drawBuffer, res);

		// sort buffer's contents by the perspective
		Sorter.sortTopToBottom(drawBuffer);

		// convert buffer contents into isometric points
		for (int i = 0; i < drawBuffer.size(); i++){
			CartesianMapping<?> mapping = drawBuffer.get(i);
			Point ptIso = Geometry.cartesianToIsometric(mapping.point, cam);
			mapping.point.x = ptIso.x;
			mapping.point.y = ptIso.y;
		}

		// draw contents of buffer
		for (CartesianMapping<?> mapping : drawBuffer){
			Object toDraw = mapping.thing;
			if (toDraw instanceof WorldIcon) drawIcon(g,(CartesianMapping<WorldIcon>) mapping,cam);
			else if (toDraw instanceof Tile) drawTile(g,(CartesianMapping<Tile>) mapping,cam);
		}

		// Some basic debug info
		g.setColor(Color.BLACK);
		g.drawString("Knight Capital Battle Renderer v0.1", 30, 30);
	}

	/**
	 * Iterate over all tiles in the controller's world. Draws the tiles and adds any icons on them to the buffer.
	 * @param graphics: object on which to draw things
	 * @param controller: contains info about world to be drawn
	 * @param drawBuffer: list of things to be drawn later
	 */
	public static void addTilesAndIconsToBuffer(Graphics graphics, BattleController controller, List<CartesianMapping<?>> drawBuffer, Dimension resolution){
		final World world = controller.getWorld();
		final Camera camera = controller.getCamera();

		Tile[][] tiles = controller.getWorld().getTiles();
		for (int y = 0; y < tiles.length; y++){
			for (int x = 0; x < tiles[y].length; x++){
				Point ptCart = new Point(x,y);
				if (!Geometry.isPointOnScreen(ptCart,controller.getCamera(),resolution)) continue;

				// add tile to buffer
				Tile tile = world.getTile(x,y);
				Point ptRotated = Geometry.rotateByCamera(ptCart, camera, world.dimensions);

				int intensity = 0;
				if (tile == controller.getSelectedTile()) intensity = 55;
				else if (controller.isHighlighted(ptCart)) intensity = 25;

				CartesianMapping<Tile> tileToDraw = new CartesianMapping<>(-2,tile,ptRotated,intensity);
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
		final int ICON_WD = mapping.thing.getImage().getWidth();
		final int ICON_HT = mapping.thing.getImage().getHeight();
		int iconY = isoY - TILE_HT/4;
		int iconX = isoX + TILE_WD/2 - ICON_WD/2;
		mapping.thing.draw(graphics,iconX,iconY);
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
