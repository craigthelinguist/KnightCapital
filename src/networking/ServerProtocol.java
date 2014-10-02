package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerProtocol implements Runnable {


	private DataInputStream in;
	private DataOutputStream out;
	private ServerProtocol[] users;

	public ServerProtocol(DataInputStream in, DataOutputStream out, ServerProtocol[] users){

		this.in = in;
		this.out = out;
		this.users = users;

	}

	@Override
	public void run() {
		while(true){


			try {
				String message = in.readUTF();

				for(int i=0 ; i < users.length; i++){

					if(users[i]!=null){

						users[i].getOut().writeUTF(message);

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
