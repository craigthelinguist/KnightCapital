package gui.reusable;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * A MouseListener and MouseMotionListener that doesn't do anything. Used in child components that should be inert
 * to events happening in parent components. It is flyweight; there is only one, static, immutable DeadListener object.
 * @author craigthelinguist
 *
 */
public class DeadListener implements MouseListener, MouseMotionListener {

	private DeadListener(){}
	private static DeadListener deadListener = new DeadListener();
	
	public static DeadListener get(){
		return deadListener;
	}
	
	@Override public void mouseDragged(MouseEvent arg0) {}
	@Override public void mouseMoved(MouseEvent arg0) {}
	@Override public void mouseClicked(MouseEvent arg0) {}
	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mousePressed(MouseEvent arg0) {}
	@Override public void mouseReleased(MouseEvent arg0) {}

}
