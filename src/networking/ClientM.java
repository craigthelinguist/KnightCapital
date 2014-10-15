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
	public ClientM(Socket socket, WorldController game){

		this.socket = socket;
	    this.world = game;

	}




	/**
	 * Receive information from server and update
	 * */
	@Override
	public void run() {




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
