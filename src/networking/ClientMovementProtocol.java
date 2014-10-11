package networking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.JFrame;

public class ClientMovementProtocol implements MouseListener, KeyListener, Runnable{
	private DataInputStream in;
	private DataOutputStream out;
	private int keyPressed;


	//System.out.println(String.valueOf(ke.getKeyChar()).toUpperCase());


	public ClientMovementProtocol(DataInputStream in, DataOutputStream out){

		this.in=in;
		this.out=out;

	}











	@Override
	public void run() {
		while(true){




		}

	}


	public static void main(String[] args) {



	}











	@Override
	public void keyTyped(KeyEvent e) {


	}











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
