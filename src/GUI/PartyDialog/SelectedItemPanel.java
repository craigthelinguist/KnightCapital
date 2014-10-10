package GUI.PartyDialog;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.Constants;
import world.icons.ItemIcon;
import world.icons.Party;
import world.icons.WorldIcon;

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


	private WorldIcon icon;
	private boolean isOwner;

	// JLabels
	private JLabel imageLabel;
	private JLabel nameLabel;
	private JLabel descriptionLabel;

	/** Construct new empty Item Panel **/
	public SelectedItemPanel(Dimension d, boolean isOwner) {
		super();
		this.setPreferredSize(d);
		this.isOwner = isOwner;

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
		nameLabel.setFont(Constants.HeaderFont);
		gc.gridx = 0;
		gc.gridy = 1;
		this.add(nameLabel, gc);

		// create label for description
		descriptionLabel = new JLabel();
		descriptionLabel.setFont(Constants.SubheaderFont);
		gc.gridx = 0;
		gc.gridy = 2;
		this.add(descriptionLabel, gc);

		this.setVisible(true);
	}

	/**
	 * Set selected object
	 */
	public void setSelected(WorldIcon icon) {
		this.icon = icon;
		updateInfo();
	}


	/**
	 * Draw the current shit to panel
	 */
	public void drawPanel() {

	}

	private void updateInfo() {
		// set image
		this.imageLabel.setIcon(new ImageIcon(this.icon.getPortrait()));

		// set name
		this.nameLabel.setText(this.icon.getAnimationName());

		// set description
		this.descriptionLabel.setText(this.icon.getAnimationName());

		if(this.icon instanceof ItemIcon) {
			// nothing? Would we need any other information from an item?
		}

		else if(this.icon instanceof Party) {
			//if player is owner of party
			if(this.isOwner) {
				// display stats
				System.out.println("Accessor is owner of party");
			}

			//else nothing
			else {
				System.out.println("Accessor is not owner of party");
			}
		}


	}

}
