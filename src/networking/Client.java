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

import player.Player;
/**
 *A client connection receives information about the current state of the game
 * and relays that into the local copy of the game.
 * @author Neal Hartley and Myles Glass
 *
 */
public class Client implements Runnable {

	private static Socket socket;
	private static PrintWriter printWriter;

	private  static DataInputStream in;
	private static DataOutputStream out;


	private static ClientProtocol client;

	private ObjectInputStream input;
	private ObjectOutputStream output;
	private int ID;
	private Player player;

	public static void main(String[] args) {

//		String s = "wrong!";
//
//		try {
//
//			System.out.println("'exit' to terminate connection.");
//			System.out.println("Send a message to the server.");
//
//			//connects to socket based on ip and port number. Ip needs to be configured for individual testing on different computers.
//			System.out.println("connecting...");
//			socket = new Socket("130.195.4.155", 45612);
//			System.out.println("conected!");
//
//			//construct input stream from socket
//			in = new DataInputStream(socket.getInputStream());
//			out = new DataOutputStream(socket.getOutputStream());
//
//			//initialises the client protocol thread which is always -
//			//- listening for incoming messages from the server.
//			client = new ClientProtocol(in,out);
//			Thread listener = new Thread(client);
//			listener.start();
//
//
//			//printwriter that prints a message to the socket for the server to see.
//			printWriter = new PrintWriter(socket.getOutputStream(),true);
//
//		}
//		catch(Exception e) {
//			System.out.println(e);
//		}
//
//		while(true){
//
//			try {
//
//				    if(!client.getStatus()){
//
//				    	System.out.println("server inactive...closing application");
//				    	System.exit(0);
//
//				    }
//					out.writeUTF(message());
//
//
//			}
//			//suppress the exception shutdown the client
//			catch (EOFException e) {
//
//			System.out.println("server no longer active, shutting down");
//			System.exit(0);
//
//			}
//
//			catch (IOException e) {
//
//				e.printStackTrace();
//			}
//
//		}
	}



	public Client() {
		String s = "wrong!";

		try {

			System.out.println("'exit' to terminate connection.");
			System.out.println("Send a message to the server.");

			//connects to socket based on ip and port number. Ip needs to be configured for individual testing on different computers.
			System.out.println("connecting...");
			socket = new Socket("130.195.4.174", 45612);
			System.out.println("conected!");

			//construct input stream from socket
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			//initialises the client protocol thread which is always -
			//- listening for incoming messages from the server.
			client = new ClientProtocol(in,out);
			Thread listener = new Thread(client);
			listener.start();


			//printwriter that prints a message to the socket for the server to see.
			printWriter = new PrintWriter(socket.getOutputStream(),true);

		}
		catch(Exception e) {
			System.out.println(e);
		}

		while(true){

			try {

				    if(!client.getStatus()){

				    	System.out.println("server inactive...closing application");
				    	System.exit(0);

				    }
					out.writeUTF(message());


			}
			//suppress the exception shutdown the client
			catch (EOFException e) {

			System.out.println("server no longer active, shutting down");
			System.exit(0);

			}

			catch (IOException e) {

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
			output = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			input = new ObjectInputStream(socket.getInputStream());
			ID = in.readInt();

			player = (Player) input.readObject();
			System.out.println("client request ID: "+ID);





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
			//			try {
			//				socket.close();
			//				System.out.println("socket closed yo");
			//				System.exit(0);
			//			} catch (IOException e) {

			//				e.printStackTrace();
			//			}

			return "error!!!!";



		}
	}


	public void notifyThread(){
		System.out.println("notifying via Client");
		client.notifyChange();

	}

}

