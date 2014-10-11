package networking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ServerMovementProtocol {
	private DataInputStream in;
	private DataOutputStream out;
	private Connection[] users;
	private int playNum;
	
	public ServerMovementProtocol(DataInputStream in, DataOutputStream out, Connection[] users, int playNum){
		
		this.in=in;
		this.out=out;
		this.users = users;
		this.playNum = playNum;
		
	}
	
	
	
	
	

}
