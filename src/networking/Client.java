package networking;


import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import controllers.WorldController;
import player.Player;
/**
 *A client connection receives information about the current state of the game
 * and relays that into the local copy of the game.
 * @author Neal Hartley and Myles Glass
 *
 */
public class Client implements Runnable {

	private static Socket messageSocket;

	private static Socket moveSocket;

	private static PrintWriter printWriter;

	//World for clients!
    private WorldController world;

	//in and out for messaging.
	private  static DataInputStream in;
	private static DataOutputStream out;

	//in and out for movement.
	private static ObjectInputStream moveIn;
	private static ObjectOutputStream moveOut;

	private static ClientMessagingProtocol clientMessager;
	private static ClientMovementProtocol clientMover;

	private ObjectInputStream input;
	private ObjectOutputStream output;
	private int ID;
	private Player player;

	public static void main(String[] args) {


	}



	public Client(String ipAddress, int msgPort, int movePort, WorldController world) {
		String s = "wrong!";
		this.world = world;

		try {

			System.out.println("'exit' to terminate connection.");
			System.out.println("Send a message to the server.");

			//connects to socket based on ip and port number. Ip needs to be configured for individual testing on different computers.
			System.out.println("connecting...");


			//connect messsage socket
			messageSocket = new Socket(ipAddress, msgPort);
			System.out.println("conected messages! waiting for move socket connection.....");

			moveSocket = new Socket(ipAddress, movePort);

			System.out.println("connected move socket, hopefully fixed chat!");


			//construct input stream from socket for messaging
			in = new DataInputStream(messageSocket.getInputStream());
			out = new DataOutputStream(messageSocket.getOutputStream());


			//construct in and out stream for movement
			moveIn = new ObjectInputStream(moveSocket.getInputStream());
			moveOut = new ObjectOutputStream(moveSocket.getOutputStream());
			out.flush();
			
			System.out.println("[client] made data and object streams!");

			//initialises the client protocol thread which is always -
			//- listening for incoming messages from the server.
			clientMessager = new ClientMessagingProtocol(in,out);

			clientMover = new ClientMovementProtocol(moveIn, moveOut, world);


			Thread listener = new Thread(clientMessager);
			Thread movement = new Thread(clientMover);

			movement.start();
			listener.start();


			//printwriter that prints a message to the socket for the server to see.
			printWriter = new PrintWriter(messageSocket.getOutputStream(),true);

		}
		catch(Exception e) {
			System.out.println(e);
		}

		while(true){

			
					try {
						
						out.writeUTF(message());
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


			

		}
	}

	/**
	 * receive player information from server
	 * */


	@Override
	public void run() {

		try {
			output = new ObjectOutputStream(messageSocket.getOutputStream());
			out.flush();
			input = new ObjectInputStream(messageSocket.getInputStream());
			ID = in.readInt();

			player = (Player) input.readObject();
			System.out.println("client request ID: "+ID);


			//will do more once we get the player class going

		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String message(){
		String s = "exit";

		//creates new buffered reader for reading user input
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try {
			//assigns input to string.


			s = bufferRead.readLine();



		}

		catch (IOException e) {

			e.printStackTrace();
		}

		// if not exiting returns the user input.
		if(!s.equals("exit")){
			return s;
		}
		//otherwise it closes the socket
		else{


			return "error!!!!";



		}
	}


	public void notifyThread(){
		System.out.println("notifying via Client");
		clientMessager.notifyChange();

	}

}

