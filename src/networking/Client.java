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


			socket = new Socket("130.195.4.163", 45612);
			printWriter = new PrintWriter(socket.getOutputStream(),true);
			while(true) {
				printWriter.println(message());
			}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}


	public static String message(){
		String s = "exit";

		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	    try {
			s = bufferRead.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    if(!s.equals("exit")){
		return s;
	    }
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

