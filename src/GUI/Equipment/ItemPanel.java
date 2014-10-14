package GUI.Equipment;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;

public class ItemPanel extends JPanel {

	private BufferedImage backgroundSlot;

	public ItemPanel(String slotType) {
		this.setPreferredSize(new Dimension(80,80));
		backgroundSlot = ImageLoader.load(Constants.GUI_INVENTORY + slotType+".png");
	}


	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(backgroundSlot, 0, 0, getWidth(), getHeight(), this);
	  }
}
