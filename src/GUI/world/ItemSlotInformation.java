package GUI.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;

public class ItemSlotInformation extends JDialog  {

	/**
	 *
	 * @param frame The MainFrame this Dialog is attached to
	 * @param msg The message to draw in this dialog
	 */

	public ItemSlotInformation(MainFrame frame, String msg) {
		super(frame,true);
		this.setModal(false);
		this.setSize(200,200); //height, width
		//this.setSize(400,374); //height, width
		this.setUndecorated(true); //removes the border

		ItemInfoPanel panel = new ItemInfoPanel(frame,msg);
		this.add(panel);
		this.setResizable(false);
	}


}
