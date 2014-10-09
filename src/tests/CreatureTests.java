package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import player.Player;
import game.units.Stat;
import game.units.Unit;

public class CreatureTests {

	Unit unit;
	Player player;

	@Test
	public void testHealthiness(){

		generateUnit();
		unit.setStat(Stat.HEALTH, 100);
		unit.fullHeal();
		int percent = unit.healthiness();
		assert(percent == 100);

	}

	public void generateUnit(){
		Player p1 = new Player("tupac",4);
		unit = new Unit("knight", p1);
	}


}
