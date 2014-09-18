package storage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import tools.GlobalConstants;
import world.AbstractTile;
import world.TileImpl;
import world.World;

/**
 * I'm just using this to load my tiles/other stuff until the real stuff comes along. You can safely
 * ignore this class and it will probably be deleted before the end.
 * @author craigthelinguist
 */
public class TemporaryLoader {

	// use the static methods
	private TemporaryLoader(){}
	
	// the different kinds of tiles
	private static final char GRASS_TILE = 'G';
	private static final char DIRT_TILE = 'D';
	
	/**
	 * Loads, creates, and returns the specified World.
	 * @param filename: name of file containing the World.
	 * @return an instance of the World described
	 */
	public static World loadWorld(String filename){
		
		AbstractTile[][] tiles = null;
		Scanner scan = null;
		try{
			
			scan = new Scanner(new File(GlobalConstants.ASSETS + filename));
				
			// skip over comments
			String regex = "\\s*#";
			Pattern comments = Pattern.compile(regex);
			while (scan.hasNext(comments)) scan.nextLine();
			
			// dimensions of world
			int across, down;
			try{
				across = scan.nextInt();
				down = scan.nextInt();
			}
			catch(NumberFormatException e){
				throw new IOException("Error parsing world dimensions");
			}
			scan.nextLine();
				
			// create tiles
			tiles = new AbstractTile[across][down];
			for (int y = 0; y < down; y++){
				char[] line = scan.nextLine().toCharArray();
				for (int x = 0; x < across; x++){
						
					switch (line[x]){
						case GRASS_TILE:
							tiles[x][y] = TileImpl.newGrassTile();
							break;
						case DIRT_TILE:
							tiles[x][y] = TileImpl.newDirtTile();
							break;
						default:
							throw new IOException("Unknown symbol " + line[x] + " at ("+"x"+","+y+")");
					}
						
				}
			}
			
		}
		catch (IOException e){
			e.printStackTrace();
			System.exit(1);
		}
		finally{
			if (scan != null) scan.close();
		}
		
		// return tiles
		if (tiles == null){
			System.out.println("Error loading " + filename + ": world was null at the end");
			System.exit(1);
		}
		return new World(tiles);
	
	}
	
}
