package tools;

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
	
	/**
	 * Sort points from bottom-most to topmost.
	 * @param stuffToSort: list of cartesian mappings to sort.
	 */
	public static <T> void sortBottomToTop(List<CartesianMapping<? extends Object>> stuffToSort) {
		BottomToTopSorter sorter = new BottomToTopSorter();
		Collections.sort(stuffToSort,sorter);
	}
	
	/**
	 * Sort points from leftmost to rightmost.
	 * @param stuffToSort: list of cartesian mappings to sort.
	 */
	public static <T> void sortLeftToRight(List<CartesianMapping<? extends Object>> stuffToSort) {
		LeftToRightSorter sorter = new LeftToRightSorter();
		Collections.sort(stuffToSort, sorter);
	}

	/**
	 * Sort points from rightmost to leftmost.
	 * @param stuffToSort: list of cartesian mappings to sort.
	 */
	public static <T> void sortRightToLeft(List<CartesianMapping<? extends Object>> drawBuffer) {
		RightToLeftSorter sorter = new RightToLeftSorter();
		Collections.sort(drawBuffer, sorter);
	}
	
	/**
	 * The following classes are the sorters used to do the sorting.
	 */
	
	private static class TopToBottomSorter implements Comparator<CartesianMapping<?>> {
		@Override
		public int compare(CartesianMapping arg0, CartesianMapping arg1) {
			return arg0.point.y - arg1.point.y;
		}
	}

	
	private static class BottomToTopSorter implements
			Comparator<CartesianMapping> {
		@Override
		public int compare(CartesianMapping arg0, CartesianMapping arg1) {
			return arg1.point.y - arg0.point.y;
		}
	}

	private static class LeftToRightSorter implements
			Comparator<CartesianMapping> {
		@Override
		public int compare(CartesianMapping arg0, CartesianMapping arg1) {
			return arg0.point.x - arg1.point.x;
		}
	}

	private static class RightToLeftSorter implements
			Comparator<CartesianMapping> {
		@Override
		public int compare(CartesianMapping arg0, CartesianMapping arg1) {
			return arg1.point.x - arg0.point.x;
		}
	}
	

}
