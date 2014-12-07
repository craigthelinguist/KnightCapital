package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import game.units.creatures.Unit;
import game.units.stats.AttackType;
import game.units.stats.HeroStats;
import game.units.stats.Stat;
import game.units.stats.UnitStats;

public class StatsTests {

	UnitStats unit;
	HeroStats hero;

	@Test
	public void testGettersAndSettersForUnitStats(){
		change(false,Stat.DAMAGE,10,5);
		change(false,Stat.HEALTH,10,5);
		change(false,Stat.ARMOUR,10,5);
		change(false,Stat.SPEED,10,5);

		// units don't have these stats
		try{
			change(false,Stat.SIGHT, 10, 5);
			fail("unit doesn't have sight stat");
		}
		catch(Exception e){}
		try{
			change(false,Stat.MOVEMENT, 10, 5);
			fail ("unit doesn't have movement stat");
		}
		catch(Exception e){}

	}

	@Test
	public void testGettersAndSettersForHeroStats(){
		change(true,Stat.DAMAGE,10,5);
		change(true,Stat.HEALTH,10,5);
		change(true,Stat.ARMOUR,10,5);
		change(true,Stat.SPEED,10,5);
		change(true,Stat.MOVEMENT,10,5);
		change(true,Stat.SIGHT,10,5);
	}

	@Test
	public void testIllegalChangesForUnitStats(){
		init();
		try{
			unit.setBuff(Stat.SIGHT,5);
			fail("unit doesn't have sight");
		}
		catch(Exception e){}
		try{
			unit.setBuff(Stat.MOVEMENT,5);
			fail("unit doesn't have movement");
		}
		catch(Exception e){}
		try{
			unit.getBase(Stat.SIGHT);
			fail("unit doesn't have sight");
		}
		catch(Exception e){}
		try{
			unit.getBase(Stat.MOVEMENT);
			fail("unit doesn't have movement");
		}
		catch(Exception e){}
		try{
			unit.getBuff(Stat.SIGHT);
			fail("unit doesn't have sight");
		}
		catch(Exception e){}
		try{
			unit.getBuff(Stat.MOVEMENT);
			fail("unit doesn't have movement");
		}
		catch(Exception e){}
		try{
			unit.getTotal(Stat.SIGHT);
			fail("unit doesn't have sight");
		}
		catch(Exception e){}
		try{
			unit.getTotal(Stat.MOVEMENT);
			fail("unit doesn't have movement");
		}
		catch(Exception e){}
	}

	@Test
	public void testCurrentStats(){
		init();
		unit.setBase(Stat.HEALTH, 20);
		unit.setCurrent(Stat.HEALTH, 20);
		assertEquals(20,unit.getCurrent(Stat.HEALTH));
	}

	@Test
	public void testIllegalCurrentStatGetters(){
		init();
		try{
			unit.getCurrent(Stat.DAMAGE);
			fail("can't get current damage");
		}
		catch(Exception e){}

		try{
			unit.getCurrent(Stat.ARMOUR);
			fail("can't get current damage");
		}
		catch(Exception e){}

		try{
			unit.getCurrent(Stat.SPEED);
			fail("can't get current damage");
		}
		catch(Exception e){}

		try{
			hero.getCurrent(Stat.SIGHT);
			fail("can't get current damage");
		}
		catch(Exception e){}

		try{
			unit.getCurrent(Stat.MOVEMENT);
			fail("can't get current damage");
		}
		catch(Exception e){}
	}


	@Test
	public void testIllegalCurrentStatSetters(){
		init();
		try{
			unit.setCurrent(Stat.DAMAGE,5);
			fail("can't set current damage");
		}
		catch(Exception e){}

		try{
			unit.setCurrent(Stat.ARMOUR,5);
			fail("can't set current damage");
		}
		catch(Exception e){}

		try{
			unit.setCurrent(Stat.SPEED,5);
			fail("can't set current damage");
		}
		catch(Exception e){}

		try{
			hero.setCurrent(Stat.SIGHT,5);
			fail("can't set current damage");
		}
		catch(Exception e){}

		try{
			unit.setCurrent(Stat.MOVEMENT,5);
			fail("can't set current damage");
		}
		catch(Exception e){}
	}

	public void change(boolean isHero, Stat stat, int baseVal, int buffVal){
		init();
		UnitStats unit = isHero ? this.hero : this.unit;
		unit.setBase(stat, baseVal);
		assertEquals(unit.getBase(stat), baseVal);
		assertEquals(unit.getTotal(stat), baseVal);
		unit.setBuff(stat, buffVal);
		assertEquals(unit.getBase(stat), baseVal);
		assertEquals(unit.getBuff(stat), buffVal);
		assertEquals(unit.getTotal(stat), baseVal+buffVal);
	}

	public void init(){
		unit = new UnitStats(0,0,0,0,AttackType.MELEE);
		hero = new HeroStats(0,0,0,0,0,0,AttackType.MELEE);
	}

}
