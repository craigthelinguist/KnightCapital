package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public abstract class Controller {

	public abstract void buttonPressed(JButton button, Object[] info);
	public abstract void mousePressed(MouseEvent me, Object[] info);
	public abstract void keyPressed(KeyEvent ke, Object[] info);
	
	public void buttonPressed(JButton button){
		buttonPressed(button,null);
	}
	
	public void keyPressed(KeyEvent ke){
		keyPressed(ke,null);
	}
	
	public void mousePressed(MouseEvent me) {
		mousePressed(me,null);
	}
	
}
