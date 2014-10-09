package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

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
public class BattleController implements Controller{

	@Override
	public void buttonPressed(JButton button, Object[] info) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent me, Object[] info) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent ke, Object[] info) {
		// TODO Auto-generated method stub
		
	}

}
