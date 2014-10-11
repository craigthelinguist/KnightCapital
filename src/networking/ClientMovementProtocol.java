package networking;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author hartleneal
 *
 *supposed to handle mouse and key inputs from the client side and then send them to the serverMovementProtocol
 *which then deals with them accordingly in order to update game state.
 *
 */
public class ClientMovementProtocol implements MouseListener, KeyListener, Runnable{
	private DataInputStream in;
	private DataOutputStream out;
	private int keyPressed;


	//System.out.println(String.valueOf(ke.getKeyChar()).toUpperCase());


	public ClientMovementProtocol(DataInputStream in, DataOutputStream out){

		this.in=in;
		this.out=out;


		JFrame f =  new JFrame();
		JPanel x =  new JPanel();
		x.add(new JLabel("Test"));https://developer.valvesoftware.com/wiki/Source_Multiplayer_Networking

		x.addMouseListener(this);
		x.addKeyListener(this);
		x.setSize(200, 200);
		f.add(x);


		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}





	@Override
	public void run() {


	}


	public static void main(String[] args) {



	}




	@Override
	public void keyTyped(KeyEvent e) {


	}




	/**
	 * when a key event is pressed it turns it into an integer which is sent via the outputstream
	 * to the server side.
	 */

	@Override
	public void keyPressed(KeyEvent e) {


		keyPressed = e.getKeyCode();
		String keyPressedString = e.toString();


		System.out.println("in client move prot and a key was pressed: " + keyPressed);

		try {
			out.writeInt(keyPressed);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		System.out.println("mouse pressed!!");

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
