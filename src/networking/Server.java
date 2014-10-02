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

public class Server extends Thread{

	private static final int USER_LIMIT = 5;
	private static final int PORT = 45612;

	//host socket and also list of client sockets.
	private static ServerSocket serverSocket;
	private static ArrayList<Socket> clients;

	private static BufferedReader bufferedReader;
	private static String inputLine;

	//input and outputstreams
	private DataInputStream input;
	private DataOutputStream output;


	public Server() {
		// Initialise Server Socket
		initServer();

		// Wait for client
		connectClient();

		//Wait for input
		waitForInput();
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
			System.out.println("Socket : "+ PORT);
			System.out.println("-------------------------------------------");

			//Create server socket
			serverSocket = new ServerSocket(PORT);

		} catch(Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Connect client
	 */
	private void connectClient() {
		try {
			// Accept Client Connection
			Socket temp = serverSocket.accept();//just testing connections.
			clients.add(temp);
			System.out.println("got connection socket to client: "+ temp.getInetAddress());
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Wait for input from clients
	 */
	private void waitForInput() {
		try{
			for(Socket client : clients) {
				// Create a reader
				bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

				while((inputLine = bufferedReader.readLine()) != null) {
					System.out.println("[User "+clients.indexOf(client)+"] "+inputLine);

					PrintWriter printWriter = new PrintWriter(client.getOutputStream(), true);
					printWriter.print(inputLine);
					
					//echo message across all clients
					for(Socket s : clients){
						output = new DataOutputStream(s.getOutputStream());
						output.writeUTF(inputLine);					
					}

					// Special command for shutting down server. Could be exploited.
					if(inputLine.equals("shutdown")) {
						endServer();
					}

					// Go back to waiting for more input
					else {
						waitForInput();
					}
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * End Server
	 * Close each of the clients sockets and then the server.
	 */
	private void endServer() {
		try {
			for(Socket client : clients) {
				client.close();
			}

			serverSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("SERVER END.");
	}

	public static void main(String[] args) {
		new Server();
	}
}

