package storage;

import world.World;

public class TesterClassToBeDeleted {

	
	/**
	 * this is just a tester class to test the load and save
	 * will delete it later once Neal and myself get the
	 * Items class going
	 * selemon
	 * */
	
	public TesterClassToBeDeleted(){
		World world = TemporaryLoader.loadWorld("world_temporary.txt");
		System.out.println("-----------Original---------");
		System.out.println(world.NUM_TILES_ACROSS);
		System.out.println(world.NUM_TILES_DOWN);
		System.out.println(world.WORLD_HT);
		System.out.println(world.WORLD_WD);
		SaveXML save = new SaveXML(world);
		save.write();
		load();
	}
	
	private void load() {
		// TODO Auto-generated method stub
		LoadXML load = new LoadXML();
		World w = load.read();
		System.out.println("-----------Loaded---------");
		System.out.println(w.NUM_TILES_ACROSS);
		System.out.println(w.NUM_TILES_DOWN);
		System.out.println(w.WORLD_HT);
		System.out.println(w.WORLD_WD);
	}

	public static void main(String[] args){
		new TesterClassToBeDeleted();
	}
	
	
}
