package networking;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Simple Server for distributing game information with Clients
 * @author neal and myles
 */

public class Server{

	private static final int USER_LIMIT = 5;
	private static final int PORT = 45612;
	private static final int MOVE_PORT = 45612;
	

	//host socket and also list of client sockets.
	private static ServerSocket serverMessageSocket;
	private static ServerSocket ServerMoveSocket;
	private static ArrayList<Socket> clients;
	

	//list of running serverprotocol threads. each protocol dealling with one connected user.
    private static ServerMessagingProtocol[] users = new ServerMessagingProtocol[10];
   
    private static ServerMovementProtocol[] movingUsers = new ServerMovementProtocol[10];
      
	private static BufferedReader bufferedReader;
	private static String inputLine;

	//input and outputstreams
	private DataInputStream input;
	private DataOutputStream output;
    private DataOutputStream out;

	public Server() throws IOException{
		// Initialise Server Socket
		initServer();

		// Wait for client
		connectClient();

		//Wait for input/check for disconnect
		 //waitForInput();
	}

	/**
	 * Initialise server
	 * Starts the server socket and not much else
	 */
	private void initServer() {

		// Set limit on ammout of users
		  clients = new ArrayList<Socket>();

		try {

			//Print Information
			System.out.println("Simple Server - by Myles and Neal");
			System.out.println("IP Address : " + InetAddress.getLocalHost());
			System.out.println("MessageSocket : "+ PORT);
			System.out.println("MoveSocket : " + MOVE_PORT);
			System.out.println("-------------------------------------------");

			//Create server socket
			serverMessageSocket = new ServerSocket(PORT);
			ServerMoveSocket = new ServerSocket(MOVE_PORT);

		} catch(Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Connect client
	 */
	private void connectClient() throws IOException{
		while(true){

			// Accept Client Connection
			Socket temp = serverMessageSocket.accept();//just testing connections.
			
			clients.add(temp);

		for(int i =0 ; i < 10; i++){
			System.out.println("got connection socket to client: "+ temp.getInetAddress());
			input = new DataInputStream(temp.getInputStream());
			out = new DataOutputStream(temp.getOutputStream());

			
			//first checks if any previous connections have become unusable.
			if(users[i]!=null && !users[i].getStatus() )
				//if not in use is destroyed.
				users[i]=null;
				
			
			//if this connection spot is vacant fills it with the incoming request.
			if(users[i] == null){

				users[i] = new ServerMessagingProtocol(input, out, users, i);
				Thread thread = new Thread(users[i]);
				thread.start();
				
				break;
			}
		}


		}

	}


	public static void main(String[] args) {
		try {
			new Server();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

