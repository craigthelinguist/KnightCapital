package networking;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import world.World;
import world.icons.Party;
import world.icons.WorldIcon;
import world.tiles.Tile;
import controllers.WorldController;


/**
 *
 * @author Neal Hartley && Selemon Yitbarek
 *
 *supposed to handle mouse and key inputs from the client side and then send them to the serverMovementProtocol
 *which then deals with them accordingly in order to update game state.
 *
 */
public class ClientMovementProtocol implements  Runnable{
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private WorldController world;
	private int keyPressed;

	private Point current;
	private int currentILoc;
	private int currentJLoc;

	private Packet incoming;

	private ClientMoveInputProtocol  readIncoming;


	//System.out.println(String.valueOf(ke.getKeyChar()).toUpperCase());


	public ClientMovementProtocol(ObjectInputStream in, ObjectOutputStream out, WorldController world){

		this.in=in;
		this.out=out;
		this.world = world;

		System.out.println("[client move protocol instantiated.]");

		readIncoming = new ClientMoveInputProtocol(in, world);
		Thread thread = new Thread(readIncoming);
		thread.start();


		World model = world.getWorld();
		Tile[][] tiles = model.getTiles();
		for (int i = 0; i < tiles.length; i++){
			for (int j = 0; j < tiles[i].length; j++){

				Tile tile = tiles[i][j];
				WorldIcon occupant = tile.occupant();
				if (occupant instanceof Party){
					Party party = (Party)occupant;

					if(party.getOwner() == world.getPlayer()){

						currentILoc = i;
						currentJLoc = j;


					}

				}

			}
		}





	}





	@Override
	public void run() {

		while(true){

			World model = world.getWorld();
			Tile[][] tiles = model.getTiles();
			for (int i = 0; i < tiles.length; i++){
				for (int j = 0; j < tiles[i].length; j++){

					Tile tile = tiles[i][j];
					WorldIcon occupant = tile.occupant();
					if (occupant instanceof Party){
						Party party = (Party)occupant;

						if(party.getOwner() == world.getPlayer()){


							if(currentILoc!=i && currentJLoc != j){
                             System.out.println("there has been a change!");
                             Packet update = new Packet(world.getPlayer(), i, j);
                             currentILoc = i; currentJLoc = j;


                             try {
								out.writeObject(update);
							} catch (IOException e) {
								System.out.println("couldn't send packets");
								e.printStackTrace();
							}

							}
						}
					}
				}
			}


		}




	}


	public static void main(String[] args) {



	}


}
