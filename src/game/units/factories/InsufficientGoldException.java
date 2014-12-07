package game.units.factories;

public class InsufficientGoldException extends Exception{

	@Override
	public String getMessage(){
		return "Not enough gold!";
	}
	
}
