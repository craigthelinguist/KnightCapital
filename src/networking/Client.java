package networking;


import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
/**
 *
 * @author Neal Hartley and Myles Glass
 *
 */
public class Client {

	private static Socket socket;
	private static PrintWriter printWriter;
    private static DataInputStream in;

	public static void main(String[] args) {

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


			//initialises the client protocol thread which is always -
			//- listening for incoming messages from the server.
			ClientProtocol client = new ClientProtocol(in);
			Thread listener = new Thread(client);
			listener.start();


			//printwriter that prints a message to the socket for the server to see.
			printWriter = new PrintWriter(socket.getOutputStream(),true);
			

			while(true) {
				printWriter.println(message());


			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

/**
 *
 * @return message to send to server.
 */
	public static String message(){
		String s = "exit";

		//creates new buffered reader for reading user input
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	    try {
	    	//assigns input to string.
			s = bufferRead.readLine();
		} catch (IOException e) {

			e.printStackTrace();
		}

	    // if not exiting returns the user input.
	    if(!s.equals("exit")){
		return s;
	    }
	    //otherwise it closes the socket
	    else{
	    	try {
				socket.close();
				System.out.println("socket closed yo");
				System.exit(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    	return "error!!!!";



	    }
	}
}

