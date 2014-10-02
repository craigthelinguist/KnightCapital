package networking;

import java.io.DataInputStream;
import java.io.IOException;
/**
 *
 * @author Neal Hartley
 * Runnable thread implemented by the client in order to listen to responses form the server port.
 * Will hold functionality to deal with different types of incoming messages and how they will
 * affect the clients current board state.
 */
public class ClientProtocol implements Runnable{

	DataInputStream in;



	/**
	 * needs to be initialised with a DataInputStream from an already initialised port
	 * Stream is constantly checked for incoming messages.
	 * @param in
	 */
	public ClientProtocol(DataInputStream in){
		this.in = in;
	}



	/**
	 * consistently checks for incoming data from socket, deals with it accordingly.
	 */
	@Override
	public void run() {

		String message;

		while(true){

			try {
				// currently checks for incoming string messages, functionality for
				// reading byte stream for board changes will be added.
				message = in.readUTF();
				System.out.println("global message: " + message);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}
