package storage;

import player.Player;
import world.World;


/**
 * a class to hold on to the main states of a game
 * used by savexml
 * 
 * */
public class States {
	private Player player;
	private World world;
	
	
	public States(Player player, World world){
		this.player =player;
		this.world =world;
	}
	
	public World getWorld(){
		return world;
	}
	
	public Player getPlayer(){
		return player;
	}
	
}
