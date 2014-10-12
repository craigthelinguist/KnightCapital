package networking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


/**
 *
 * @author hartleneal
 * server side dealing with key events and mouse events sent from the client side movement protocol.
 */

public class ServerMovementProtocol implements Runnable {
	private DataInputStream in;
	private DataOutputStream out;
	private Connection[] users;
	private int playNum;



	public ServerMovementProtocol(DataInputStream in, DataOutputStream out, Connection[] users, int playNum){

		System.out.println("made a movement protocol!");

		this.in=in;
		this.out=out;
		this.users = users;
		this.playNum = playNum;

	}

	/**
	 * loops waiting for input. deals with it accordingly.
	 */

	@Override
	public void run() {
		// TODO Auto-generated method stub


		int incoming;

		while(true){

			try {
				incoming = in.readInt();
				System.out.println("player: " + playNum +" pressed key event int: " + incoming);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}




		}

	}






}
