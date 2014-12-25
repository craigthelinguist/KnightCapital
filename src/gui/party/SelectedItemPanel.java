package gui.party;

import game.items.Item;
import game.units.creatures.Creature;
import game.units.creatures.Hero;
import game.units.stats.Stat;

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
 * @author myles, Ewan Moshi
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

	/**
	 * Update the selected item panel for the currently selected item.
	 */
	private void updateInfoItem(){
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
	}
	
	/**
	 * Update the selected item panel for the currently selected unit.
	 */
	private void updateInfoUnit(){
		//remove the equip button
		this.remove(equipItem);
	
		// set image
		this.imageLabel.setIcon(new ImageIcon(this.unit.getPortrait()));

		// set name
		this.nameLabel.setText("<html><font color='yellow'>"+this.unit.getName()+"</font></html>");

		// if you don't have visibility over this unit, you're done, 
		if (!master.isOwner()) return;
			
		// otherwise display its stats, moves, etc.
		int damageBase = this.unit.getBase(Stat.DAMAGE);
		int healthBase = this.unit.getBase(Stat.HEALTH);
		int armourBase = this.unit.getBase(Stat.ARMOUR);
		int speedBase = this.unit.getBase(Stat.SPEED);
		int damageBuff = this.unit.getBuffed(Stat.DAMAGE);
		int healthBuff = this.unit.getBuffed(Stat.HEALTH);
		int armourBuff = this.unit.getBuffed(Stat.ARMOUR);
		int speedBuff = this.unit.getBuffed(Stat.SPEED);
		int healthCurrent = this.unit.get(Stat.HEALTH);

		// format the info
		StringBuilder html = new StringBuilder("<html>");
		html.append(generateHTMLForHealth(healthCurrent,healthBase,healthBuff));
		html.append(generateHTML("Damage",damageBase,damageBuff));
		html.append(generateHTML("Armour",armourBase,armourBuff));
		html.append(generateHTML("Speed",speedBase,speedBuff));
			
		// hero has extra stats
		if (unit instanceof Hero){
			Hero hero = (Hero)unit;
			int sight = hero.getBase(Stat.SIGHT);
			int buff_sight = hero.getBuffed(Stat.SIGHT);
			int move = hero.getBase(Stat.MOVEMENT);
			int buff_move = hero.getBuffed(Stat.MOVEMENT);
			html.append(generateHTML("Sight",sight,buff_sight));
			html.append(generateHTML("Movement",move,buff_move));
		}
			
		// close html tag
		html.append("</html>");
			
		//set description
		this.descriptionLabel.setText("<html><font color='yellow'> " + this.unit.getName() + " </font></html>");
		this.descriptionLabel.setText(html.toString());	
	}
	
	/**
	 * Update the text output for the selected item panel.
	 */
	private void updateInfo() {
		if (item != null) updateInfoItem();
		else if (unit != null) updateInfoUnit();
	}

	/**
	 * Generates an html string for the unit's health. 
	 * @param current: their current health
	 * @param base: their base health
	 * @param buff: amount their health is buffed by
	 * @return an html-formatted string
	 */
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

	/**
	 * Generates an html string for any of the unit's stats (except health!)
	 * @param name: name of the stat
	 * @param base: base value of the stat
	 * @param buff: buffed value of the stat
	 * @return an html-formatted string
	 */
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
