package networking;

import java.net.ServerSocket;
import java.net.Socket;



/**
 * 
 * @author Neal
 * just an object for grouping together the server protocols, keeps them together.
 *
 */
public class Connection {

	ServerMessagingProtocol connection;
	ServerMovementProtocol move;
	
	
	
	
	/*
	 * 
	 */
	public Connection(ServerMessagingProtocol connection, ServerMovementProtocol move){
		
		this.connection = connection;
		this.move = move;
		
		
	}


	public ServerMessagingProtocol getMessageProt() {
		
		return connection;
	
	}
	
	public ServerMovementProtocol getMoveProt() {
		
		return move;
	
	}
	
	
}
