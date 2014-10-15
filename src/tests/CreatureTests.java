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
	public void testDamageOnUnarmoured(){
		generateUnarmouredUnit();
		int totalHP = unit.get(Stat.HEALTH);
		int dmg = 20;
		unit.damage(dmg);
		assertTrue(unit.getHealth() == totalHP-dmg);
	}

	@Test
	public void testDamageOnArmoured(){
		generateArmouredUnit();
		int totalHP = unit.get(Stat.HEALTH);
		int dmg = 20;
		unit.damage(dmg);
		assertTrue(unit.getHealth() == totalHP-(dmg-unit.get(Stat.ARMOUR)));
	}

	@Test
	public void testOverkill(){
		generateUnarmouredUnit();
		int totalHP = unit.get(Stat.HEALTH);
		int dmg = totalHP*2;
		unit.damage(dmg);
		assertTrue("should be 0 but was " + unit.getHealth(),unit.getHealth() == 0);
	}

	@Test
	public void testHeal(){
		generateUnarmouredUnit();
		int totalHP = unit.get(Stat.HEALTH);
		int heal = 100;
		int dmg = 20 - unit.get(Stat.ARMOUR);
		unit.damage(dmg);
		assertTrue(unit.getHealth() == totalHP - dmg);
		unit.heal(heal);
		assertTrue("should have " + totalHP + " health but has " +unit.getHealth(),unit.getHealth() == totalHP);
	}

	@Test
	public void testHealWhenDead(){
		generateUnarmouredUnit();
		int totalHP = unit.get(Stat.HEALTH);
		int damage = totalHP + unit.get(Stat.ARMOUR);
		unit.damage(damage);
		assertTrue(unit.getHealth() == 0);
		unit.heal(damage);
		assertTrue(unit.getHealth() == 0);
		unit.fullHeal();
		assertTrue(unit.getHealth() == 0);
		unit.revive(30);
		assertTrue(unit.getHealth() == 30);
	}

	@Test
	public void testFullHeal(){
		generateUnarmouredUnit();
		int totalHP = unit.get(Stat.HEALTH);
		unit.damage(totalHP);

	}

	@Test
	public void testHealthiness100(){
		generateUnarmouredUnit();
		unit.fullHeal();
		int percent = (int)(unit.healthiness()*100);
		assertTrue(percent == 100);
	}

	@Test
	public void testHealthiness80(){
		generateUnarmouredUnit();
		int dmg = (int)(unit.getHealth()*0.2);
		unit.damage(dmg);
		int hp = unit.getHealth();
		int percent = (int)(unit.healthiness()*100);
		assertTrue(percent == 80);
	}

	@Test
	public void testHealthiness50(){
		generateUnarmouredUnit();
		int half = unit.get(Stat.HEALTH)/2;
		unit.setCurrent(Stat.HEALTH, half);
		int percent = (int)(unit.healthiness()*100);
		assertTrue("should be 50, was " + percent,percent == 50);
	}

	@Test
	public void testHealthiness0(){
		generateUnarmouredUnit();
		unit.damage(100);
		int percent = (int) unit.healthiness();
		assertTrue(percent == 0);
	}

	@Test
	public void testDeadness(){
		generateUnarmouredUnit();
		unit.damage(unit.getHealth());
		assertTrue(unit.isDead());
	}

	@Test
	public void testNotDeadness(){
		generateUnarmouredUnit();
		unit.damage(1);
		assertFalse(unit.isDead());
	}

	public void generateUnarmouredUnit(){
		player = new Player("tupac",1);
		UnitStats stats = new UnitStats(100,25,15,0,AttackType.MELEE);
		unit = new Unit("Knight","knight",player,stats);
	}

	public void generateArmouredUnit(){
		player = new Player("tupac",1);
		UnitStats stats = new UnitStats(100,25,15,10,AttackType.MELEE);
		unit = new Unit("Knight","knight",player,stats);
	}


}
