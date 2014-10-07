package world.towns;

/**
 * Buildings enable certain types of units to be trained from the city.
 * What units those are should be specified in data files that are loaded
 * in when you start up the game.
 * @author craigthelinguist
 *
 */
public class Building {

	// what kind of building is this
	final String name;
	
	public Building(String type){
		name = type;
	}
	
	public String getType(){
		return name;
	}
	
}
