package networking;

import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import controllers.WorldController;
import player.Player;

/**
 * client receives information about current state of the game
 * and relays that into the local copy of the game. it also notifies
 * the server of mouse events by the player
 *
 * @author selemon and neal
 * */
public class ClientM extends Thread implements MouseListener{

	private final Socket socket;
	private WorldController world;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private int ID;


	/**
	 * constructor
	 * @param socket - the socket for the client
	 * */
	public ClientM(Socket socket){
		this.socket = socket;

	}




	/**
	 * Receive information from server and update
	 * */
	@Override
	public void run() {

		Message message = new Message(world);

		System.out.println("ready bruh!");

		try {
//			Socket socket = new Socket(InetAddress.getLocalHost(), 8989);
			System.out.println("In there boii");
			ObjectOutputStream clientOutputStream = new ObjectOutputStream(socket.getOutputStream());
		    System.out.println("past output");
//			ObjectInputStream clientInputStream = new ObjectInputStream(socket.getInputStream());
		    System.out.println("past input");

		    byte[] gyeah = Serialization.serialize(world);
		    System.out.println("serialized");
		    clientOutputStream.write(gyeah);

//		    world = in;

//		    message = (Message) clientInputStream.readObject();

		    System.out.println("recieved bawse!!");




		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}









	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub


	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub

	}



}
