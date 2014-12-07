package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import player.Player;
import game.effects.Buff;
import game.effects.Heal;
import game.units.creatures.Unit;
import game.units.stats.AttackType;
import game.units.stats.Stat;
import game.units.stats.UnitStats;

/**
 * A class that will test the functionality of buffs
 * @author Aaron, Neal
 *
 */
public class BuffTests {

	private Unit unit;

	/**
	 * Test that the buffs are equivalent
	 */
	@Test
	public void testEquivalence(){
		Buff b1 = Buff.newPermaBuff(Stat.DAMAGE,10);
		Buff b2 = Buff.newPermaBuff(Stat.DAMAGE, 10);
		assertEquals(b1,b2);
		Buff b3 = Buff.newTempBuff(Stat.DAMAGE, 10);
		assertFalse(b1.equals(b3));
		assertFalse(b2.equals(b3));
		Heal h1 = new Heal(Stat.HEALTH,5);
		assertFalse(b1.equals(h1));
		assertFalse(b1.equals(null));
		Buff b4 = Buff.newPermaBuff(Stat.DAMAGE,6);
		assertFalse(b1.equals(b4));
		Buff b5 = Buff.newPermaBuff(Stat.HEALTH,6);
		assertFalse(b1.equals(b5));
	}

	/**
	 * Test that the buffs are applied correctly to the unit
	 */
	@Test
	public void testApplication(){
		Buff buff = Buff.newTempBuff(Stat.DAMAGE, 10);
		init();
		int totalDamageb4 = unit.get(Stat.DAMAGE);
		int totalDamageBuffb4 = unit.getBuffed(Stat.DAMAGE);
		buff.applyTo(unit);
		int totalDamageNow = unit.get(Stat.DAMAGE);
		int totalDamageBuffnow = unit.getBuffed(Stat.DAMAGE);
		assertFalse(totalDamageBuffnow == totalDamageBuffb4);
		assertFalse(totalDamageb4 == totalDamageNow);
		assertTrue(totalDamageBuffnow == buff.amount);
		assertTrue(totalDamageNow == unit.getBase(Stat.DAMAGE) + buff.amount);
	}

	/**
	 * Remove the buff and test if the buff has been removed from the unit's stat.
	 */
	@Test
	public void testRemoval(){
		Buff buff = Buff.newTempBuff(Stat.DAMAGE, 10);
		init();
		buff.applyTo(unit);
		assertTrue(unit.get(Stat.DAMAGE) == unit.getBase(Stat.DAMAGE) + buff.amount);
		buff.removeFrom(unit);
		assertTrue(unit.getBuffed(Stat.DAMAGE) == 0);
		assertTrue(unit.get(Stat.DAMAGE) == unit.getBase(Stat.DAMAGE) );
	}

	/**
	 * A method to initialize a unit.
	 * Helper method for th tests.
	 */
	private void init(){
		Player player1 = new Player("DJ Pearce", 1);
		UnitStats stats = new UnitStats(10,10,10,10,AttackType.MELEE);
		unit = new Unit("Knight","knight",player1,stats);
	}

}
