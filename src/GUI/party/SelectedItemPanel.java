package GUI.party;

import game.items.Item;
import game.units.Creature;
import game.units.Hero;
import game.units.Stat;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

	//button for equip Item
	JButton equipItem;

	// JLabels
	private JLabel imageLabel;
	private JLabel nameLabel;
	private JLabel descriptionLabel;

	//the constraints for this panel
	GridBagConstraints gc;

	/** Construct new empty Item Panel **/
	public SelectedItemPanel(PartyDialog master, Dimension d) {
		super();
		this.setPreferredSize(d);
		this.master = master;

		// Set layout
		this.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;

		equipItem = new JButton("EquipItem");
		equipItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//equip the item onto the unit
			}
        });


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

			//set equip button
			gc.gridx = 0;
			gc.gridy = 3;
			this.add(equipItem,gc);

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
			//remove the equip button
			this.remove(equipItem);

			// set image
			this.imageLabel.setIcon(new ImageIcon(this.unit.getPortrait()));

			// set name
			this.nameLabel.setText("<html><font color='yellow'>"+this.unit.getName()+"</font></html>");


			/*set up the label that displays how many moves the player has left */
			int damage = this.unit.getBase(Stat.DAMAGE);
			int health = this.unit.getBase(Stat.HEALTH);
			int armour = this.unit.getBase(Stat.ARMOUR);
			int speed = this.unit.getBase(Stat.SPEED);

			int buffD = this.unit.getBuffed(Stat.DAMAGE);
			int buffH = this.unit.getBuffed(Stat.HEALTH);
			int buffA = this.unit.getBuffed(Stat.ARMOUR);
			int buff_speed = this.unit.getBuffed(Stat.SPEED);

			int curr_health = this.unit.get(Stat.HEALTH);

			StringBuilder html = new StringBuilder("<html>");
			html.append(generateHTMLForHealth(curr_health,health,buffH));
			html.append(generateHTML("Damage",damage,buffD));
			html.append(generateHTML("Armour",armour,buffA));
			html.append(generateHTML("Speed",speed,buff_speed));
			if (unit instanceof Hero){

				Hero hero = (Hero)unit;
				int sight = hero.getBase(Stat.SIGHT);
				int buff_sight = hero.getBuffed(Stat.SIGHT);
				int move = hero.getBase(Stat.MOVEMENT);
				int buff_move = hero.getBuffed(Stat.MOVEMENT);

				html.append(generateHTML("Sight",sight,buff_sight));
				html.append(generateHTML("Movement",move,buff_move));

			}
			html.append("</html>");

			//set description
			this.descriptionLabel.setText("<html><font color='yellow'> " + this.unit.getName() + " </font></html>");
			this.descriptionLabel.setText(html.toString());
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

	private String generateHTMLForHealth(int current, int base, int buff){
		StringBuilder str = new StringBuilder();
		str.append("<font color='yellow'>Health: </font> <font color='white'>"+current+"/</font>");
		int total = base+buff;
			if (buff > 0){
				str.append("<font color='green'>" + total + "</font>");
			}
			else if (buff < 0){
				str.append("<font color='red'>" + total + "</font>");
			}
			else{
				str.append("<font color='white'>" + total + "</font>");
			}
		str.append("<br/>");
		return str.toString();
	}

	private String generateHTML(String name, int base, int buff){
		StringBuilder str = new StringBuilder();
		str.append("<font color='yellow'>"+name+": </font>");

		if (buff > 0){
			str.append("<font color='green'>" + (base+buff) + "</font>");
		}
		else if (buff < 0){
			str.append("<font color='red'>" + (base+buff) + "</font>");
		}
		else{
			str.append("<font color='white'>" + base + "</font>");
		}
		str.append("<br/>");
		return str.toString();
	}

}
