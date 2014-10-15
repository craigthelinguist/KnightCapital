package GUI.world;

import game.items.Item;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;
import tools.ImageManipulation;
import world.icons.Party;
import world.tiles.Tile;

/**
 * This panel displays inventory as an image background for the panel
 *
 * @author Ewan Moshi
 *
 */
public class InventoryPanel extends JPanel implements MouseListener {

	public static final int INVENTORY_ROWS = 2;
	public static final int INVENTORY_COLS = 3;

	GridBagConstraints c;

	private ItemSlotPanel[][] slots;
	private Item[][] items;

	/*The item dialogs/descriptions */
	ItemSlotInformation dSlot1;
	ItemSlotInformation dSlot2;
	ItemSlotInformation dSlot3;
	ItemSlotInformation dSlot4;
	ItemSlotInformation dSlot5;
	ItemSlotInformation dSlot6;

	/*The main frame */
	private MainFrame frame;

	/*The item slots*/
	JPanel slot1;
	JPanel slot2;
	JPanel slot3;
	JPanel slot4;
	JPanel slot5;
	JPanel slot6;

	private BufferedImage backgroundImage;

	public InventoryPanel(MainFrame f) {
		this.frame = f;

		slots = new ItemSlotPanel[INVENTORY_ROWS][INVENTORY_COLS];


		/*set the size of this panel to be size of the image*/
		this.setPreferredSize(new Dimension(375,200));
		this.setOpaque(true);
		/*Initialize the image for the inventory panel*/
		backgroundImage = ImageLoader.load(Constants.GUI_FILEPATH + "inventory.png");


		/*Initialize the layout and the insets*/
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.insets = new Insets(15,15,10,10);
		//c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;


		/*The top row panels */

		/*Declare and initialize slot1 (panel) */
		slot1 = new JPanel();
		slot1.setOpaque(false);
		c.insets = new Insets(15,15,10,10);
		c.gridx = 0;
		c.gridy = 0;
		this.add(slot1,c);

		/*Declare and initialize slot2 */
		slot2 = new JPanel();
		slot2.setOpaque(false);
		c.gridx = 1;
		c.gridy = 0;
		this.add(slot2,c);

		/*Declare and initialize slot3 */
		slot3 = new JPanel();
		slot3.setOpaque(false);
		c.insets = new Insets(15,10,10,16);
		c.gridx = 2;
		c.gridy = 0;
		this.add(slot3,c);


		/*The bottom row panels*/

		/*Declare and initialize slot4 */
		slot4 = new JPanel();
		slot4.setOpaque(false);
		c.insets = new Insets(10,15,15,10);
		c.gridx = 0;
		c.gridy = 1;
		this.add(slot4,c);

		/*Declare and initialize slot5 */
		slot5 = new JPanel();
		slot5.setOpaque(false);
		c.gridx = 1;
		c.gridy = 1;
		this.add(slot5,c);

		/*Declare and initialize slot6 */
		slot6 = new JPanel();
		slot6.setOpaque(false);
		c.insets = new Insets(10,10,15,16);
		c.gridx = 2;
		c.gridy = 1;
		this.add(slot6,c);

	}


	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, 375, 200, this);
	  }


	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		repaint();

		if(e.getSource() == slots[0][0]) {
			slots[0][0].changeBackground(ImageManipulation.lighten(slots[0][0].getBackgroundImage(),55));
			dSlot1 = new ItemSlotInformation(frame,items[0][0].getItem().getDescription());
			dSlot1.setLocation(frame.getWidth()-500,frame.getHeight()-400);
			dSlot1.setVisible(true);
	    }
		if(e.getSource() == slots[0][1]) {
			slots[0][1].changeBackground(ImageManipulation.lighten(slots[0][1].getBackgroundImage(),55));
			dSlot2 = new ItemSlotInformation(frame,items[0][1].getItem().getItem().getDescription());
			dSlot2.setLocation(frame.getWidth()-425,frame.getHeight()-400);
			dSlot2.setVisible(true);
	    }
		if(e.getSource() == slots[0][2]) {
			slots[0][2].changeBackground(ImageManipulation.lighten(slots[0][2].getBackgroundImage(),55));
			dSlot3 = new ItemSlotInformation(frame,items[0][2].getItem().getDescription());
			dSlot3.setLocation(frame.getWidth()-375,frame.getHeight()-400);
			dSlot3.setVisible(true);
	    }
		if(e.getSource() == slots[1][0]) {
			slots[1][0].changeBackground(ImageManipulation.lighten(slots[1][0].getBackgroundImage(),55));
			dSlot4 = new ItemSlotInformation(frame,items[1][0].getItem().getDescription());
			dSlot4.setLocation(frame.getWidth()-500,frame.getHeight()-400);
		}
		if(e.getSource() == slots[1][1]) {
			slots[1][1].changeBackground(ImageManipulation.lighten(slots[1][1].getBackgroundImage(),55));
			dSlot5 = new ItemSlotInformation(frame,items[1][1].getItem().getDescription());
			dSlot5.setLocation(frame.getWidth()-500,frame.getHeight()-400);
	    }
		if(e.getSource() == slots[1][2]) {
			slots[1][2].changeBackground(ImageManipulation.lighten(slots[1][2].getBackgroundImage(),55));
			dSlot6 = new ItemSlotInformation(frame,items[1][2].getItem().getDescription());
			dSlot6.setLocation(frame.getWidth()-500,frame.getHeight()-400);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		repaint();
		if(e.getSource() == slots[0][0]) {
			slots[0][0].changeBackground(ImageLoader.load(Constants.ITEMS + slots[0][0].getImageName()));
			dSlot1.dispose();
	    }
		if(e.getSource() == slots[0][1]) {
			slots[0][1].changeBackground(ImageLoader.load(Constants.ITEMS + slots[0][1].getImageName()));
			dSlot2.dispose();
		}
		if(e.getSource() == slots[0][2]) {
			slots[0][2].changeBackground(ImageLoader.load(Constants.ITEMS + slots[0][2].getImageName()));
			dSlot3.dispose();
	    }
		if(e.getSource() == slots[1][0]) {
			slots[1][0].changeBackground(ImageLoader.load(Constants.ITEMS + slots[1][0].getImageName()));
			dSlot4.dispose();
		}
		if(e.getSource() == slots[1][1]) {
			slots[1][1].changeBackground(ImageLoader.load(Constants.ITEMS + slots[1][1].getImageName()));
			dSlot5.dispose();	    }
		if(e.getSource() == slots[1][2]) {
			slots[1][2].changeBackground(ImageLoader.load(Constants.ITEMS + slots[1][2].getImageName()));
			dSlot6.dispose();
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}


	/**
	 * Update the inventory panel.
	 * Iterate through the items of the party and redraw all the items into the Item Slot Panels.
	 * @param tile
	 * @throws Exception
	 */
	public void updateInventoryPanel(Tile tile) throws Exception {
		if (tile == null) return;
		if(!(tile.occupant() instanceof Party)) return;

		Party p =(Party)tile.occupant();
		items = p.getInventory();

		for(int i = 0; i < items.length; i++) {
			for(int j = 0; j < items[0].length; j++) {
				if(items[i][j] != null) {
					if(slots[i][j] == null) {
						/*Declare and initialize slot1 (panel) */

						/*This changes insets depending on which slot it's up to. This is a poverty solution but they are not aligned properly
						 * if you don't use different inset. This is because the inventory slots aren't divided equally.
						 */
						if(i == 1 && j == 2) {
							c.insets = new Insets(10,10,15,16);
						}
						else if(i == 0 && j == 2) {
							c.insets = new Insets(15,10,10,16);
						}
						else if(i == 1  && j == 0) {
							c.insets = new Insets(10,15,15,10);
						}
						else if(i == 1 && j == 1) {
							c.insets = new Insets(10,15,15,10);
						}
						else {
							c.insets = new Insets(15,15,10,10);
						}
						slots[i][j] = new ItemSlotPanel(items[i][j].getImageName()+"Slot");
						c.gridx = j;
						c.gridy = i;
						slots[i][j].addMouseListener(this);
						this.add(slots[i][j],c);
					}
				}
			}
		}
	}
}

