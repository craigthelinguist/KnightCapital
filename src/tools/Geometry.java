package tools;

import renderer.Camera;
import world.World;

import java.util.List;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

public class Geometry {

	private static final int TILE_HT = GlobalConstants.TILE_HT;
	private static final int TILE_WD = GlobalConstants.TILE_WD;
	private static final int HALF_TILE_HT = TILE_HT/2;
	private static final int HALF_TILE_WD = TILE_WD/2;
	
	/**
	 * Convert an isometric point to a cartesian point.
	 * @param iso: a point in isometric space.
	 * @param camera: the view by which everything is being projected.
	 * @return Converted Cartesian Point
	 */
	public static Point isometricToCartesian(Point iso, Camera camera){
		
		// the isometric -> cartesian function doesn't work for all points inside an isometric tile
		// because ours is not a true isometric projection - we're just translating and stacking tiles
		// in a way that gives the illusion. however, isometric -> cartesian formula still works if
		// iso is the midpoint of a tile, so let's find the closest midpoint to iso and perform the
		// iso -> cart function.
		
		// get the closest mid point
		iso = getClosestMidPoint(iso,camera);
		
		// un-translate the isometric perspective
		iso.x = iso.x - camera.getOriginX();
		iso.y = iso.y - camera.getOriginY();
	
		// isometric -> cartesian
		int cartY = (int)( 0.5*(iso.y/HALF_TILE_HT - iso.x/HALF_TILE_WD) );
		int cartX = (int)( 0.5*(iso.y/HALF_TILE_HT + iso.x/HALF_TILE_WD) );
		return new Point(cartX-1,cartY);
		
	}

	/**
	 * Returns true if the vector represented by origin can be expressed as the vector represented
	 * by point + the linear combination ax + by, where a = tile_wd/2 and b = tile_ht/2. Put another
	 * way, returns true if origin.x = point.x + ax, and origin.y = point.y + by.
	 * 
	 * Put another way, if origin is the centre of a tile, and point is also the centre of a tile,
	 * then this will return true.
	 * 
	 * @param origin: a point in isometric space.
	 * @param point: another point in isometric space.
	 * @return: true if one is a linear combination of the other.
	 */
	private static boolean isLinearCombination(Point origin, Point point){
		
		// check we have a linear combination ax + by
		int dx = point.x - origin.x;
		int dy = point.y - origin.y;
		if (dy % HALF_TILE_HT != 0 || dx % HALF_TILE_WD != 0) return false;
		
		// check a+b is even: a+b odd means we're at a corner where tile vertices meet
		return (int)(dy/HALF_TILE_HT + dx/HALF_TILE_WD) %2 == 0;
		
	}
	
	/**
	 * Given a point iso in isometric space projected from the perspective of the camera, this method will
	 * return the point closest to iso that is the centre of a tile.
	 * @param iso: a point in isometric space.
	 * @param cam: camera by which the space is being projected.
	 * @return: the closest point to iso that is the centre of a tile.
	 */
	private static Point getClosestMidPoint(Point iso, Camera cam){
		
		// use the topmost point as a reference
		Point origin = new Point(0,0);
		origin = cartesianToIsometric(origin,cam);
		origin.x = origin.x + HALF_TILE_WD;
		origin.y = origin.y + HALF_TILE_HT;
		
		// the idea is that we look around iso for points that are the middle of tiles.
		// these points are at most HALF_TILE_WD away from iso in the x direction, and
		// at most HALF_TILE_HT away from iso in the y direction, so that tells us the
		// limits of iteration. Since the intersections of tile corners also align with
		// the middle of tiles, we have to check that we haven't actually found one of
		// those which we do using the isLinearCombination function.
		List<Point> surroundings = new ArrayList<>();
		
		// look right of iso.
		for (int x = iso.x; x < iso.x + HALF_TILE_WD; x++){
			
			// we're at an x-coordinate that aligns with the middle of the tile.
			if ((origin.x - x) % HALF_TILE_WD == 0){
				
				// look up for the top-right tile.
				for (int y = iso.y; y > iso.y - HALF_TILE_HT; y--){
					if ( (origin.y - y) % HALF_TILE_HT == 0){
						Point candidate = new Point(x,y);
						if (!isLinearCombination(origin,candidate)){
							continue;
						}
						surroundings.add(candidate);
						break;
					}
				}
				
				// look down for the bottom-right tile.
				for (int y = iso.y; y < iso.y + HALF_TILE_HT; y++){
					if ( (origin.y - y) % HALF_TILE_HT == 0){
						Point candidate = new Point(x,y);
						if (!isLinearCombination(origin,candidate)){
							continue;
						}
						surroundings.add(candidate);
						break;
					}
				}
			
				break;
			}
			
		}
		
		// look left of iso
		for (int x = iso.x; x > iso.x - HALF_TILE_WD && x >= 0; x--){
			
			// we're at an x-coordinate that aligns with the middle of the tile.
			if ((origin.x - x) % HALF_TILE_WD == 0){
			
				// look up for the top-left tile.
				for (int y = iso.y; y > iso.y - HALF_TILE_HT; y--){
					if ((origin.y - y) % HALF_TILE_HT == 0){
						Point candidate = new Point(x,y);
						if (!isLinearCombination(origin,candidate)){
							continue;
						}
						surroundings.add(candidate);
						break;
					}	
				}
				
				// look up for the bottom-left tile.
				for (int y = iso.y; y < iso.y + HALF_TILE_HT; y++){
					if ((origin.y - y) % HALF_TILE_HT == 0){
						Point candidate = new Point(x,y);
						if (!isLinearCombination(origin,candidate)){
							continue;
						}
						surroundings.add(candidate);
						break;
					}
				}
			
				break;
			}
		}
		
		// now that we have our points we get the closest one.
		if (surroundings.isEmpty()) return new Point(-1,-1);
		Point closestPt = surroundings.get(0);
		int dist = Geometry.taxicab(iso, closestPt);
		for (int i = 1; i < surroundings.size(); i++){
			Point otherPt = surroundings.get(i);
			int otherDist = Geometry.taxicab(iso, otherPt);
			if (otherDist < dist) closestPt = otherPt;
		}
		return closestPt;
	}
	
	/**
	 * Convert a cartesian coordinate to an isometic coordinate on screen.
	 * @param cartX
	 * @param cartY
	 * @param camera
	 * @return isometric point.
	 */
	public static Point cartesianToIsometric(Point pt, Camera camera) {
		int cartX = pt.x;
		int cartY = pt.y;
		int isoX = (cartX-cartY)*HALF_TILE_WD + camera.getOriginX();
		int isoY = (cartX+cartY)*HALF_TILE_HT + camera.getOriginY();
		return new Point(isoX, isoY);
	}
	
	/**
	 * Return the taxicab distance between two points.
	 * @param p1: first point.
	 * @param p2: second point.
	 * @return: taxicab distance between the two points.
	 */
	public static int taxicab(Point p1, Point p2){
		return Math.abs(p1.x-p2.x) + Math.abs(p1.y-p2.y);
	}
	
	/**
	 * Given a rotated point and the perspective by which it was rotated, recovers the original point.
	 * @param ptRotated: a point rotated by the perspective of some camera.
	 * @param camera: the camera whose perspective this point was rotated by.
	 * @param world: world in which this point exists.
	 * @return: the original point
	 */
	public static Point recoverOriginalPoint(Point ptRotated, Camera camera, World world){
		
		// To whom it may concern,
		//
		// it really is this simple: you just rotate it again by the same perspective. 
		// That might not be so obvious so I'm leaving it like this for the benefit of others,
		// but if you want to see why it works the rotatePoint method just horizontally or vertically
		// mirrors points in an array.
		//		-- sincerely,
		//			  Aaron.
		return rotatePoint(ptRotated,camera,world);
		
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
