package storage;

import game.effects.Buff;
import game.items.PassiveItem;
import game.items.Target;
import game.units.Stat;

import java.util.ArrayList;

public class CreateWorld {

	public static void makeGame() {
		/**
		 * S I N G L E P L A Y E R
		 * I
		 * N
		 * G
		 * L
		 * E
		 * P
		 * L
		 * A
		 * Y
		 * E
		 * R
		 */

		// Create Buffs
		ArrayList<Buff[]> buffs = new ArrayList<Buff[]>();

		Buff[] damage5 = new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,5) };
		Buff[] damage10 = new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,10) };
		Buff[] damage15 = new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,15) };
		Buff[] damage25 = new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,25) };
		buffs.add(damage5);
		buffs.add(damage10);
		buffs.add(damage15);
		buffs.add(damage25);

		Buff[] armor5 = new Buff[]{ Buff.newTempBuff(Stat.ARMOUR,5) };
		Buff[] armor10 = new Buff[]{ Buff.newTempBuff(Stat.ARMOUR,10) };
		Buff[] armor15 = new Buff[]{ Buff.newTempBuff(Stat.ARMOUR,15) };
		Buff[] armor25 = new Buff[]{ Buff.newTempBuff(Stat.ARMOUR,25) };
		buffs.add(armor5);
		buffs.add(armor10);
		buffs.add(armor15);
		buffs.add(armor25);

		Buff[] speed5 = new Buff[]{ Buff.newTempBuff(Stat.SPEED,5) };
		Buff[] speed10 = new Buff[]{ Buff.newTempBuff(Stat.SPEED,10) };
		Buff[] speed15 = new Buff[]{ Buff.newTempBuff(Stat.SPEED,15) };
		Buff[] speed25 = new Buff[]{ Buff.newTempBuff(Stat.SPEED,25) };
		buffs.add(speed5);
		buffs.add(speed10);
		buffs.add(speed15);
		buffs.add(speed25);

		Buff[] health5 = new Buff[]{ Buff.newTempBuff(Stat.HEALTH,5) };
		Buff[] health10 = new Buff[]{ Buff.newTempBuff(Stat.HEALTH,10) };
		Buff[] health15 = new Buff[]{ Buff.newTempBuff(Stat.HEALTH,15) };
		Buff[] health25 = new Buff[]{ Buff.newTempBuff(Stat.HEALTH,25) };
		buffs.add(health5);
		buffs.add(health10);
		buffs.add(health15);
		buffs.add(health25);

		Buff[] sight5 = new Buff[]{ Buff.newTempBuff(Stat.SIGHT,5) };
		Buff[] sight10 = new Buff[]{ Buff.newTempBuff(Stat.SIGHT,10) };
		Buff[] sight15 = new Buff[]{ Buff.newTempBuff(Stat.SIGHT,15) };
		Buff[] sight25 = new Buff[]{ Buff.newTempBuff(Stat.SIGHT,25) };
		buffs.add(sight5);
		buffs.add(sight10);
		buffs.add(sight15);
		buffs.add(sight25);

		Buff[] move5 = new Buff[]{ Buff.newTempBuff(Stat.MOVEMENT,5) };
		Buff[] move10 = new Buff[]{ Buff.newTempBuff(Stat.MOVEMENT,10) };
		Buff[] move15 = new Buff[]{ Buff.newTempBuff(Stat.MOVEMENT,15) };
		Buff[] move25 = new Buff[]{ Buff.newTempBuff(Stat.MOVEMENT,25) };
		buffs.add(move5);
		buffs.add(move10);
		buffs.add(move15);
		buffs.add(move25);

		// Create Hero Items

		// AMULETS
		PassiveItem gainsAmulet = new PassiveItem(
				"Amulet of Gains",
				"amulet",
				"An amulet that grants sickening gains to Hero.\n +10 Damage",
				damage10,
				Target.HERO,
				"gainsAmulet.xml"
				);

		PassiveItem swoleAmulet = new PassiveItem(
				"Amulet of Swoleness",
				"amulet",
				"An amulet that grants all units a huge increase in strength to Party.\n +5 Damage",
				damage5,
				Target.PARTY,
				"swoleAmulet.xml"
				);

		PassiveItem speedAmulet = new PassiveItem(
				"Amulet of Sonic",
				"amulet",
				"An amulet that grants your Hero an increase in speed.\n +10 Speed",
				speed25,
				Target.HERO,
				"speedAmulet.xml"
				);

		PassiveItem armourAmulet = new PassiveItem(
				"Amulet of Swoleness",
				"amulet",
				"An amulet that increases protection from enemies.\n +10 Armor",
				armor10,
				Target.HERO,
				"armourAmulet.xml"
				);

		PassiveItem movementAmulet = new PassiveItem(
				"Amulet of Nike",
				"amulet",
				"An amulet that grants all units a huge increase in movement.\n +10 Damage",
				damage10,
				Target.PARTY,
				"movementAmulet.xml"
				);
	}
}
