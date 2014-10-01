package networking;


import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
/**
 *
 * @author Neal Hartley
 *
 */
public class Client {

	private static Socket socket;
	private static PrintWriter printWriter;

	public static void main(String[] args) {

		String s = "wrong!";

		try {

			System.out.println("'exit' to terminate connection.");
			System.out.println("Send a message to the server.");

            //connects to socket based on ip and port number. Ip needs to be configured for individual testing on different computers.
			socket = new Socket("130.195.4.163", 45612);
			
			//printwriter that prints a message to the socket for the server to see.
			printWriter = new PrintWriter(socket.getOutputStream(),true);
			BufferedReader in =new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while(true) {
				printWriter.println(message());
				
				
//			    if(in.readLine()!=null){
//				System.out.println(in.readLine());
//			    }
				
				
				
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

