package GUI.Equipment;

import game.units.EquipmentBorder;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;



public class EquipmentPanelLayered extends JLayeredPane{

		public EquipmentPanelLayered(EquipmentMain equipMainDialog) {
			this.setPreferredSize(new Dimension(1180,equipMainDialog.getHeight()));

			this.setBorder(BorderFactory.createLineBorder(Color.RED)); // this is just for testing remove later
			/*Declare and initialise a new DialogPanel and set the layering position underneath */
			EquipmentPanelBackground equipmentPanel = new EquipmentPanelBackground();
			equipmentPanel.setBounds(12,12,1156, equipMainDialog.getHeight()-73);
			this.add(equipmentPanel,new Integer(1),0);

			/*Declare and initialise a new DialogBackground and set the layering position on top */
			EquipmentBorder equipBorder = new EquipmentBorder();
			equipBorder.setBounds(0,0,1180,750);
			this.add(equipBorder,new Integer(0),0);

			HeroEquipLayeredPane heroLayer = new HeroEquipLayeredPane();
			heroLayer.setBounds(50,50,800,700);
			this.add(heroLayer,new Integer(2),0);
		}

}
