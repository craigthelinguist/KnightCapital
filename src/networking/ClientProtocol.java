package networking;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.InputStreamReader;
/**
 *
 * @author Neal Hartley
 * Runnable thread implemented by the client in order to listen to responses form the server port.
 * Will hold functionality to deal with different types of incoming messages and how they will
 * affect the clients current board state.
 */
public class ClientProtocol implements Runnable{

	private static DataInputStream in;
	private boolean clean = true;
	private static DataOutputStream out;

	/**
	 * needs to be initialised with a DataInputStream from an already initialised port
	 * Stream is constantly checked for incoming messages.
	 * @param in
	 */
	public ClientProtocol(DataInputStream in, DataOutputStream out){
		this.in = in;
		this.out = out;
	}



	/**
	 * consistently checks for incoming data from socket, deals with it accordingly.
	 */
	@Override
	public void run() {

		String message;


		while(clean){
			 System.out.println("clientProtocol running");



			try {
				// currently checks for incoming string messages, functionality for
				// reading byte stream for board changes will be added.
				message = in.readUTF();
				System.out.println("global message: " + message);
			}
			catch (EOFException e) {
				this.clean = false;
				}

			catch (IOException e) {

				e.printStackTrace();
			}
		}
	}


	public Boolean getStatus(){
		return clean;

	}


}
