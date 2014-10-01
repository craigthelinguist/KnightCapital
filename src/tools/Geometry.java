package tools;

import renderer.Camera;

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
	private static Point cartesianToIsometric(int cartX, int cartY, Camera camera) {
		Log.print("WARNING cart -> iso conversion untested");
		int isoX = (cartX - cartY) + camera.getOriginX();
		int isoY = ((cartX + cartY) / 2) + camera.getOriginY();

		return new Point(isoX, isoY);
	}
}
