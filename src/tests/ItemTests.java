package tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import player.Player;
import game.effects.Buff;
import game.items.Item;
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
	public void testApplication(){

		Buff b1 = Buff.newTempBuff(Stat.DAMAGE, 10);
		Buff b2 = Buff.newTempBuff(Stat.ARMOUR, 10);
		List<Buff> buffs = new ArrayList<>();
		buffs.add(b1); buffs.add(b2);

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
