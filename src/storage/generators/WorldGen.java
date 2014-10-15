package storage.generators;

import java.util.HashSet;
import java.util.Set;

import game.units.AttackType;
import game.units.Hero;
import game.units.HeroStats;
import player.Player;
import world.World;
import world.icons.DecorIcon;
import world.icons.Party;
import world.tiles.CityTile;
import world.tiles.Tile;
import world.towns.City;
import controllers.WorldController;

public class WorldGen {

	/**
	 * Used to contruct the singleplayer map that will be saved into xml
	 */
	public static void MylesWorldGen() {
		Player p = new Player("John The Baptist",1);

		HeroStats stats_hero = new HeroStats(60,10,80,0,6,10,AttackType.MELEE);
		Hero hero = new Hero("Gabe Newell","knight",p,stats_hero);

		Party party1 = new Party(hero, p, Party.newEmptyPartyArray());
		party1.refresh();

		int size = 10000;
		Tile[][] tiles = TemporaryLoader.loadWorld(size, size);





		// add a city
		CityTile[][] cityTiles = new CityTile[City.WIDTH][City.WIDTH];

		for (int i=4, a=0; i <= 6; i++, a++){
			for (int j=4, b=0; j <= 6; j++, b++){
				CityTile ct = new CityTile(i,j);
				tiles[i][j] = ct;
				cityTiles[a][b] = ct;
			}
		}

		City city = new City("Porirua","basic", p, cityTiles);

		// world data
		Player[] players = new Player[]{ p };
		Set<City> cities = new HashSet<>();
		cities.add(city);

		World w = new World(tiles, players, cities);

		w.setIcon(party1, 1, 1);

		new WorldController(w, p);
	}

	public static void main(String[] gabe) {
		MylesWorldGen();
	}
}
