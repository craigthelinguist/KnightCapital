package networking;

import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

		System.out.println("Connection found");
		try{

             System.out.println("inside try[CLIENT]");
             Message message;

			in = new ObjectInputStream(socket.getInputStream());

			System.out.println("past in [client]");
			//Retrieve the message sent from the server
			//Message message = (Message) in.readObject();
			//ID = message.getID();
			System.out.println("message recieved CLIENT");
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			//update the gui
			//
			//
			boolean exit = false;

			world.getGui().getCanvas().addMouseListener(this);
			while(!exit){

				System.out.println("looping[CLIENT]");

				message = (Message) in.readObject();

				world = message.getWorld();
				if(world.getGui().getCanvas().getClicked()){
					try {
						System.out.println("a changed has happened client side");
						world.getGui().getCanvas().setClicked(false);
						out.writeObject(new Message(null, world, ID));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.out.println("should have worldController.");
				//here we up date the gui with world
				//and display it



			}


			socket.close();

		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
