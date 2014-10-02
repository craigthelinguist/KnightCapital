package tools;

import renderer.Camera;
import world.World;

import java.awt.Point;

public class Geometry {

	/**
	 * Convert a point on screen in isometric projection,
	 * and return a new point converted to Cartesian coordinates for use with the world's 2d array.
	 * @param Isometric point to convert
	 * @return Converted Cartesian Point
	 */
	public static Point isometricToCartesian(Point iso, Camera camera){
		float xPos = iso.x - camera.getOriginX();
		float yPos = iso.y - camera.getOriginY();

		float offset = 0.13f; //this is super hacky, my bad FIXME

		// Calculate Cartesian X
		float cartX = (((2 * yPos) + xPos) / GlobalConstants.TILE_WD) -1;
		cartX -= ((int)cartX * offset);

		// Calculate Cartesian Y
		float cartY = (((2 * yPos) - xPos) / GlobalConstants.TILE_WD) ;
		cartY -= ((int)cartY * offset);

		int camOrientation = camera.getOrientation();

		switch(camOrientation) {
			case Camera.NORTH:	break;
			case Camera.SOUTH:	cartY = GlobalConstants.WORLD_HEIGHT - cartY;
								cartX = GlobalConstants.WORLD_WIDTH - cartX;
								break;
			case Camera.EAST: 	float tempCartX = cartX;
								cartX = cartY;
								cartY = Math.abs(GlobalConstants.WORLD_WIDTH - tempCartX);
								break;
			case Camera.WEST: 	float tempCartY = cartY;
								cartY = cartX;
								cartX = GlobalConstants.WORLD_HEIGHT - tempCartY;
								break;
			default:			break;
		}

		Log.print("[Converter] Converted isometric point ("+iso.x+","+iso.y+") to cartesian point ("+(int)cartX+","+(int)cartY+")");
		return new Point((int)cartX, (int)cartY);
	}

	/**
	 * Convert a cartesian coordinate to an isometic coordinate on screen.
	 * I haven't checked this yet so it's highly likely it doesn't work.
	 * @param cartX
	 * @param cartY
	 * @param camera
	 * @return isometric point.
	 */
	public static Point cartesianToIsometric(Point pt, Camera camera) {
		int cartX = pt.x;
		int cartY = pt.y;
		final int HALF_TILE_WD = GlobalConstants.TILE_WD/2;
		final int HALF_TILE_HT = GlobalConstants.TILE_HT/2;
		int isoX = (cartX-cartY)*HALF_TILE_WD + camera.getOriginX();
		int isoY = (cartX+cartY)*HALF_TILE_HT + camera.getOriginY();
		return new Point(isoX, isoY);
	}
	
	/**
	 * Return the taxicab distance between two points.
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static int taxicab(Point p1, Point p2){
		return Math.abs(p1.x-p2.x) + Math.abs(p1.y-p2.y);
	}
	
	/**
	 * Rotate a cartesian point inside the world's boundaries according to the perspective of the given camera
	 * @param pt: the point being rotated
	 * @param camera: camera the point is being viewed from.
	 * @return: the new position rotated according to the camera.
	 */
	public static Point rotatePoint(Point ptCart, Camera camera, World world) {
		final int TILES_ACROSS = world.NUM_TILES_ACROSS;
		final int TILES_DOWN = world.NUM_TILES_DOWN;
		int x = ptCart.x; int y = ptCart.y;
		int orientation = camera.getOrientation();
		if (orientation == Camera.EAST){
			x = TILES_ACROSS-1-x;
		}
		else if (orientation == Camera.SOUTH){
			x = TILES_ACROSS-1-x;
			y = TILES_DOWN-1-y;
		}
		else if (orientation == Camera.WEST){
			y = TILES_DOWN-1-y;
		}
		return new Point(x,y);
	}
	
}
