package GUI.party;

import game.items.Item;
import game.units.Creature;
import game.units.Stat;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.Constants;
import world.icons.ItemIcon;
import world.icons.Party;

/**
 * Responsible for displaying the selected item or unit that user has clicked on.
 *
 * Displays the following information:
 * 		If Unit:
 * 			-Name
 * 			-Description
 * 			The following with a breakdown i.e. HP 100/100 , Damage/ Armor 100 + BuffNum
 * 			-Health
 * 			-Damage
 * 			-Armor
 * 		If Item
 * 			-Name
 * 			-Lore Description
 * 			-Breakdown of what it will do if applied
 *
 * By default when you open your hero is displayed.
 * @author myles
 *
 */
public class SelectedItemPanel extends JPanel{

	private PartyDialog master;

	// This panel can display both units and items.
	private Creature unit;
	private Item item;

	// JLabels
	private JLabel imageLabel;
	private JLabel nameLabel;
	private JLabel descriptionLabel;

	/** Construct new empty Item Panel **/
	public SelectedItemPanel(PartyDialog master, Dimension d) {
		super();
		this.setPreferredSize(d);
		this.master = master;

		// Set layout
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;

		// create and set label for image
		imageLabel = new JLabel();
		gc.gridx = 0;
		gc.gridy = 0;
		this.add(imageLabel);

		// create label for name
		nameLabel = new JLabel();
		nameLabel.setHorizontalAlignment(JLabel.LEFT);
		nameLabel.setFont(Constants.HeaderFont);
		gc.gridx = 0;
		gc.gridy = 1;
		this.add(nameLabel, gc);

		// create label for description
		descriptionLabel = new JLabel();
		descriptionLabel.setFont(Constants.SubheaderFont);
		descriptionLabel.setHorizontalAlignment(JLabel.LEFT);
		gc.gridx = 0;
		gc.gridy = 2;
		this.add(descriptionLabel, gc);

		this.setVisible(true);
	}

	/**
	 * Set selected item
	 */
	public void setSelectedItem(Item item) {
		this.unit = null;
		this.item = item;
		updateInfo();
	}

	/**
	 * Set selected creature
	 */
	public void setSelectedUnit(Creature unit) {
		this.unit = unit;
		this.item = null;
		updateInfo();
	}

	/**
	 * Draw the current shit to panel
	 */
	public void drawPanel() {

	}

	private void updateInfo() {
		// if selected is an ITEM
		if(item != null) {
			// set image
			this.imageLabel.setIcon(new ImageIcon(this.item.getPortrait()));

			// set name
			this.nameLabel.setText(this.item.getAnimationName());

			// set description
			this.descriptionLabel.setText(this.item.getAnimationName());

			if(master.isOwner()) {
				// display stats
				System.out.println("[SelectedItemPanel] Accessor is owner of item");
			}

			//else nothing
			else {
				System.out.println("[SelectedItemPanel] Accessor is not owner of item");
			}
		}


		// If selected is a UNIT
		if(unit != null) {
			// set image
			this.imageLabel.setIcon(new ImageIcon(this.unit.getPortrait()));

			// set name
			this.nameLabel.setText(this.unit.getAnimationName());


			/*set up the label that displays how many moves the player has left */
			int damage = this.unit.getBase(Stat.DAMAGE);
			int health = this.unit.getBase(Stat.HEALTH);
			int armour = this.unit.getBase(Stat.ARMOUR);

			int buffD = this.unit.getBuffed(Stat.DAMAGE);
			int buffH = this.unit.getBuffed(Stat.HEALTH);
			int buffA = this.unit.getBuffed(Stat.ARMOUR);

			this.descriptionLabel = new JLabel("<html>Health: "+health+"<font color='green'> +"+buffH+"</font> <br>Damage: "+damage+"<font color='green'> +"+buffD+"</font> <br>Armour: "+armour+"<font color='green'> +"+buffA+"</font><br>Moves Left:</html>");


			// set description
			//this.descriptionLabel.setText(this.unit.getAnimationName());
			//this.descriptionLabel.setText("<html>Health: "+health+"<font color='green'> +"+buffH+"</font> <br>Damage: "+damage+"<font color='green'> +"+buffD+"</font> <br>Armour: "+armour+"<font color='green'> +"+buffA+"</font><br>Moves Left: "+moves+"</html>");
			if(master.isOwner()) {
				// display stats
				System.out.println("[SelectedItemPanel] Accessor is owner of party");
			}

			//else nothing
			else {
				System.out.println("[SelectedItemPanel] Accessor is not owner of party");
			}
		}
	}

}
