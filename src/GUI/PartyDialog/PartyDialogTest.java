package GUI.PartyDialog;

import game.units.*;
import world.World;
import world.icons.Party;
import world.tiles.PassableTile;
import world.tiles.Tile;
import GUI.MainFrame;
import player.Player;
import storage.TemporaryLoader;

public class PartyDialogTest {
	
	public PartyDialogTest() {
		Tile tile = PassableTile.newDirtTile(0, 0);
		Player p = new Player("LL Cool J",1);
		Hero hero = new Hero("ovelia",p);
		Party party = new Party(hero, p);
		tile.setIcon(party);
		
		MainFrame frame = new MainFrame();
		PartyDialog pd = new PartyDialog(frame, tile);
		
		pd.setVisible(true);
		frame.add(pd);
	}
	
	public static void main(String[] args) {
		new PartyDialogTest();
		
		
	}
	
	
	
}
