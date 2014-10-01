package renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A Quick Demo to show how the animation, frame, and spritesheet are used together to create movement
 * @author myles
 *
 */
public class AnimationDemo {
	private JFrame frame;
	private JPanel panel;
	
	private Animation animation;
	
	@SuppressWarnings("serial")
	public AnimationDemo() {
		
		// Set up frame shit
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		
		// Set panel for drawing
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 400, 400);
				render(g);
				this.repaint();
			}
		};
		
		frame.add(panel);
		frame.setVisible(true);
		
		// load spritesheet
		SpriteSheet.loadSpriteSheet("dude_animation_sheet", 130, 150);
		
		// specify frame positions
		BufferedImage[] runFrames = {
				SpriteSheet.getSprite(0, 0),
				SpriteSheet.getSprite(1, 0),
				SpriteSheet.getSprite(2, 0),
				SpriteSheet.getSprite(3, 0),
				SpriteSheet.getSprite(4, 0),
				SpriteSheet.getSprite(5, 0),
				SpriteSheet.getSprite(6, 0),
				
				SpriteSheet.getSprite(0, 1),
				SpriteSheet.getSprite(1, 1),
				SpriteSheet.getSprite(2, 1),
				SpriteSheet.getSprite(3, 1),
				SpriteSheet.getSprite(4, 1),
				SpriteSheet.getSprite(5, 1),
				SpriteSheet.getSprite(6, 1),
				
				SpriteSheet.getSprite(0, 2),
				SpriteSheet.getSprite(1, 2),
				SpriteSheet.getSprite(2, 2),
				SpriteSheet.getSprite(3, 2),
				SpriteSheet.getSprite(4, 2),
				SpriteSheet.getSprite(5, 2)
		};
		
		// create animation
		animation = new Animation(runFrames, 1); // this wil refresh every update cycle, not ideal
		
		
		// start game
		run();
	}
	
	private void run() {
		
		//start animation
		animation.start();
		
		// Dummy Game Loop
		while(true) {
			
			// Update 'Game'
			update();
			
			// Render 'game'
			panel.repaint();
			
			//Noob pause
			try{ Thread.sleep(50); } catch(Exception e) {}
		}
	}
	
	private void update() {
		animation.update();
	}
	
	private void render(Graphics g) {
		if(animation != null) {
			g.drawImage(animation.getSprite(), 100, 100, null);
		}
	}
	
	public static void main(String[] professorsnape) {
		new AnimationDemo();
	}
	
}
