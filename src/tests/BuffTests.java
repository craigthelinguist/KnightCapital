package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import game.effects.Buff;
import game.effects.Heal;
import game.units.AttackType;
import game.units.Stat;
import game.units.UnitStats;

public class BuffTests {

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
	}


}
