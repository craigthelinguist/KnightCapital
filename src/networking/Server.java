package networking;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Simple Server for distributing game information with Clients
 * @author neal and myles
 *
 */
public class Server extends Thread{

	private static final int USER_LIMIT = 5;
	private static final int PORT = 45612;

	private static ServerSocket serverSocket;
	private static Socket[] clientSocket;
	private static int userCount;
	private static BufferedReader bufferedReader;
	private static String inputLine;

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

	private void initServer() {

		// Set limit on ammout of users
		clientSocket = new Socket[USER_LIMIT - 1];
		userCount = 0;

		try {

			//Print Information
			System.out.println("Simple Server - by Myles and Neal");
			System.out.println("IP Address : " + InetAddress.getLocalHost());
			System.out.println("Socket : "+ PORT);

			serverSocket = new ServerSocket(PORT);

		} catch(Exception e) {
			System.out.println(e);
		}
	}

	private void connectClient() {
		try {
			// Accept Client Connection
			clientSocket[userCount] = serverSocket.accept();
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}

	private void waitForInput() {
		try{
			// Create a reader
			bufferedReader = new BufferedReader(new InputStreamReader(clientSocket[userCount].getInputStream()));

			while((inputLine = bufferedReader.readLine()) != null) {
				System.out.println(inputLine);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}


	private void updateClient(String serverMsg) {

	}


	public static void main(String[] args) {
		new Server();
	}
}

