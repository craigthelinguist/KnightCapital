package game.units.factories;

public class NoSpaceInCityException extends Exception{

	@Override
	public String getMessage(){
		return "No room in the city!";
	}
	
}
