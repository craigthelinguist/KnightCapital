package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import player.Player;
import game.units.AttackType;
import game.units.Stat;
import game.units.Unit;
import game.units.UnitStats;
/**
 *
 * @author Neal Hartley
 *
 */
public class CreatureTests {

	Unit unit;
	Player player;

	@Test
	public void testDamage(){
		generateUnit();
		int totalHP = unit.get(Stat.HEALTH);
		int dmg = 20;
		unit.damage(dmg);
		assert(unit.getHealth() == totalHP-dmg);
	}

	@Test
	public void testOverkill(){
		generateUnit();
		int totalHP = unit.get(Stat.HEALTH);
		int dmg = totalHP*2;
		unit.damage(dmg);
		assert(unit.getHealth() == 0);
	}

	@Test
	public void testHeal(){
		generateUnit();
		int totalHP = unit.get(Stat.HEALTH);
		int heal = 40;
		int dmg = 20;
		unit.damage(dmg);
		assert(unit.getHealth() == totalHP - dmg);
		unit.heal(heal);
		assert(unit.getHealth() == totalHP);
	}

	@Test
	public void testHealWhenDead(){
		generateUnit();
		int totalHP = unit.get(Stat.HEALTH);
		unit.damage(totalHP);
		assert(unit.getHealth() == 0);
		unit.heal(totalHP);
		assert(unit.getHealth() == 0);
		unit.fullHeal();
		assert(unit.getHealth() == 0);
	}

	@Test
	public void testFullHeal(){
		generateUnit();
		int totalHP = unit.get(Stat.HEALTH);
		unit.damage(totalHP);
		
	}

	@Test
	public void testHealthiness100(){
		generateUnit();
		unit.fullHeal();
		int percent = (int) unit.healthiness();
		assert(percent == 100);
	}

	@Test
	public void testHealthiness50(){
		generateUnit();
		int half = unit.get(Stat.HEALTH)/2;
		unit.setCurrent(Stat.HEALTH, half);
		int percent = (int) unit.healthiness();
		assert(percent == 50);
	}

	@Test
	public void testHealthiness0(){
		generateUnit();
		unit.damage(100);
		int percent = (int) unit.healthiness();
		assert(percent == 0);
	}

	public void generateUnit(){
		player = new Player("tupac",1);
		UnitStats stats = new UnitStats(100,25,15,10,AttackType.MELEE);
		unit = new Unit("Knight","knight",player,stats);
	}


}
