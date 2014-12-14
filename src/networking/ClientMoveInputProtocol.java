package networking;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import world.World;
import world.icons.Party;
import world.icons.WorldIcon;
import world.tiles.Tile;
import controllers.WorldController;

/**
 * 
 * @author Neal Hartley && Selemon Yitbarek
 *  deals with incoming movement packets. Uses them to update players locations on map.
 */
public class ClientMoveInputProtocol implements Runnable{

	
	private WorldController world;
	private ObjectInputStream in;
	private Packet incoming;
	
	public ClientMoveInputProtocol(ObjectInputStream in, WorldController world) {
	
		this.world = world;
		this.in = in;
		
		
	}

	@Override
	public void run() {
		while(true){
			
			try {
				incoming = (Packet) in.readObject();
				
				
				System.out.println(    "[CMIP]recieved packet from server with deets:" + incoming.getI() + "  "  + incoming.getJ()  );
				
				
				World model = world.getWorld();
				Tile[][] tiles = model.getTiles();
				for (int i = 0; i < tiles.length; i++){
					for (int j = 0; j < tiles[i].length; j++){

						Tile tile = tiles[i][j];
						WorldIcon occupant = tile.occupant();
						
						if (occupant instanceof Party){
							Party party = (Party)occupant;
                            
							System.out.println("found a party instance");
							
							if(party.getOwner().getSlot() == incoming.getplayer().getSlot()){
 
								System.out.println("party was owned by this dude!");
								
							System.out.println(	model.moveParty(party.getOwner(), new Point(i, j), new Point( incoming.getI() , incoming.getJ())));
				
							}
						}
					}
				}
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
	}

	
	
	
	
	
	
}
