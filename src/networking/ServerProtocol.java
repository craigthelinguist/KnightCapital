package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

public class ServerProtocol implements Runnable {


	private DataInputStream in;
	private DataOutputStream out;
	private ServerProtocol[] users;
	private int iAm;
	private Boolean clean = true;

	public ServerProtocol(DataInputStream in, DataOutputStream out, ServerProtocol[] users, int playNum){

		System.out.println("made a protocol");


		for(int i = 0; i<users.length; i++){

			if(users[i]==null){
				System.out.println(i--);
				break;
			}

		}

		this.in = in;
		this.out = out;
		this.users = users;
		this.iAm = playNum;

	}

	@Override
	public void run() {

		String message;

		while(clean){

			try {
				message = in.readUTF();
				System.out.println("incoming message is:" + message);

				for(int i=0 ; i < users.length; i++){

					if(users[i]!=null){
						System.out.println("users!=null");

						users[i].getOut().writeUTF( "player "+ iAm + "  : "  +message);

					}
				}
			} catch (EOFException e) {
                clean = false;
				//exception catched and loop stopped. Cleaned and destroyed in server
			}

			 catch (IOException e) {

				e.printStackTrace();
			}

		}

	}

	public DataOutputStream getOut(){
		return this.out;

	}
	
	public Boolean getStatus(){
		
		return clean;
		
	}

}
