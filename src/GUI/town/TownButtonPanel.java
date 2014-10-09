package GUI.town;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controllers.TownController;

public class TownButtonPanel extends JPanel{

	private TownController controller;
	private JButton button_leave;
	
	protected TownButtonPanel(Dimension dimensions, TownController townController) {
		this.controller = townController;
		this.setPreferredSize(dimensions);
		
		button_leave = new JButton("Leave");
		button_leave.setPreferredSize(new Dimension(80,50));
		button_leave.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				controller.buttonPressed(button_leave.getText());
			}
		
		});
		this.add(button_leave);
		this.setPreferredSize(new Dimension(400,200));
		
	}

}
