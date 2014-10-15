package renderer;

import java.io.Serializable;

import tools.Constants;

/**
 * Camera
 * Simple camera object to represent a camera in-game.
 * Can Pan in all directions, and rotate.
 * @author myles
 *
 */

public class Camera {

	/**
	 * 
	 */
	
	// For orientation direction
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;

	private static int origin_x;
	private static int origin_y;
	private static int orientation;

	/**
	 * Contruct a new camera object.
	 * I'm under the impression you should only need to do this once.
	 * @param int for x origin
	 * @param int for y origin
	 * @param orietation of camera
	 */
	public Camera(int originX, int originY, int orient) {
		origin_x = originX;
		origin_y = originY;
		orientation = orient;
	}

	// Setters
	public void setOrigin(int x, int y) {
		origin_x = x;
		origin_y = y;
	}
	public void setOriginX(int x) { origin_x = x; }
	public void setOriginY(int y) { origin_y = y; }

	// Getters
	public int getOriginX() { return origin_x; }
	public int getOriginY() { return origin_y; }

	public void pan(int xPan, int yPan) {
		origin_x += xPan;
		origin_y += yPan;
	}

	/**
	 *	Pan Downwards.
	 *	Panning distance is defined in Tools.GlobalConstants
	 */
	public void panDown() {
		origin_y -= Constants.CAMERA_PAN;
	}

	/**
	 *	Pan Upwards.
	 *	Panning distance is defined in Tools.GlobalConstants
	 */
	public void panUp() {
		origin_y += Constants.CAMERA_PAN;
	}

	/**
	 *	Pan Right.
	 *	Panning distance is defined in Tools.GlobalConstants
	 */
	public void panRight() {
		origin_x -= Constants.CAMERA_PAN;
	}

	/**
	 *	Pan Left.
	 *	Panning distance is defined in Tools.GlobalConstants
	 */
	public void panLeft() {
		origin_x += Constants.CAMERA_PAN;
	}

	/**
	 * Get the current orientation of the camera
	 */
	public int getOrientation() {
		return orientation;
	}

	/**
	 * Rotate camera 90 degrees clockwise
	 */
	public void rotateClockwise() {
		if(orientation == WEST) {
			orientation = NORTH;
		}
		else
			orientation++;
	}

	/**
	 * Rotate camera 90 degrees counter-clockwise
	 */
	public void rotateCounterClockwise() {
		if(orientation == NORTH) {
			orientation = WEST;
		}
		else
			orientation--;
	}

	/**
	 * Set the orientation of camera
	 * 	0 : NORTH
	 * 	1 : EAST
	 * 	2 : SOUTH
	 * 	3 : WEST
	 * @param orientation you want to set camera to
	 */
	public void setOrientation(int orient) {
		orientation = orient;
	}




}
