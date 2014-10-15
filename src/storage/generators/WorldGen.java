package storage.generators;

import game.units.AttackType;
import game.units.Hero;
import game.units.HeroStats;
import player.Player;
import world.World;
import world.icons.DecorIcon;
import world.icons.Party;
import world.tiles.Tile;
import world.towns.City;
import controllers.WorldController;

public class WorldGen {

	/**
	 * Used to contruct the singleplayer map that will be saved into xml
	 */
	public static void MylesWorldGen() {
		Player p1 = new Player("John The Baptist",1);

		HeroStats stats_hero = new HeroStats(60,10,80,0,6,10,AttackType.MELEE);
		Hero hero = new Hero("Gabe Newell","knight",p1,stats_hero);

		Party party1 = new Party(hero, p1, Party.newEmptyPartyArray());
		party1.refresh();

		World w = TemporaryLoader.loadWorld("singleplayer.txt", p1);

		w.setIcon(party1, 1, 1);

		DecorIcon tree = new DecorIcon(DecorIcon.TREE);
		DecorIcon bush = new DecorIcon(DecorIcon.BUSH);
		DecorIcon rock = new DecorIcon(DecorIcon.ROCK);

		for(int x = 0; x < 30; x++) {

			for(int y = 0; y < 30; y++) {



			}

		}



		new WorldController(w, p1);
	}

	public static void main(String[] gabe) {
		MylesWorldGen();
	}
}
