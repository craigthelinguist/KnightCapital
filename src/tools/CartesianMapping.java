package tools;

import java.awt.Point;

/**
 * A CartesianMapping is an indexing of some point in Cartesian space to something,
 * It's a way to locate things.
 * @author craigthelinguist
 * @param <T>: the thing
 */
public class CartesianMapping<T> {

	public final T thing;
	public final Point point;
	public final int depth;
	public final int intensity;
	
	public CartesianMapping(int d, T t, Point p, int i){
		thing = t;
		point = p;
		depth = d;
		intensity = i;
	}

}
