package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import world.World;
import controllers.WorldController;


/**
 * a class that can create a server and clients sets up game communication
 * @author selemon and neal
 * */
public class NetworkM {

	public static final int port = 8080;


	/**
	 * Create a server and accept clients connections.
	 * @param world - WorldControl.
	 * @param port - The port number.
	 * @param clients - Number of clients
	 *
	 *
	 **/
	public static void createServer(WorldController world, int port, int clients){

		System.out.println("listening on port : "+port);

		ServerM swerv;
		try{

			DataInputStream in;
			//array of clients connecting to the server
			ClientM[] connections = new ClientM[clients];
			ServerSocket socket = new ServerSocket(port);

			//make the state of the game waiting

			System.out.println("waiting for "+clients+" to join");
			DataOutputStream out;
			//			world.setState = waiting
			//loop till game over
			while(true){
				Socket sock = socket.accept();
				out = new DataOutputStream(sock.getOutputStream());
				out.writeUTF("hello from server");
				out.flush();

				System.out.println("accepted connection from : "+sock.getInetAddress());
				in = new DataInputStream(sock.getInputStream());
//				int id = world;

				//loop around adding a client till client equals 0
				connections[--clients] = new ClientM(sock);
				System.out.println("waiting for "+clients+" to join");
				if(clients==0){
					System.out.println("All clients are now connected");
					//make the state of the game to playing
					//example: game.setState(WorldController.playing);

					swerv = new ServerM(sock, world);
					swerv.start();

					//start the threads
					for(ClientM s:connections){
						s.start();
					}
					startGame(world, connections, sock);
					socket.close();
					//connection step completed
					return;
				}
			}



		}catch(IOException e){e.printStackTrace();}


	}


	public static void createClient(String addr, int port, String name) throws IOException {
		Socket s = new Socket(addr, port);
		System.out.println("CONNECTED TO " + addr + ": " + port);
		DataOutputStream out = new DataOutputStream(s.getOutputStream());
		out.writeUTF(name);
		out.flush();
		ClientM sv = new ClientM(s);
		sv.run();
	}



	/**
	 * play game as long as there is at least one client
	 * @param world the worldcontroller
	 * @param connections the array of clients connected to the server
	 * @param socket
	 * */
	private static void startGame(WorldController world, ClientM[] connections, Socket socket) {

		boolean clients = false;
		while(true){
			//check to see there is at least one client connected
			for(ClientM s: connections){
				if(s.isAlive()){
					clients = true;

				}
			}
			//if there are no more clients game over
			if(!clients){
				System.out.println("GAME OVER all clients disconnected");
				return;
			}

			//when need a method in world that can let us know if the state is playing
//			while(world.getState()== PLAYING){
//				Thread.yield();
//			}






		}

	}

}
