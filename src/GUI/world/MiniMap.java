package GUI.world;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class MiniMap extends JPanel{
	
	public MiniMap() {
		//this.setSize(400,200); 
		this.setPreferredSize(new Dimension(400,200));
		MiniMapLayeredPane miniMapLayeredPane = new MiniMapLayeredPane();
		this.setLayout(new BorderLayout());
		this.add(miniMapLayeredPane,BorderLayout.CENTER);
		this.setOpaque(false);
		this.setVisible(true);
	}
}
