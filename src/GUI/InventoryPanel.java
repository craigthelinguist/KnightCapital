package GUI;

import game.items.Item;

import java.awt.Color;
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
	GameDialog dSlot3;
	GameDialog dSlot4;
	GameDialog dSlot5;
	GameDialog dSlot6;

	/*The main frame */
	private MainFrame frame;

	/*The item slots*/
	ItemSlotPanel slot1;
	ItemSlotPanel slot2;
	JPanel slot3;
	JPanel slot4;
	JPanel slot5;
	JPanel slot6;

	private BufferedImage backgroundImage;

	public InventoryPanel(MainFrame f) {
		this.frame = f;

		slots = new ItemSlotPanel[INVENTORY_COLS][INVENTORY_ROWS];


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
		slot1 = new ItemSlotPanel("epipenSlot.png");
		c.insets = new Insets(15,15,10,10);
		c.gridx = 0;
		c.gridy = 0;
		//this.add(slot1,c);

		/*Declare and initialize slot2 */
		slot2 = new ItemSlotPanel("potionSlot.png");
		c.gridx = 1;
		c.gridy = 0;
		//this.add(slot2,c);

		/*Declare and initialize slot3 */
		slot3 = new JPanel();
		slot3.setBackground(new Color(255,0,0,50));  //this is just to test the position of this panel (third parameter is alpha value)
		c.insets = new Insets(15,10,10,16);
		c.gridx = 2;
		c.gridy = 0;
		this.add(slot3,c);


		/*The bottom row panels*/

		/*Declare and initialize slot4 */
		slot4 = new JPanel();
		slot4.setBackground(new Color(255,0,0,50));
		c.insets = new Insets(10,15,15,10);
		c.gridx = 0;
		c.gridy = 1;
		this.add(slot4,c);

		/*Declare and initialize slot5 */
		slot5 = new JPanel();
		slot5.setBackground(new Color(255,0,0,50));
		c.gridx = 1;
		c.gridy = 1;
		this.add(slot5,c);

		/*Declare and initialize slot6 */
		slot6 = new JPanel();
		slot6.setBackground(new Color(255,0,0,50));
		c.insets = new Insets(10,10,15,16);
		c.gridx = 2;
		c.gridy = 1;
		this.add(slot6,c);

		/*Add mouse listener for all slots */
		slot1.addMouseListener(this);
		slot2.addMouseListener(this);
		slot3.addMouseListener(this);
		slot4.addMouseListener(this);
		slot5.addMouseListener(this);
		slot6.addMouseListener(this);


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
			dSlot1 = new ItemSlotInformation(frame,items[0][0].getDescription());
			dSlot1.setLocation(frame.getWidth()-500,frame.getHeight()-400);
			dSlot1.setVisible(true);
	    }
		if(e.getSource() == slot2) {
			slot2.changeBackground(ImageManipulation.lighten(slot2.getBackgroundImage(),55));
			dSlot2 = new ItemSlotInformation(frame,"Heals your hero for    100 health.");
			dSlot2.setLocation(frame.getWidth()-425,frame.getHeight()-400);
			dSlot2.setVisible(true);
	    }
		if(e.getSource() == slot3) {
	        slot3.setBackground(Color.YELLOW);
	    }
		if(e.getSource() == slot4) {
			slot4.setBackground(Color.YELLOW);
		}
		if(e.getSource() == slot5) {
	        slot5.setBackground(Color.YELLOW);
	    }
		if(e.getSource() == slot6) {
			slot6.setBackground(Color.YELLOW);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		repaint();
		if(e.getSource() == slots[0][0]) {
			slots[0][0].changeBackground(ImageLoader.load(Constants.ITEMS + slots[0][0].getImageName()+"Slot"));
			dSlot1.dispose();
	    }
		if(e.getSource() == slot2) {
			slot2.changeBackground(ImageLoader.load(Constants.GUI_FILEPATH + slot2.getImageName()));
			dSlot2.dispose();
		}
		if(e.getSource() == slot3) {
	        slot3.setBackground(new Color(255,0,0,50));
	    }
		if(e.getSource() == slot4) {
			slot4.setBackground(new Color(255,0,0,50));
		}
		if(e.getSource() == slot5) {
	        slot5.setBackground(new Color(255,0,0,50));
	    }
		if(e.getSource() == slot6) {
			slot6.setBackground(new Color(255,0,0,50));
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}


	public void updateInventoryPanel(Tile tile) throws Exception {
		if (tile == null) return;
		if(!(tile.occupant() instanceof Party)) return;

		Party p =(Party)tile.occupant();
		items = p.getInventory();

		for(int i = 0; i < items.length; i++) {
			for(int j = 0; j < items[i].length; j++) {

				if(items[i][j] != null) {

					for(int x = 0; x < items.length; x++) {
						for(int y = 0; y < items[x].length; y++) {
								if(slots[x][y] == null) {
									/*Declare and initialize slot1 (panel) */
									slots[x][y] = new ItemSlotPanel(items[i][j].getName());
									c.insets = new Insets(15,15,10,10);
									c.gridx = i;
									c.gridy = j;
									slots[x][y].addMouseListener(this);
									this.add(slots[x][y],c);

							}
						}
					}

				}
			}
		}
	}

}
