package gui.party;

import game.items.Item;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.Constants;
import world.icons.Party;

/**
 * Responsible for displaying the items currently equipped to the hero of selected party
 * @author myles, Ewan Moshi
 *
 */
public class HeroItemsPanel extends JPanel{

	private final int ITEM_WD = Constants.INVENTORY_DIMENSIONS.width;
	private final int ITEM_HT = Constants.INVENTORY_DIMENSIONS.height;
	private final int INVENTORY_ROWS = Party.INVENTORY_ROWS;
	private final int INVENTORY_COLS = Party.INVENTORY_COLS;

	// the parent of this panel
	private PartyDialog master;
	private Party party;

	public HeroItemsPanel(PartyDialog master, Party p, Dimension d) {
		super();
		this.setPreferredSize(d);
		this.master = master;
	}

	@Override
	protected void paintComponent(Graphics g){

		// only draw items in the party if you're allowed to see them
		if (!master.isOwner()) return;
		
		g.setColor(Color.BLACK);

		for (int y = 0; y < INVENTORY_ROWS; y++){
			for (int x = 0; x < INVENTORY_COLS; x++){

				// don't draw if these cases are true
				Item item = null;
				try {
					item = party.getItem(x, y);
				} catch(NullPointerException e) {
					//ignore
				}

				boolean weShouldDraw = true;
				if (item == null) weShouldDraw = false;

				if (weShouldDraw){
					BufferedImage portrait = item.getImage();
					g.drawImage(portrait, x*ITEM_WD, y*ITEM_HT, null);
				}

				// draw outlines
				g.setColor(Color.BLACK);
				g.drawRect(x*ITEM_WD, y*ITEM_HT, ITEM_WD, ITEM_HT);

			}
		}

	}

}
