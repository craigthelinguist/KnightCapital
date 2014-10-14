package networking;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import player.Player;
import world.World;
import controllers.WorldController;

/**
 * recieves events from a client connection by a socket
 * movement events are registered with the game. the server is also responsible
 * for transmiting information to the client about the game state
 *
 * @author Selemon and Neal
 * */
public class ServerM extends Thread{

	private final WorldController world;
	private final int ID;
	private final Socket socket;


	/**
	 * Constructor
	 * @param socket used to communicate with the client
	 * @param uid the ID of the player
	 * @param game the worldcontroller
	 * */
	public ServerM(Socket socket, int uid, WorldController game) {
		this.socket = socket;
		this.ID = uid;
		this.world = game;
	}


	public ServerM(Socket socket, WorldController world) {
		this.socket = socket;
		this.world = world;
		this.ID = 3;
	}


	/**
	 * create new stream and wait for client movement events
	 * send new player information to client
	 * */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("working Server CLASS");
		try{
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			//extract the objects
			Message message = (Message) in.readObject();

			//get the initial state of the player and write to stream
			out.write(ID);
//			out.writeObject(world.getPlayer());
			out.reset();
			while(true){//this should change to see if the current player is playing
				//check if there is bytes to read
				

//					int input = in.readInt();//event from client
					//player movement
					MouseEvent event = message.getMouseEvent();
					Point destination = new Point(event.getX(), event.getY());
					Player player = message.getWorld().getPlayer();
					//gets the tile at the clicked point
//					world.moveParty(player, location, destination)
					world.mousePressed(event);
					//broadcast the state of the player
					Message buf = new Message(ID, world);
					out.writeObject(buf);
					out.flush();
					out.reset();

				


			}



//			socket.close();

		}

		catch(IOException e){
			System.out.println("player "+ID+" disconnected");
		}

		catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}






}
