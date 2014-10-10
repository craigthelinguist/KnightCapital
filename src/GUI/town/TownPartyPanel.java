package GUI.town;

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
import GUI.reusable.PartyPanel;

import tools.Constants;
import world.icons.Party;
import world.towns.City;

public class TownPartyPanel extends JPanel {
	
	// constants
	private static final int PORTRAIT_WD = Constants.PORTRAIT_DIMENSIONS.width;
	private static final int PORTRAIT_HT = Constants.PORTRAIT_DIMENSIONS.height;
	private static final int HEALTH_BAR_WD = PORTRAIT_WD;
	private static final int HEALTH_BAR_HT = 8;
	private static final int PARTY_COLS = Party.PARTY_COLS;
	private static final int PARTY_ROWS = Party.PARTY_ROWS;
	private static final int CELL_WD = PORTRAIT_WD;
	private static final int CELL_HT = PORTRAIT_HT + HEALTH_BAR_HT;
	private static final Color HEALTH_COLOR = new Color(50,160,70);
	
	// party
	private Party party;
	
	// top-level component
	private TownExchangePanel master;
	
	protected TownPartyPanel(TownExchangePanel master, Party party, City city){
		this.master = master;
		this.party = party;
		this.setPreferredSize(new Dimension(CELL_WD*PARTY_COLS+1,CELL_HT*PARTY_ROWS+1));
	}
	
	@Override
	protected void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		for (int x = 0; x < PARTY_COLS; x++){
			for (int y = 0; y < PARTY_ROWS; y++){
				int xDraw = x * CELL_WD;
				int yDraw = y * CELL_HT;
				Creature member = party.getMember(x,y);
				
				// draw health bar
				if (member != null){
					double healthiness = party.getMember(x,y).healthiness();
					g.setColor(HEALTH_COLOR);
					g.fillRect(xDraw, yDraw+PORTRAIT_HT, (int)(HEALTH_BAR_WD*healthiness), HEALTH_BAR_HT);
				}
				
				// draw portrait
				boolean weShouldDraw = true;
				if (member == null) weShouldDraw = false;
				if (master.getDraggedPanel() != null && master.getDraggedPanel() == this){
					if (master.getDraggedPoint().equals(new Point(x,y))) weShouldDraw = false; // being dragged
				}
				if (weShouldDraw){
					BufferedImage portrait = member.getPortrait();
					g.drawImage(portrait,xDraw,yDraw,null);
				}

				// health bar outline
				g.setColor(Color.BLACK);
				g.drawRect(xDraw, yDraw+PORTRAIT_HT, HEALTH_BAR_WD, HEALTH_BAR_HT);
				
				// portrait outline
				g.drawRect(xDraw, yDraw, PORTRAIT_WD, PORTRAIT_HT);
				
				
			}
		}	
	}
	
	/**
	 * Return the party index at the given mouse click.
	 * @return: the index of the mouse click, or null if the index wasn't valid.
	 */
	protected Point getFromPoint(Point click, Point panelPos){
		final int CELL_WIDTH = PORTRAIT_WD;
		final int CELL_HEIGHT = PORTRAIT_HT + HEALTH_BAR_HT;
		Point position = this.getLocationOnScreen();
		position.x -= panelPos.x; position.y -= panelPos.y;
		Point withinPanel = new Point(click.x - position.x, click.y - position.y);
		int x = withinPanel.x/CELL_WIDTH;
		int y = withinPanel.y/CELL_HEIGHT;
		if (x >= 0 && x < PARTY_COLS && y >= 0 && y < PARTY_ROWS) return new Point(x,y);
		else return null;
	}

	public Party getParty() {
		return this.party;
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
		
		City c1 = new City("basic", player, null);
		
		TownPartyPanel tpp = new TownPartyPanel(null,party,c1);
		//tpp.party = party;
		panel.add(tpp);
		frame.add(tpp);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
}
