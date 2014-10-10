package GUI.town;

import game.effects.Buff;
import game.items.Item;
import game.items.PassiveItem;
import game.units.Creature;
import game.units.Hero;
import game.units.Stat;
import game.units.Unit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import player.Player;

import tools.Constants;
import world.icons.Party;
import world.towns.City;

public class TownItemPanel extends JPanel{

	private final int ITEM_WD = Constants.INVENTORY_DIMENSIONS.width;
	private final int ITEM_HT = Constants.INVENTORY_DIMENSIONS.height;
	private final int INVENTORY_ROWS = Party.INVENTORY_ROWS;
	private final int INVENTORY_COLS = Party.INVENTORY_COLS;
	
	private TownExchangePanel master;
	private Party party;
	
	protected TownItemPanel(TownExchangePanel master, Party party, City city){
		this.master = master;
		this.setPreferredSize(new Dimension(INVENTORY_ROWS*ITEM_WD+1,INVENTORY_COLS*ITEM_HT+1));
	}

	@Override
	protected void paintComponent(Graphics g){
		
		g.setColor(Color.BLACK);
		
		for (int y = 0; y < INVENTORY_ROWS; y++){
			for (int x = 0; x < INVENTORY_COLS; x++){
				
				// don't draw if these cases are true
				Item item = party.getItem(x, y);
				boolean weShouldDraw = true;
				if (item == null) weShouldDraw = false;
				if (master.getDraggedPanel() != null && master.getDraggedPanel() == this){
					if (master.getDraggedPoint().equals(new Point(x,y))) weShouldDraw = false; // being dragged
				}
				if (weShouldDraw){
					BufferedImage portrait = item.getPortrait();
					g.drawImage(portrait, x*ITEM_WD, y*ITEM_HT, null);
				}
				
				// draw outlines
				g.setColor(Color.BLACK);
				g.drawRect(x*ITEM_WD, y*ITEM_HT, ITEM_WD, ITEM_HT);
				
			}
		}
		
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		Player player = new Player("Biggie Smalls",2);
		Hero hero = new Hero("ovelia",player);
		Creature[][] members = Party.newEmptyParty();
		
		Unit u1 = new Unit("knight",player);
		u1.setStat(Stat.HEALTH, 100);
		u1.revive(20);
		
		Unit u2 = new Unit("knight",player);
		u2.setStat(Stat.HEALTH, 100);
		u2.revive(45);
		
		members[1][1] = u1;
		members[0][0] = u2;
		members[2][1] = hero;
		
		Party party = new Party(hero, player, members);
		
		Buff[] buffsWeapon = new Buff[]{ new Buff(Stat.DAMAGE,5,true), new Buff(Stat.ARMOUR, 10, true) };
		PassiveItem weapon = new PassiveItem(buffsWeapon, "weapon", "Weapon","A powerful weapon crafted by the mighty Mizza +5 Damage");

		Buff[] buffsArrows= new Buff[]{ new Buff(Stat.DAMAGE,1,true) };
		PassiveItem arrows = new PassiveItem(buffsArrows, "poisonarrow", "Poison Arrows","Poisonous arrows whose feathers were made from the hairs of Mizza. All archers in party gain +1 damage");
		
		party.addItem(weapon);
		party.addItem(arrows);
		
		City c1 = new City("basic", player, null);
		
		TownItemPanel tip = new TownItemPanel(null,party,c1);
		//tpp.party = party;
		panel.add(tip);
		frame.add(tip);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
}
