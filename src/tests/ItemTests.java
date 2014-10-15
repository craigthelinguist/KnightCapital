package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import player.Player;
import game.effects.Buff;
import game.effects.Effect;
import game.items.Item;
import game.items.PassiveItem;
import game.items.Target;
import game.units.AttackType;
import game.units.Creature;
import game.units.Hero;
import game.units.HeroStats;
import game.units.Stat;
import game.units.Unit;
import game.units.UnitStats;
import world.icons.Party;

public class ItemTests {

	private Party party;
	private Hero hero;
	private Unit unit;

	@Test
	public void testApplicationToHero(){

		init();

		Buff b1 = Buff.newTempBuff(Stat.DAMAGE, 10);
		Buff b2 = Buff.newTempBuff(Stat.HEALTH, 10);
		Buff[] buffs = new Buff[]{ b1, b2 };

		PassiveItem item = new PassiveItem("","","",buffs,Target.HERO,"");
		item.applyEffectsTo(party);

		// make sure hero stats updated
		assertTrue(  hero.getBuffed(Stat.DAMAGE) == 10);
		assertTrue( hero.getBuffed(Stat.HEALTH)==10);
		assertTrue(hero.get(Stat.DAMAGE) == 10 + hero.getBase(Stat.DAMAGE));
		assertTrue(hero.get(Stat.HEALTH) == 10 + hero.getBase(Stat.HEALTH));

		// this item's target is hero only - make sure unit stats did not update
		assertTrue( unit.getBuffed(Stat.DAMAGE) == 0);
		assertTrue(unit.getBuffed(Stat.HEALTH) == 0);
		assertTrue( unit.get(Stat.DAMAGE) == unit.getBase(Stat.DAMAGE));
		assertTrue( unit.get(Stat.HEALTH) == unit.getBase(Stat.HEALTH));


	}

	@Test
	public void testApplicationToParty(){

		init();

		Buff b1 = Buff.newTempBuff(Stat.DAMAGE, 10);
		Buff b2 = Buff.newTempBuff(Stat.HEALTH, 10);
		Buff[] buffs = new Buff[]{ b1, b2 };

		PassiveItem item = new PassiveItem("","","",buffs,Target.PARTY,"");
		System.out.println("stop");
		item.applyEffectsTo(party);

		// make sure hero stats updated
		assertTrue(  hero.getBuffed(Stat.DAMAGE) == 10);
		assertTrue( hero.getBuffed(Stat.HEALTH)==10);
		assertTrue(hero.get(Stat.DAMAGE) == 10 + hero.getBase(Stat.DAMAGE));
		assertTrue(hero.get(Stat.HEALTH) == 10 + hero.getBase(Stat.HEALTH));

		assertTrue(unit.getBuffed(Stat.DAMAGE) == 10);
		assertTrue(unit.getBuffed(Stat.HEALTH) == 10);
		assertTrue(unit.get(Stat.DAMAGE) == 10 + unit.getBase(Stat.DAMAGE));
		assertTrue(unit.get(Stat.HEALTH) == 10 + unit.getBase(Stat.HEALTH));


	}

	@Test
	public void testRemovalFromParty(){

		init();

		Buff b1 = Buff.newTempBuff(Stat.DAMAGE, 10);
		Buff b2 = Buff.newTempBuff(Stat.HEALTH, 10);
		Buff[] buffs = new Buff[]{ b1, b2 };

		PassiveItem item = new PassiveItem("","","",buffs,Target.PARTY,"");
		item.applyEffectsTo(party);
		item.removeEffectsFrom(party);

		// this item's target is hero only - make sure hero stats did not update
				assertTrue( hero.getBuffed(Stat.DAMAGE) == 0);
				assertTrue(hero.getBuffed(Stat.HEALTH) == 0);
				assertTrue( hero.get(Stat.DAMAGE) == hero.getBase(Stat.DAMAGE));
				assertTrue( hero.get(Stat.HEALTH) == hero.getBase(Stat.HEALTH));

		// this item's target is hero only - make sure unit stats did not update
				assertTrue( unit.getBuffed(Stat.DAMAGE) == 0);
				assertTrue(unit.getBuffed(Stat.HEALTH) == 0);
				assertTrue( unit.get(Stat.DAMAGE) == unit.getBase(Stat.DAMAGE));
				assertTrue( unit.get(Stat.HEALTH) == unit.getBase(Stat.HEALTH));


	}

	@Test
	public void testRemovalFromHero(){

		init();

		Buff b1 = Buff.newTempBuff(Stat.DAMAGE, 10);
		Buff b2 = Buff.newTempBuff(Stat.HEALTH, 10);
		Buff[] buffs = new Buff[]{ b1, b2 };

		PassiveItem item = new PassiveItem("","","",buffs,Target.HERO,"");
		item.applyEffectsTo(party);
		item.removeEffectsFrom(party);

		// this item's target is hero only - make sure hero stats did not update
				assertTrue( hero.getBuffed(Stat.DAMAGE) == 0);
				assertTrue(hero.getBuffed(Stat.HEALTH) == 0);
				assertTrue( hero.get(Stat.DAMAGE) == hero.getBase(Stat.DAMAGE));
				assertTrue( hero.get(Stat.HEALTH) == hero.getBase(Stat.HEALTH));

		// this item's target is hero only - make sure unit stats did not update
				assertTrue( unit.getBuffed(Stat.DAMAGE) == 0);
				assertTrue(unit.getBuffed(Stat.HEALTH) == 0);
				assertTrue( unit.get(Stat.DAMAGE) == unit.getBase(Stat.DAMAGE));
				assertTrue( unit.get(Stat.HEALTH) == unit.getBase(Stat.HEALTH));


	}

	@Test
	public void testApplicationToUnknownTarget(){

		init();

		Buff b1 = Buff.newTempBuff(Stat.DAMAGE, 10);
		Buff b2 = Buff.newTempBuff(Stat.HEALTH, 10);
		Buff[] buffs = new Buff[]{ b1, b2 };

		PassiveItem item = new PassiveItem("","","",buffs,Target.UNIT,"");

		try{
			item.applyEffectsTo(party);
			fail("shouldn't bea ble to apply in ambiguous context");
		}
		catch(Exception e){}



	}

	private void init(){
		Player player1 = new Player("John Baptist", 1);
		HeroStats herostats = new HeroStats(10,10,10,10,10,10, AttackType.MELEE);
		hero = new Hero("Ovelia","ovelia",player1,herostats);
		UnitStats unitstats = new UnitStats(10,10,10,10,AttackType.MELEE);
		unit = new Unit("Knight","knight",player1,unitstats);
		Creature[][] members = Party.newEmptyPartyArray();
		members[0][0] = hero;
		members[1][0] = unit;
		party = new Party(hero,player1,members);
	}

}
