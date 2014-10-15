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
		//World w = TemporaryLoader.loadWorld("2player.txt",p1);

		HeroStats stats_hero = new HeroStats(60,10,80,0,6,10,AttackType.MELEE);
		Hero hero = new Hero("Gabe Newell","knight",p1,stats_hero);

		Party party1 = new Party(hero, p1, Party.newEmptyPartyArray());
		party1.refresh();

		Player p2 = new Player("King Potatoes",2);
		HeroStats stats_hero2 = new HeroStats(60,10,80,0,6,10,AttackType.MELEE);
		Hero hero2 = new Hero("RMS","knight",p2,stats_hero2);
		Party party2 = new Party(hero2, p2, Party.newEmptyPartyArray());
		party2.refresh();

		Tile[][] t = TemporaryLoader.loadWorld(10, 10);

		//Tile[] c1Tiles = new Tile[]{};

		City c1 = new City("Rongotai", "basic", p1, null);
		City c2 = new City("Porirua","basic", p2, null);

		World w = new World(TemporaryLoader.loadWorld(10, 10), new Player[]{p1, p2}, null);
		w.setIcon(party1, 1, 1);
		w.setIcon(party2, 5, 1);

		DecorIcon tree = new DecorIcon(DecorIcon.TREE);

		w.setIcon(tree, 0, 0);

		new WorldController(w, p1);
	}

	public static void main(String[] gabe) {
		MylesWorldGen();
	}
}
