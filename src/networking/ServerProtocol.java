package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerProtocol implements Runnable {


	private DataInputStream in;
	private DataOutputStream out;
	private ServerProtocol[] users;

	public ServerProtocol(DataInputStream in, DataOutputStream out, ServerProtocol[] users){

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

	}

	@Override
	public void run() {

		String message;

		while(true){




			try {
				message = in.readUTF();
				System.out.println("incoming message is:" + message);

				for(int i=0 ; i < users.length; i++){

					if(users[i]!=null){
						System.out.println("users!=null");

						users[i].getOut().writeUTF( "player "+ i+ " : "  +message);

					}


				}

			} catch (IOException e) {

				e.printStackTrace();
			}



		}




	}

	public DataOutputStream getOut(){
		return this.out;

	}

}
