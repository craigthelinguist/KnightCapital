package game.units.factories;

public class NoSuchUnitException extends Exception {
	
	@Override
	public String getMessage(){
		return "No such unit!";
	}
	
	
}
