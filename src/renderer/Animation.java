package renderer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import renderer.Frame;

import tools.Log;

/**
 * Animation
 * Animation collects a list of frames that make up a stop-motion animation, and
 * the allows the user to play through each frame at a specified time step.
 *
 * Please look at RendererDemo to see how this can be used.
 *
 * @author myles
 *
 */
public class Animation {
	private int frameCount;		// Counts each frame each time the update cycle is called
								// NOTE: At this time, this depends on update being called once
								// every game loop. If a more complicated timestep is introduced
								// this class will need to be altered
	private int frameDelay;		// The delay between frames
	private int currentFrame;	// The current index of the frame that is currently being displayed
	private int totalFrames;	// Count of the total number of frames in an animation

	private boolean stopped;	// Is the animation currently halted?

	private ArrayList<Frame> frames = new ArrayList<Frame>(); // List of frame objects.

	/**
	 * Creates a new Animation
	 *
	 * @param frames is an array of BufferedImage that represent the frames of your animation, in order
	 * @param frameDelay is the delay between frames in
	 */
	public Animation(BufferedImage[] frames, int frameDelay) {
		this.frameDelay = frameDelay;
		this.stopped = true;

		// Add frames to frames list
		for(int i = 0; i < frames.length; i++) {
			addFrame(frames[i], frameDelay);
		}

		// Set all dynamic fields to beginning
		this.frameCount = 0;
		this.currentFrame = 0;

		this.totalFrames = frames.length;
	}

	/**
	 * Start Animation from the currentFrame position.
	 * Use Restart to ensure animation is played from beginning
	 */
	public void start() {
		if (!stopped) {
			return;
		}

		if(this.frames.size() == 0) {
			return;
		}

		this.stopped = false;
	}

	/**
	 * Stop the animation playing.
	 * This will not reset or restart the animation.
	 */
	public void stop () {
		if(this.frames.size() == 0) {
			return;
		}

		this.stopped = true;
	}

	/**
	 * Restart animation at first frame
	 */
	public void restart() {
		if(this.frames.size() == 0) {
			return;
		}

		this.stopped = false;
		this.currentFrame = 0;
	}

	/**
	 * Reset the animation to the first frame.
	 * This will stop the animation
	 */
	public void reset() {
		// Resets all dynamic fields
		this.stopped = true;
		this.currentFrame = 0;
		this.frameCount = 0;
	}

	/** Add a BufferedImage to the collection of frames at the end point **/
	private void addFrame(BufferedImage frame, int duration) {

		// If duration of frame is negative or 0 throw an error
		if(duration <= 0) {
			Log.print("[Animation] Invalid Duration: "+duration);
			throw new RuntimeException("[Animation] Invalid Duration: "+duration);
		}

		// Add frame to frames list
		frames.add(new Frame(frame, duration));
		currentFrame = 0;
	}

	/** Returns the current frame as a BufferedImage **/
	public BufferedImage getSprite() {
		return frames.get(currentFrame).getFrame();
	}

	/**
	 * Update the current animation
	 */
	public void update() {
		// Check to see if the animation is running or not
		if(!stopped) {

			// If it is, increment the frame counter by one.
			frameCount++;

			if (frameCount > frameDelay) {
				// Reset Frame Count
				frameCount = 0;

				// Increment the current frame to the next in the list.
				currentFrame ++;

				// Reset animation if the end frame has been reached
				if(currentFrame >  totalFrames - 1) {
					currentFrame = 0;
				}
			}
		}
	}
}
