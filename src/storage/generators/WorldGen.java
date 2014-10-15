package storage.generators;

import game.units.AttackType;
import game.units.Hero;
import game.units.HeroStats;
import player.Player;
import world.World;
import world.icons.Party;
import controllers.WorldController;

public class WorldGen {

	/**
	 * Used to contruct the singleplayer map that will be saved into xml
	 */
	public static void MylesWorldGen() {
		Player p = new Player("John The Baptist",1);
		World w = TemporaryLoader.loadWorld("singleplayer.txt",p);
		HeroStats stats_hero = new HeroStats(60,10,80,0,6,8,AttackType.MELEE);
		Hero hero = new Hero("Gabe Newell","knight",p,stats_hero);

		hero.setMovePts(10);

		Party p1 = new Party(hero, p, Party.newEmptyPartyArray());
		p1.refresh();

		w.setIcon(p1, 15, 15);

		new WorldController(w, p);
	}

	public static void main(String[] gabe) {
		MylesWorldGen();
	}
}
