package GUI.Equipment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import game.effects.Buff;
import game.items.PassiveItem;
import game.items.Target;
import game.units.creatures.Hero;
import game.units.stats.AttackType;
import game.units.stats.HeroStats;
import game.units.stats.Stat;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;

import player.Player;
import storage.generators.TemporaryLoader;
import tools.Constants;
import tools.ImageLoader;
import world.World;
import GUI.world.CustomButton;
import GUI.world.GameDialog;
import GUI.world.MainFrame;

/**
 * The main panel for the equipment panel
 * @author Ewan Moshi
 *
 */
public class EquipmentMain extends JDialog implements ActionListener{

	CustomButton button_leave;


	public EquipmentMain(MainFrame mainFrame, Hero hero) {
		//this.setPreferredSize(new Dimension(800,800));
		this.setSize(1280,800);
		this.setLayout(new FlowLayout());

		/*Load the three images for exiting button*/
		/*Declare and initialize the images for the buttons */
		BufferedImage closeTownDefault = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "closeButton.png");
		BufferedImage closeTownPressed = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "closeButtonClicked.png");
		BufferedImage closeTownHover = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "closeButtonHover.png");
		button_leave = new CustomButton(closeTownDefault, closeTownPressed, closeTownHover);
		button_leave.addActionListener(this);

		JPanel exitPanel = new JPanel();
		exitPanel.setLayout(new BorderLayout());
		exitPanel.setPreferredSize(new Dimension(70,this.getHeight()));
		exitPanel.add(button_leave,BorderLayout.NORTH);
		exitPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		exitPanel.setOpaque(false);

		EquipmentPanelLayered equipmentPanelLayered = new EquipmentPanelLayered(this);



		/*add the panels onto this dialog*/
		this.add(exitPanel);
		this.add(equipmentPanelLayered);

		this.setResizable(true);
		this.setVisible(true);
	}





	/*for testing the equipment panel*/
	public static void main(String[] args) {

		/*Load some items to test with*/
		/*Loading items*/
		Buff[] buffsAmulet = new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,5) };
		PassiveItem amulet = new PassiveItem("amulet", "amulet",
				"An amulet that grants sickening gains.\n +5 Damage",
				buffsAmulet,Target.HERO);

		Buff[] buffsWeapon = new Buff[]{ Buff.newPermaBuff(Stat.DAMAGE,5), Buff.newTempBuff(Stat.ARMOUR, 10) };
		PassiveItem weapon = new PassiveItem("weapon", "weapon",
				"A powerful weapon crafted by the mighty Mizza +5 Damage",
				buffsWeapon,Target.HERO);

		Buff[] buffsArrows= new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,1) };
		PassiveItem arrows = new PassiveItem("poisonarrow", "poisonarrow",
				"Poisonous arrows whose feathers were made from the hairs of Mizza. All archers in party gain +1 damage",
				buffsArrows, Target.PARTY);

		/*Loading the playey*/
		Player p = new Player("John The Baptist",4);
		World w = TemporaryLoader.loadWorld("world_temporary.txt",p);
		HeroStats stats_hero = new HeroStats(60,10,80,0,6,8,AttackType.MELEE);
		Hero hero = new Hero("ovelia","ovelia",p,stats_hero);


		try {
			EquipmentMain eq = new EquipmentMain(new MainFrame(), hero);
			eq.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}















	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
