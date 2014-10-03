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
	private static final int HALF_TILE_HT = GlobalConstants.TILE_HT/2;
	private static final int HALF_TILE_WD = GlobalConstants.TILE_WD/2;
	
	/**
	 * Convert a point on screen in isometric projection,
	 * and return a new point converted to Cartesian coordinates for use with the world's 2d array.
	 * @param Isometric point to convert
	 * @return Converted Cartesian Point
	 */
	public static Point isometricToCartesian(Point iso, Camera camera){
		
		iso = getPt(iso,camera);
		
		iso.x = iso.x - camera.getOriginX();
		iso.y = iso.y - camera.getOriginY();
		
		final int HALF_TILE_WD = GlobalConstants.TILE_WD/2;
		final int HALF_TILE_HT = GlobalConstants.TILE_HT/2;
		
		int cartY = (int)( 0.5*(iso.y/HALF_TILE_HT - iso.x/HALF_TILE_WD) );
		int cartX = (int)( 0.5*(iso.y/HALF_TILE_HT + iso.x/HALF_TILE_WD) );
		Point cart = new Point(cartX-1,cartY);
		return cart;
		
		/**
		
		
		float offset = 0.13f; //this is super hacky, my bad FIXME

		// Calculate Cartesian X
		float cartX = (((2 * yPos) + xPos) / GlobalConstants.TILE_WD) -1;
		cartX -= ((int)cartX * offset);

		// Calculate Cartesian Y
		float cartY = (((2 * yPos) - xPos) / GlobalConstants.TILE_WD) ;
		cartY -= ((int)cartY * offset);

		//Log.print("[Converter] Converted isometric point ("+iso.x+","+iso.y+") to cartesian point ("+(int)cartX+","+(int)cartY+")");
		**/
	}

	private static final int taxi1 = GlobalConstants.TILE_WD/2 + GlobalConstants.TILE_HT/2;
	private static final int taxi2 = GlobalConstants.TILE_WD/2 + GlobalConstants.TILE_WD/2;
	private static final int taxi3 = GlobalConstants.TILE_HT/2 + GlobalConstants.TILE_HT/2;
	
	/**
	 * linear combinations of wd/2 + wd/2, wd/2 + ht/2, and ht/2 + ht/2
	 * @param dist
	 * @return
	 */
	public static boolean inCentre(Point origin, Point point){
		
		// check we have a linear combination ax + by
		int dx = point.x - origin.x;
		int dy = point.y - origin.y;
		if (dy % HALF_TILE_HT != 0 || dx % HALF_TILE_WD != 0) return false;
		
		// check a+b is even
		return (int)(dy/HALF_TILE_HT + dx/HALF_TILE_WD) %2 == 0;
		
	}
	
	public static Point getPt(Point iso, Camera cam){
		
		// taxicab distance from centre of any tile to centre of any other tile

		
		Point origin = new Point(0,0);
		origin = cartesianToIsometric(origin,cam);
		origin.x = origin.x + HALF_TILE_WD;// - cam.getOriginX();
		origin.y = origin.y + HALF_TILE_HT;// - cam.getOriginY();
		System.out.printf("CLICK AT: (%d,%d)\n", iso.x, iso.y);
		//origin.x = iso.x - cam.getOriginX();// - GlobalConstants.TILE_WD/2;
		//origin.y = iso.y - cam.getOriginY();
		
		List<Point> surroundings = new ArrayList<>();
		
		// iterate to right
		for (int x = iso.x; x < iso.x + HALF_TILE_WD; x++){
			
			if ( (origin.x - x) % HALF_TILE_WD == 0 ){
				
				// top-right tile
				for (int y = iso.y; y > iso.y - HALF_TILE_HT; y--){
					if ( (origin.y - y) % HALF_TILE_HT == 0){
						Point candidate = new Point(x,y);
						if (!inCentre(origin,candidate)){
							continue;
						}
						surroundings.add(candidate);
						break;
					}
				}
				
				// bottom-right tile
				for (int y = iso.y; y < iso.y + HALF_TILE_HT; y++){
					if ( (origin.y - y) % HALF_TILE_HT == 0){
						Point candidate = new Point(x,y);
						int taxicabCand = Geometry.taxicab(origin,candidate);
						if (!inCentre(origin,candidate)){
							continue;
						}
						surroundings.add(candidate);
						break;
					}
				}
			
			}
		}
		
		// iterate to left
		for (int x = iso.x; x > iso.x - HALF_TILE_WD && x >= 0; x--){
			if ( (origin.x - x) % HALF_TILE_WD == 0){
			
				// top-left tile
				for (int y = iso.y; y > iso.y - HALF_TILE_HT; y--){
					if ((origin.y - y) % HALF_TILE_HT == 0){
						Point candidate = new Point(x,y);
						int taxicabCand = Geometry.taxicab(origin,candidate);
						if (!inCentre(origin,candidate)){
							continue;
						}
						surroundings.add(candidate);
						break;
					}	
				}
				
				// bottom-left tile
				for (int y = iso.y; y < iso.y + HALF_TILE_HT; y++){
					if ((origin.y - y) % HALF_TILE_HT == 0){
						Point candidate = new Point(x,y);
						int taxicabCand = Geometry.taxicab(origin,candidate);
						if (!inCentre(origin,candidate)){
							continue;
						}
						surroundings.add(candidate);
						break;
					}
				}
			
				//break;
			}
		}
		
		if (surroundings.isEmpty()) return new Point(-1,-1);
		Point closestPt = surroundings.get(0);
		int dist = Geometry.taxicab(iso, closestPt);
		for (int i = 1; i < surroundings.size(); i++){
			Point otherPt = surroundings.get(i);
			int otherDist = Geometry.taxicab(iso, otherPt);
			if (otherDist < dist) closestPt = otherPt;
		}
		
		for (Point point : surroundings){
			System.out.printf("(%d,%d)\n", point.x, point.y);
		}
		
		System.out.printf("POINT CHOSEN WAS (%d,%d)\n", closestPt.x, closestPt.y);
		return closestPt;
		
		//Point cartesian = isometricToCartesian(pt,cam);
		//System.out.printf("(%d,%d)\n", cartesian.x, cartesian.y);
		//return isometricToCartesian(pt,cam);
	}
	
	/**
	 * 
	 * 
	 * 
	 * 
	 * get surrounding centre points.
	 * pt = closest of those centre points.
	 * cart = iso->cartesian (point)
	 * return cart.
	 */
	
	
	
	
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
