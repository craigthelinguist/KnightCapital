package renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import controllers.WorldController;

import tools.GlobalConstants;

import world.Tile;
import world.WorldIcon;
import world.World;

public class WorldRenderer {

	// use the static methods
	private WorldRenderer(){}

	/**
	 * Draw the world on the given graphics object.
	 * @param world: the world to draw
	 * @param graphics: thing on which you're drawing
	 * @param resolution: size of screen being drawn to
	 */
	public static void render(WorldController controller, Graphics graphics, Dimension resolution){

		final int TILE_WD = GlobalConstants.TILE_WD;
		final int TILE_HT = GlobalConstants.TILE_HT;
		World world = controller.getWorld();
		Camera camera = controller.getCamera();
		Tile selected = controller.getSelectedTile();
		Tile[][] tiles = world.getTiles();

		// At this point rotate is called every render cycle, but this isn't very efficient.
		tiles = rotateArray(tiles, camera.getOrientation());

		for (int y = 0; y < tiles.length; y++){
			for (int x = 0; x < tiles[0].length; x++){
				int isoX = camera.getOriginX() + (TILE_WD/2)*x - (TILE_WD/2)*y;
				int isoY = camera.getOriginY() + (TILE_HT/2)*x + (TILE_HT/2)*y;
				Tile tile = world.getTile(x,y);
				if (tile == selected) tile.drawHighlighted(graphics,isoX,isoY);
				else tile.draw(graphics, isoX, isoY);
			}
		}

		for (int y = 0; y < tiles.length; y++){
			for (int x = 0; x < tiles[0].length; x++){
				if (!tiles[x][y].occupied()) continue;
				int isoX = camera.getOriginX() + (TILE_WD/2)*x - (TILE_WD/2)*y;
				int isoY = camera.getOriginY() + (TILE_HT/2)*x + (TILE_HT/2)*y;
				drawIcon(graphics,tiles[x][y],isoX,isoY);
			}
		}

		// Some basic debug info
		graphics.setColor(Color.BLACK);
		graphics.drawString("Knight Capital", 30, 30);
		graphics.drawString("Current Rotation: "+(camera.getOrientation() * 90), 30, 50);
		graphics.drawString("Current Origin: ("+camera.getOriginX()+","+camera.getOriginY()+")", 30, 70);
		graphics.drawString("Arrow keys to move camera", 30, 110);
		graphics.drawString("Press r to rotate", 30, 130);

	}

	public static void highlightTile(Graphics graphics, World world, int x, int y){
		Tile[][] tiles = world.getTiles();
		tiles[x][y].drawHighlighted(graphics,x,y);
	}
	
	public static void drawTile(Graphics graphics, World world, int x, int y){
		Tile[][] tiles = world.getTiles();
		tiles[x][y].draw(graphics, x, y);
	}
	
	/**
	 * Rotates the tiles in a clockwise direction
	 * Uses the field rotation to know how much to rotate by
	 * @param tiles
	 * @return rotates array
	 */
	private static Tile[][] rotateArray(Tile[][] tiles, int orientation) {
		int width = tiles.length;
		int height = tiles[0].length;

		//Log.print("[WorldRenderer] Rotating tiles by "+(rotation*90));

		// New 2DArray for rotated tiles, not efficient but will do for now.
		Tile[][] rotated = null;

		// Rotate 90 Clockwise for each rotation count. If 0 do nothing;
		for(int rotateCount = 0; rotateCount != orientation; rotateCount++) {
			rotated = new Tile[height][width];

			// Rotate tiles by 90 cw
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					rotated[i][j] = tiles[j][10 - i - 1];
				}
			}

			tiles = rotated; // without this it would only ever rotate by 0 or 90
		}

		if(rotated != null) return rotated;
		else return tiles;
	}

	private static void drawIcon(Graphics graphics, Tile tile, int isoX, int isoY){
		final int TILE_HT = GlobalConstants.TILE_HT;
		final int TILE_WD = GlobalConstants.TILE_WD;
		final int ICON_WD = GlobalConstants.ICON_WD;
		final int ICON_HT = GlobalConstants.ICON_HT;
		WorldIcon occupant = tile.occupant();
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
