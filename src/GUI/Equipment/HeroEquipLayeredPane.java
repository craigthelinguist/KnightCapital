package GUI.Equipment;

import game.units.EquipmentBorder;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;


public class HeroEquipLayeredPane extends JLayeredPane{


	public HeroEquipLayeredPane(EquipmentMain equipMainDialog) {
//		this.setPreferredSize(new Dimension(), getHeight()));

		//this.setBorder(BorderFactory.createLineBorder(Color.YELLOW)); // this is just for testing remove later
		//this.setBackground(Color.YELLOW);

		/*Declare and initialise a new DialogPanel and set the layering position underneath */
		EquipmentPanel equipmentPanel = new EquipmentPanel(equipMainDialog);
		equipmentPanel.setBounds(12,12,576, 606);
		this.add(equipmentPanel,new Integer(1),0);

		/*Declare and initialise a new DialogBackground and set the layering position on top */
		EquipmentHeroBorder equipHeroBorder = new EquipmentHeroBorder();
		equipHeroBorder.setBounds(00,0,600,630);
		this.add(equipHeroBorder);

	}
}
