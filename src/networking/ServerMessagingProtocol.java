package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 *
 * @author hartleneal
 *protocol for dealing with messages sent from the chat client. Just an echo server. recieves a message from a given user and bounces it back
 *to all clients.
 */

public class ServerMessagingProtocol implements Runnable {


	private DataInputStream in;
	private DataOutputStream out;
	private Connection[] users;
	private int iAm;
	private Boolean clean = true;

	public ServerMessagingProtocol(DataInputStream in, DataOutputStream out, Connection[] users, int playNum){

		System.out.println("made a  messaging protocol protocol");


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

			//trys to read an incoming string from the client.
			try {
				message = in.readUTF();
				System.out.println("incoming message is:" + message);


				//goes through all clients output streams, spitting message back across all channels.
				for(int i=0 ; i < users.length; i++){

					if(users[i]!=null){
						System.out.println("users!=null");

						users[i].getMessageProt().getOut().writeUTF( "player "+ iAm + "  : "  +message);

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
