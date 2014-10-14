package GUI.town;

import game.units.Unit;

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

public class TrainingDialog extends JDialog {

	static final Map<String,Integer> costs = new HashMap<>();
	static {
		costs.put("Knight", 125);
		costs.put("Archer", 75);
	}

	private TownController controller;
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
		choice.setSelectedIndex(0);

		// train button
		JButton trainButton = new JButton("Train");
		trainButton.setPreferredSize(new Dimension(80,25));
		trainButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {

				Party garrison = controller.getGarrison();
				if (!garrison.hasSpace()) return;

				String string = (String)choice.getSelectedItem();
				City city = controller.getCity();
				Player player = city.getOwner();

				if (string.equals("Knight")){
					int cost = costs.get(string);
					if (cost > player.getGold() || !city.getGarrison().hasSpace()) return;
					Unit unit = UnitLoader.load("knight.xml", city.getOwner());
					garrison.addUnit(unit);
					player.decreaseGold(cost);
					goldLabel.setText("" + player.getGold());
				}
				else if (string.equals("Archer")){
					int cost = costs.get(string);
					if (cost > player.getGold() || !city.getGarrison().hasSpace()) return;
					Unit unit = UnitLoader.load("knight.xml", city.getOwner());
					garrison.addUnit(unit);
					player.decreaseGold(cost);
					goldLabel.setText("" + player.getGold());
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
