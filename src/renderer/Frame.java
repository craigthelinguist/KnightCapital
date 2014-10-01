package renderer;

import java.awt.image.BufferedImage;

/**
 * Frame represents a single image, that is to be used as a part of an animation.
 * It has an associated duration for how long the frame should be displayed for in an animation cycle.
 * @author myles
 *
 */
public class Frame {

	private BufferedImage frame; // The actual frame, hopefully loaded via a SpriteSheet
	private int duration; // The length that the frame is displayed for.

	/**
	 * Constructs a frame object for use in an animation
	 * @param frame : A BufferedImage to display
	 * @param duration : the int length that the frame is to be displayed for.
	 */
	public Frame(BufferedImage frame, int duration) {
		this.frame = frame;
		this.duration = duration;
	}

	/**
	 * Get the frames BufferedImage
	 * @return BufferedImage
	 */
	public BufferedImage getFrame() {
		return this.frame;
	}

	/**
	 * Set the frame to a different image
	 * @param BufferedImage
	 */
	public void setFrame(BufferedImage frame) {
		this.frame = frame;
	}

	/** Get duration of this frame **/
	public int getDuration() {
		return duration;
	}

	/** Set the duration of this frame **/
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
