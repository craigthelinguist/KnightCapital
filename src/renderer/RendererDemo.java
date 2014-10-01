package renderer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import storage.TemporaryLoader;
import tools.Converter;
import tools.GlobalConstants;
import world.Party;
import world.World;

/**
 * This is a test environment for the graphics renderer.
 * @author myles
 *
 */
public class RendererDemo {

	// World Variables
	private World world;
	private Party party;
	private int party_x, party_y;

	// Camera
	private Camera camera;

	// Swing Thing
	private JFrame frame;
	private JPanel worldPanel;

	// Keyboard Bindings
	private static final int ROTATE_CW = KeyEvent.VK_R;
	private static final int ROTATE_CCW = KeyEvent.VK_E;
	private static final int PAN_UP = KeyEvent.VK_UP;
	private static final int PAN_DOWN = KeyEvent.VK_DOWN;
	private static final int PAN_RIGHT = KeyEvent.VK_RIGHT;
	private static final int PAN_LEFT = KeyEvent.VK_LEFT;

	public RendererDemo() {

		// Create World and Party.
		world = TemporaryLoader.loadWorld("world_temporary.txt");
		party = new Party("icon_ovelia.png");
		party_x = 3;
		party_y = 3;
		world.setIcon(party, party_x, party_y);

		// Set up camera
		camera = new Camera(GlobalConstants.WINDOW_WD / 2, 0, Camera.NORTH);

		// Set frame and components
		initialiseInterface();

		// Set input
		initialiseKeyInput();
		initialiseMouseInput();
	}

	/**
	 * Initialise frame, and add needed components
	 */
	private void initialiseInterface() {

		// Set up frame
		frame = new JFrame();
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(GlobalConstants.WINDOW_WD, GlobalConstants.WINDOW_HT);

		// Set world panel
		worldPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, GlobalConstants.WINDOW_WD, GlobalConstants.WINDOW_HT);
				WorldRenderer.render(world, g, getSize(), camera);
				this.repaint();
			}
		};

		// Let there be light
		worldPanel.setVisible(true);
		frame.add(worldPanel);
		frame.setVisible(true);
	}

	/**
	 * Set the keyboard interaction for demo
	 * Must be done after world panel is constructed
	 */
	private void initialiseKeyInput() {
		KeyListener keyListener = new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int keycode = e.getKeyCode();

				if(keycode == ROTATE_CW) {
					camera.rotateClockwise();
				}

				else if(keycode == ROTATE_CCW) {
					camera.rotateCounterClockwise();
				}

				else if(keycode == PAN_UP) {
					camera.panUp();
				}

				else if(keycode == PAN_DOWN) {
					camera.panDown();
				}

				else if(keycode == PAN_RIGHT) {
					camera.panRight();
				}

				else if(keycode == PAN_LEFT) {
					camera.panLeft();
				}

				worldPanel.repaint();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		};

		frame.addKeyListener(keyListener);
	}

	/**
	 * Set the mouse interaction for demo
	 * Must be done after world panel is constructed
	 */
	private void initialiseMouseInput() {
		MouseListener mouseListener = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// get mouse point x
				int x = e.getX();

				//get mouse point y
				int y = e.getY();

				//create point, converting from iso to cart
				Point p = Converter.isometricToCartesian(new Point(x, y), camera);
				int arrayX = p.x;
				int arrayY = p.y;

				// if the point is within the world boundaries
				if (arrayX>=0 && arrayX<world.NUM_TILES_ACROSS
						&& arrayY>=0 && arrayY<world.NUM_TILES_DOWN){

					// remove player icon from previous position
					world.setIcon(null, party_x, party_y);

					// add player icon to next position
					world.setIcon(party, arrayX, arrayY);

					// update icon position
					party_x = arrayX;
					party_y = arrayY;

					worldPanel.repaint();
				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		};

		frame.addMouseListener(mouseListener);

	}

	public static void main(String[] dumbledoredies) {
		new RendererDemo();
	}
}
