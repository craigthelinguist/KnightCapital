package tools;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class will sort CartesianMappings given a direction.
 * @author Aaron
 *
 */
public class Sorter {

	// use the static methods
	private Sorter(){}
	
	/**
	 * Sort points from topmost to bottom-most.
	 * @param stuffToSort: list of cartesian mappings to sort.
	 */
	public static <T> void sortTopToBottom(List<CartesianMapping<?>> cities) {
		TopToBottomSorter sorter = new TopToBottomSorter();
		Collections.sort(cities,sorter);
	}
	
	private static final Point ORIGIN = new Point(0,0);
	
	/**
	 * Used to do sorting
	 **/
	
	private static class TopToBottomSorter implements Comparator<CartesianMapping<?>> {
		@Override
		public int compare(CartesianMapping arg0, CartesianMapping arg1) {
			if (arg0.depth != arg1.depth) return arg0.depth - arg1.depth;
			return Geometry.taxicab(arg0.point, ORIGIN) - Geometry.taxicab(arg1.point, ORIGIN);
		}
	}


}
