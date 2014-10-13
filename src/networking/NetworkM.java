package networking;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
		
		try{
			DataInputStream in;
			//array of clients connecting to the server
			ServerM[] connections = new ServerM[clients];
			ServerSocket socket = new ServerSocket(port);

			//make the state of the game waiting

			System.out.println("waiting for "+clients+" to join");
			//			world.setState = waiting
			//loop till game over
			while(true){
				Socket sock = socket.accept();
				System.out.println("accepted connection from : "+sock.getInetAddress());
				in = new DataInputStream(sock.getInputStream());
				int id = world.getPlayer().slot;

				//loop around adding a client till client equals 0
				connections[--clients] = new ServerM(sock, id, world);
				if(clients==0){

					//make the state of the game to playing
					//example: game.setState(WorldController.playing);
					
					//start the threads
					for(ServerM s:connections){
						s.start();
					}
					startGame(world, connections);
					socket.close();
					//connection step completed
					return;
				}
			}



		}catch(IOException e){e.printStackTrace();}


	}

	/**
	 * play game as long as there is at least one client
	 * @param world the worldcontroller
	 * @param connections the array of clients connected to the server
	 * */
	private static void startGame(WorldController world, ServerM[] connections) {

		boolean clients = false;
		while(true){
			//check to see there is at least one client connected
			for(ServerM s: connections){
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
