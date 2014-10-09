package controllers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public interface Controller {

	public abstract void buttonPressed(JButton button, Object[] info);
	public abstract void mousePressed(MouseEvent me, Object[] info);
	public abstract void keyPressed(KeyEvent ke, Object[] info);
	
}
