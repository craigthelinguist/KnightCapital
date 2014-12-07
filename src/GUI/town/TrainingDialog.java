package GUI.town;

import game.units.creatures.Unit;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import player.Player;
import storage.converters.UnitLoader;
import tools.Constants;
import world.icons.Party;
import world.towns.City;
import controllers.TownController;

import game.units.factories.UnitFactory;

/**
 * The TrainingDialog lets you select a unit to train in town.
 * @author Aaron Craig
 */
public class TrainingDialog extends JDialog {

	/**
	 * Mapping of name of unit -> cost of unit
	 */

	// controller this dialog is attached to
	private TownController controller;

	// displays gold the player has
	private JLabel goldLabel;

	protected TrainingDialog(TownController master, JFrame frame){
		super(frame,true);
		JPanel panel = new JPanel();

		controller = master;

		// combo box
		final JComboBox<String> choice = new JComboBox<>();
		choice.setPreferredSize(new Dimension(80,25));
		choice.setMaximumSize(new Dimension(80,25));
		choice.addItem("Knight");
		choice.addItem("Archer");
		choice.addItem("Dark Knight");
		choice.setSelectedIndex(0);

		// train button
		JButton trainButton = new JButton("Train");
		trainButton.setPreferredSize(new Dimension(80,25));
		trainButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {

				String unitName = (String)choice.getSelectedItem();
				City city = controller.getCity();
				Player player = city.getOwner();
				try {
					UnitFactory.TrainUnit(unitName, player, city);
					goldLabel.setText("" + player.getGold());
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(e.getMessage());
					// @TODO: give a proper error dialog
				}
			
			}

		});

		// back button
		JButton back = new JButton("Back");
		back.setPreferredSize(new Dimension(80,25));
		//back.setMaximumSize(new Dimension(80,25));
		back.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				TrainingDialog.this.dispose();
			}

		});

		// gold icon
		ImageIcon icon = new ImageIcon(Constants.GUI_FILEPATH + "goldIcon.png");
		JLabel goldIcon = new JLabel(icon);

		// gold label
		goldLabel = new JLabel("" + master.getCity().getOwner().getGold());

		// layout
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
				.addComponent(back)
				.addComponent(goldIcon)
				.addComponent(goldLabel));

		vertical.addComponent(choice);
		vertical.addComponent(trainButton);
		vertical.addComponent(back);
		vertical.addComponent(goldIcon);
		vertical.addComponent(goldLabel);

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
