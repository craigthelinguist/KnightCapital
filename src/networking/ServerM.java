package networking;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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

	private WorldController world;
	private final int ID;
	private final Socket socket;
	public static ObjectInputStream in;
	public static ObjectOutputStream out;


	/**
	 * Constructor
	 * @param socket used to communicate with the client
	 * @param uid the ID of the player
	 * @param game the worldcontroller
	 * */
//	public ServerM(Socket socket[], int uid, WorldController game) {
//		this.socket = socket;
//		this.ID = uid;
//		this.world = game;
//	}
//
//
	public ServerM(Socket socket, WorldController world) {
		this.socket = socket;
		this.world = world;
		this.ID = 3;
	}

//	public ServerM(WorldController world){
//		this.world = world;
//	}




	public void run(){
		//eternal loop.
//<<<<<<< HEAD
		Message message = null;
		try {
//			ServerSocket socketConnection = new ServerSocket(8989);
			System.out.println("Server is waiting");
//			Socket sock = socketConnection.accept();
			ObjectInputStream serverInputStream = new ObjectInputStream(socket.getInputStream());
			System.out.println("past input");
			ObjectOutputStream serverOutputStream = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("past output");
//			message = (Message) serverInputStream.readObject();

			byte[] gyeah = (byte[]) serverInputStream.readObject();
			System.out.println("recieved");
			world = Serialization.deserialize(gyeah);
			System.out.println("deserialized");

//			world = message.getWorld();
//			MouseEvent event = message.getMouseEvent();
//			world.mousePressed(event);

//			message = new Message(event, world, 3);
			gyeah = Serialization.serialize(world);
			serverOutputStream.write(gyeah);

			System.out.println("sent bawse!!");

			serverInputStream.close();
			serverOutputStream.close();



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//=======
//		while(true){
//
//			System.out.println("looping[SERVER]");
//
//
//			//loop through socket connections checking for updates.
//			for(int i = 0; i < socket.length; i++){
//				System.out.println("checking input from :" + i + "  connection");
//				try {
//
//					in = new ObjectInputStream(socket[i].getInputStream());
//
//
//					Message message = (Message) in.readObject();
//
////					MouseEvent event = message.getMouseEvent();
//
//					world = message.getWorld();
//					System.out.println("updated my world[SERVERM]");
//
//					for(int j =  0 ; j<socket.length; j++){
//
//						out = new ObjectOutputStream(socket[i].getOutputStream());
//
//						Message mess = new Message(world);
//
//						out.writeObject(mess);
//						System.out.println("sent out world to" + j + "player");
//					}
//
//
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//
//
//			}

		}


	}









}
