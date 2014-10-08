package GUI;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLayeredPane;

public class MiniMapLayeredPane extends JLayeredPane{

	
	public MiniMapLayeredPane() {
		/*Declare and initialise a new mini map panel and set the layering position underneath */
		MiniMapPanel miniMap = new MiniMapPanel();
		miniMap.setBounds(20,15,230, 165);
		this.add(miniMap,new Integer(0),0);
		
		/*Declare and initialise a new mini map border panel and set the layering position on top */
		MiniMapBorder miniMapBorder = new MiniMapBorder();
		miniMapBorder.setBounds(0,0,400,200);
		this.add(miniMapBorder,new Integer(1),0);
	}
}
