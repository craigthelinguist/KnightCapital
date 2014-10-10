package networking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ClientMovementProtocol implements ActionListener, Runnable{
	private DataInputStream in;
	private DataOutputStream out;


	public ClientMovementProtocol(DataInputStream in, DataOutputStream out){

		this.in=in;
		this.out=out;

	}



	private static String hello;


	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			out.writeUTF(e.paramString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}


	public static void main(String[] args) {

		System.out.println(hello);

	}

}
