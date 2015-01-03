	package gui.town;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import main.GameWindow;

import controllers.TownController;
import controllers.WorldController;
import world.towns.City;

/**
 * Main panl for the Town GUI
 * @author Ewan Moshi && Craig Aaron
 *
 */
public class TownPanel extends JPanel{

	// controller and top-level view
	private TownController townController;

	// components
	private ButtonPanel buttonPanel;
	
	// master
	private GameWindow window;
	
	public TownPanel(GameWindow window, City city) {
		
		// set up fields and state
		this.setEnabled(false);
		this.window = window;
		
		// set layout, add components
		this.setLayout(new BorderLayout());
		this.buttonPanel = new ButtonPanel(this,city);
		this.add(buttonPanel, BorderLayout.WEST);
		
	}
	
	public void setController(TownController tc){
		if (townController != null) throw new RuntimeException("This TownPanel already has a TownController!");
		this.townController = tc;
		buttonPanel.setController(tc);
		this.setEnabled(true);
	}

	@Override
	protected void paintComponent(Graphics g){
		g.fillRect(0,0,getWidth(),getHeight());
	}
	
	public void endTownView() {
		this.setEnabled(false);
		window.switchToWorldScene();
	}
}
