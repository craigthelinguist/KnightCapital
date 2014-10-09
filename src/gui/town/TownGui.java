package gui.town;

import java.awt.Dimension;

import javax.swing.JFrame;

public class TownGui extends JFrame {

	private TownPanel panel;
	
	protected TownGui(TownController townController) {
		panel = new TownPanel(townController);
		this.add(panel);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	public static void main(String[] args){
		new TownGui(null);
	}
	
}
