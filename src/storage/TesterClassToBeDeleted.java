package storage;

import player.Player;
import world.World;
import world.tiles.Tile;

public class TesterClassToBeDeleted {


	/**
	 * this is just a tester class to test the load and save
	 * will delete it later once Neal and myself get the
	 * Items class going
	 * selemon
	 * */

	public TesterClassToBeDeleted(){
		//		World world = TemporaryLoader.loadWorld("world_temporary.txt");
		//		System.out.println("-----------Original---------");
		//		System.out.println(world.NUM_TILES_ACROSS);
		//		System.out.println(world.NUM_TILES_DOWN);
		//		System.out.println(world.WORLD_HT);
		//		System.out.println(world.WORLD_WD);
		//		SaveXML save = new SaveXML(world);
		//		save.write();
		for(int i =0;i<4;i++){
			load();
		}
		
	}

	private void load() {
		// TODO Auto-generated method stub
		LoadXML load = new LoadXML();
		States w = load.read();
		World wo = w.getWorld();
		Player p = w.getPlayer();
		Tile[][] t = wo.getTiles();
		System.out.println("-----------Loaded---------");
		for(int i = 0; i<t.length; i++){
			for(int j =0;j<t[i].length;j++){
				System.out.println(i+":"+j);
				System.out.println(t[i][j].occupied());
			}
		}
	}

	public static void main(String[] args){
		new TesterClassToBeDeleted();
	}


}
