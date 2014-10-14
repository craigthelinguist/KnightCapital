package GUI.town;

import game.units.Unit;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import storage.converters.UnitLoader;
import world.icons.Party;
import world.towns.City;
import controllers.TownController;

public class TrainingDialog extends JDialog {

	private TownController controller;

	protected TrainingDialog(TownController master, JFrame frame){
		super(frame,true);
		JPanel panel = new JPanel();

		controller = master;

		final JComboBox<String> choice = new JComboBox<>();
		choice.setPreferredSize(new Dimension(80,25));
		choice.setMaximumSize(new Dimension(80,25));
		choice.addItem("Knight");
		choice.addItem("Archer");
		choice.setSelectedIndex(0);

		JButton trainButton = new JButton("Train");
		trainButton.setPreferredSize(new Dimension(80,25));
		trainButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {

				Party garrison = controller.getGarrison();
				if (!garrison.hasSpace()) return;

				String string = (String)choice.getSelectedItem();
				City city = controller.getCity();

				if (string.equals("Knight")){
					Unit unit = UnitLoader.load("knight.xml", city.getOwner());
					garrison.addUnit(unit);
				}
				else if (string.equals("Archer")){
					Unit unit = UnitLoader.load("archer.xml", city.getOwner());
					garrison.addUnit(unit);
				}

			}

		});

		JButton back = new JButton("Back");
		back.setPreferredSize(new Dimension(80,25));
		//back.setMaximumSize(new Dimension(80,25));
		back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				TrainingDialog.this.dispose();
			}

		});

		GroupLayout layout = new GroupLayout(panel);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);

		horizontal.addGroup(layout.createParallelGroup()
				.addComponent(choice)
				.addComponent(trainButton)
				.addComponent(back));

		vertical.addComponent(choice);
		vertical.addComponent(trainButton);
		vertical.addComponent(back);

		this.add(panel);
		this.pack();
		this.validate();
		this.setPreferredSize(new Dimension(600,400));
		this.setLocationRelativeTo(frame);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);

	}

}
