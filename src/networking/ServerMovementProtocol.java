package networking;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
 * server side dealing with incoming packets. uses them for updating
 */

public class ServerMovementProtocol implements Runnable {
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Connection[] users;
	private int playNum;
    private Packet toDisperse;

    private WorldController world;


	public ServerMovementProtocol(ObjectInputStream in, ObjectOutputStream out, Connection[] users, int playNum, WorldController world){

		System.out.println("[IMPORTANT]made a movement protocol!");

		this.in=in;
		this.out=out;
		this.users = users;
		this.playNum = playNum;
		this.world = world;

	}

	/**
	 * loops waiting for input. deals with it accordingly.
	 */

	@Override
	public void run() {

		while(true){

			try {

				toDisperse = (Packet) in.readObject();
				System.out.println("got a packet from client!");
				System.out.println(toDisperse.getI() + " " + toDisperse.getJ());





				World model = world.getWorld();
				Tile[][] tiles = model.getTiles();
				for (int i = 0; i < tiles.length; i++){
					for (int j = 0; j < tiles[i].length; j++){

						Tile tile = tiles[i][j];
						WorldIcon occupant = tile.occupant();

						if (occupant instanceof Party){
							Party party = (Party)occupant;

							System.out.println("found a party instance");

							if(party.getOwner().slot == toDisperse.getplayer().slot){

								System.out.println("party was owned by this dude!");

							System.out.println(	model.moveParty(party.getOwner(), new Point(i, j), new Point( toDisperse.getJ() , toDisperse.getI())));


							//world.getGui().redraw();


							}
						}
					}

				}








//				for(int i= 0 ; i< users.length && users[i] != null; i++){
//
//					ServerMovementProtocol prot = users[i].getMoveProt();
//
//					ObjectOutputStream tempOut = prot.getOut();
//
//					tempOut.writeObject(toDisperse);
//
//
//
//
//					System.out.println("[SMP]sending packet out to user: " + i);
//				}
//
//
//
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}






			}

	}

	private ObjectOutputStream getOut() {

		return this.out;

	}






}
