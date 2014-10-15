package networking;

import java.io.Serializable;

import player.Player;
/**
 * 
 * @author Neal Hartley
 *serializable class that stores data for sending along an ObjectOutputStream
 *holds all cojent information regarding player movements.
 */
public class Packet implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int iPlacement;
	private int jPlacement;
	private Player p;


	public Packet(Player p, int i, int j) {

		this.p=p;
		this.iPlacement=i;
		this.jPlacement=j;


	}




	public int getI(){return this.iPlacement;}

	public int getJ(){return this.jPlacement;}

	public Player getplayer(){return this.p;}









}
