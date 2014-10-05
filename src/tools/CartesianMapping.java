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
	
	public CartesianMapping (T t, Point p){
		thing = t;
		point = p;
		depth = 1;
	}
	
	public CartesianMapping(T t, Point p, int d){
		thing = t;
		point = p;
		depth =d ;
	}
	
}
