package controllers;

import world.icons.Party;

/**
 * Battle
 * This is created whenever a player right clicks on an enemy who is within their move limit.
 *
 * Battle is turn based, the player with the unit with the highest speed goes first, and then the next fastest
 * regardless of which party it's in.
 *
 * 		Rules
 * 		-Melee units can only attack the front line until it is non-existant.
 * 		-Ranged units can attack anyone.
 * 		-AOE units attack everyone.
 * 		-Battles continue to the death
 * 		-No rush 20 min
 *
 */
public class BattleController{

	private Party party1;
	private Party party2;

	/**
	 * Constructs a new battle controller for a new battle instance.
	 * @param party 1 & 2 : the two party about to battle.
	 * @param
	 */
	public BattleController(Party p1, Party p2) {
		// set partys
		party1 = p1;
		party2 = p2;

		// construct new battle renderer


	}

	private void run() {

		// Main Loop for battle. Will run until one party is defeated
		//while()

	}

}
