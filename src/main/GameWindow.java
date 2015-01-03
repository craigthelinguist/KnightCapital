package main;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import game.effects.Buff;
import game.items.PassiveItem;
import game.items.Target;
import game.units.creatures.Creature;
import game.units.creatures.Hero;
import game.units.creatures.Unit;
import game.units.stats.AttackType;
import game.units.stats.HeroStats;
import game.units.stats.Stat;
import game.units.stats.UnitStats;
import gui.main.MainMenu;
import gui.town.TownPanel;
import gui.world.WorldPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;

import player.Player;
import storage.generators.TemporaryLoader;
import world.World;
import world.icons.ItemIcon;
import world.icons.Party;
import world.towns.City;
import controllers.TownController;
import controllers.WorldController;

public class GameWindow extends JFrame{
	
	private enum Mode{
		MAIN_MENU, IN_GAME;
	}
	
	private Mode mode;
	
	// current world controller
	private WorldController currentWorld;
	
	public GameWindow(){
		this.setExtendedState(this.MAXIMIZED_BOTH);
		this.setUndecorated(true); //true means borderless window
	
		newMainMenuScene();
		
		
		// finish up
		this.setResizable(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Change the current scene to the specified one.
	 * @param newPanel: the scene to display.
	 */
	private void changePanel(JPanel newPanel){
		getContentPane().removeAll();
		getContentPane().add(newPanel, BorderLayout.CENTER);
		getContentPane().doLayout();
		revalidate();
		update(getGraphics());
	}
	
	/**
	 * Create a new scene for the Main Menu and display it on this GameWindow.
	 */
	public void newMainMenuScene(){
		MainMenu mainMenu = new MainMenu(this);
		changePanel(mainMenu);
		this.mode = Mode.MAIN_MENU;
	}
	
	/**
	 * Create a new scene for the specified World and display it on this GameWindow.
	 * @param world: world to display.
	 */
	public void newWorldScene(World world) {
		if (this.currentWorld != null) new IllegalStateException("Creating new world scene but you already have one!");
		WorldPanel worldPanel = new WorldPanel(this);
		WorldController wc = new WorldController(world,world.getPlayers()[0],worldPanel);
		worldPanel.setController(wc);
		changePanel(worldPanel);
		this.mode = Mode.IN_GAME;
		this.currentWorld = wc;
		worldPanel.setEnabled(true);
	}
	
	public void switchToTownScene(City city){
		if (this.currentWorld == null) new IllegalStateException("Switching world->town but there's no world controller!");
		WorldPanel worldPanel = currentWorld.getGui();
		worldPanel.setEnabled(false);
		TownPanel townPanel = new TownPanel(this, city);
		changePanel(townPanel);
		TownController townController = new TownController(city, townPanel);
		townPanel.setController(townController);
		townPanel.setEnabled(true);
	}
	
	public void switchToWorldScene(){
		if (this.currentWorld == null) new IllegalStateException("Switching world->town but there's no world controller!");
		WorldPanel worldPanel = currentWorld.getGui();
		changePanel(currentWorld.getGui());
		worldPanel.setEnabled(true);
	}
	
	public static void main(String[] args){
		new GameWindow();
	}
	
	/**
	 * Test method. Create a custom world and return a WorldController for it.
	 * @param worldPanel: the view to attach controller to.
	 * @return WorldController
	 */
	private WorldController makeController(WorldPanel worldPanel){
		/*Loading items*/
		Buff[] buffsAmulet = new Buff[]{ Buff.newTempBuff(Stat.DAMAGE,5) };
		PassiveItem amulet = new PassiveItem("amulet", "amulet",
				"An amulet that grants sickening gains.\n +5 Damage",
				buffsAmulet,Target.HERO);

		Buff[] buffsWeapon = new Buff[]{ Buff.newPermaBuff(Stat.DAMAGE,5), Buff.newTempBuff(Stat.ARMOUR, 10) };
		PassiveItem weapon = new PassiveItem("weapon", "weapon",
				"A powerful weapon crafted by the mighty Mizza +5 Damage",
				buffsWeapon,Target.HERO);

		Buff[] buffsArrows= new Buff[]{ Buff.newPermaBuff(Stat.DAMAGE,1) };
		PassiveItem arrows = new PassiveItem("poisonarrow", "poisonarrow",
				"Poisonous arrows whose feathers were made from the hairs of Mizza. All archers in party gain +1 damage",
				buffsArrows, Target.PARTY);



		ItemIcon itemIcon = new ItemIcon(amulet);

		ItemIcon itemIcon2 = new ItemIcon(weapon);

		ItemIcon itemIcon3 = new ItemIcon(arrows);


		/*Loading the playey*/
		Player p = new Player("Neuromancers",1);
		World w = TemporaryLoader.loadWorld("world_temporary.txt",p);
		HeroStats stats_hero = new HeroStats(60,10,80,0,6,10,AttackType.MELEE);
		Hero hero = new Hero("Molly Millions","ovelia",p,stats_hero);

		/*
		members[0][0] = hero;
		Party party = new Party(hero, p, members);
		party.refresh();*/

		/*load a party*/
		Unit u3 = new Unit("Knight","knight",p,new UnitStats(100,25,40,1,AttackType.MELEE));
		Unit u4 = new Unit("Archer","archer",p,new UnitStats(60,15,70,0,AttackType.RANGED));
		Unit u5 = new Unit("Archer","archer",p,new UnitStats(60,15,70,0,AttackType.RANGED));
		Unit u6 = new Unit("Knight","knight",p,new UnitStats(100,25,40,1,AttackType.MELEE));
		Creature[][] members2 = Party.newEmptyPartyArray();
		members2[0][0] = u3;
		members2[1][0] = u6;
		members2[1][2] = hero;
		members2[0][1] = u4;
		members2[1][1] = u5;
		Party party = new Party(hero,p,members2);
		party.refresh();
		
		Hero h2 = new Hero("Big Gaben","knight",p,new HeroStats(140,35,55,5,8,6,AttackType.MELEE));
		Creature[][] members = Party.newEmptyPartyArray();
		members[0][0] = h2;
		Party party2 = new Party(h2,p,members);
		party2.refresh();
		
		

		party.addItem(arrows);
		w.getTile(0,0).setIcon(party);
		w.getTile(0,1).setIcon(party2);
		w.getTile(1,1).setIcon(itemIcon); //place a floor item on this tile
		w.getTile(1,2).setIcon(itemIcon2);
		w.getTile(1,3).setIcon(itemIcon2);
		w.getTile(1,4).setIcon(itemIcon);
		w.getTile(1,6).setIcon(itemIcon2);
		w.getTile(8,8).setIcon(itemIcon3);

		return new WorldController(w,p,worldPanel);
	}

}
