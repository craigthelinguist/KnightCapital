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
	public static DataInputStream in;
	public static DataOutputStream out;
	public static ServerSocket socket;
private static int numConnects = 0;

	/**
	 * Create a server and accept clients connections.
	 * @param world - WorldControl.
	 * @param port - The port number.
	 * @param clients - Number of clients
	 * @throws IOException
	 *
	 *
	 **/
	public static void createServer(WorldController world, int port, int clients) throws IOException{




			}




	public static void createClient(String addr, int port, String name, WorldController game) throws IOException {
		Socket s = new Socket(addr, port);
		System.out.println("CONNECTED TO " + addr + ": " + port);
		DataOutputStream out = new DataOutputStream(s.getOutputStream());
		out.writeUTF(name);
		out.flush();
		DataInputStream input = new DataInputStream(s.getInputStream());
		System.out.println(input.readUTF());



		ClientM sv = new ClientM(s, game);

		sv.run();


	}



	/**
	 * play game as long as there is at least one client
	 * @param world the worldcontroller
	 * @param connections the array of clients connected to the server
	 * @param socket
	 * */
	private static void startGame(WorldController world, Socket[] connections, Socket socket) {

		boolean clients = false;
		while(true){
			//check to see there is at least one client connected
			for(Socket s: connections){
				if(s.isConnected()){
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
