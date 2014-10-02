package networking;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientProtocol implements Runnable{

	DataInputStream in;
	
	public ClientProtocol(DataInputStream in){
		this.in = in;
		
		
	}
	@Override
	public void run() {

		String message;

		while(true){

			try {
				message = in.readUTF();
				System.out.println("global message: " + message);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

}
