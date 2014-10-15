package networking;

import java.awt.event.MouseEvent;
import java.io.Serializable;
import controllers.WorldController;

/**
 * @author Neal Hartley && Selemon Yitbarek
 * this class is used for passing the mouseevent, world and id
 * to and from server and client
 * */
public class Message implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private MouseEvent event;
	private WorldController world;
	private int ID;
	private int score;


	/**
	 * Constructor to send to server
	 * @param event holds the mouseevent value
	 * @param world holds the state of the world
	 * @param ID holds the player ID
	 * */
	public Message(MouseEvent event, WorldController world, int ID){
		this.event = event;
		this.world = world;
		this.ID = ID;
	}

	public Message(WorldController world){
		this.world = world;
	}

	/**
	 * Constructor to send to client
	 * @param ID holds the player ID
	 * @param game the player score
	 * */
	public Message(int ID, WorldController game){
		this.ID = ID;
		this.world = game;
	}

	public MouseEvent getMouseEvent(){return event;}
	public WorldController getWorld(){return world;}
	public int getID(){return ID;}




}
