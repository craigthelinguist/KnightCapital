package GUI.world;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;

public class ItemInfoPanel extends JPanel   {

	private String message;

	private BufferedImage backgroundImage;

	Font F= new Font("Franklin Gothic Medium", Font.BOLD, 16);
	FontMetrics FM=getFontMetrics(F);

	/**
	 *
	 * @param frame The mainframe this panel belongs to
	 * @param msg The message to draw onto this panel
	 */
	public ItemInfoPanel(MainFrame frame, String msg) {
		this.message = msg;
		this.setLayout(new BorderLayout());
		/*Initialize the image for the inventory panel*/
		backgroundImage = ImageLoader.load(Constants.GUI_FILEPATH + "itemInfoBackground.png");
		//this.setBackground(new Color(56,56,56,10));

		/*This adds an image to the label but maybe add later, it looks kinda stupid with current icons*/
	    //ImageIcon itemImage = new ImageIcon(GlobalConstants.GUI_FILEPATH + "epipen.png");
	    //JLabel label = new JLabel(itemImage);
		//
		//JLabel itemIcon = new JLabel(itemImage);
		//this.add(itemIcon,BorderLayout.SOUTH);
	}


	/**
	 * Sets the font and draws the background image and calls the drawOntoPanel method which enables text wrapping
	 * for the panel.
	 */
	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 16));
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	    drawOntoPanel(message,new Rectangle(10,10,175,180), g);
	  }


	  /**
	   * Wraps the text around a panel. Ensures that the message is not drawn
	   * out of the panel.
	   *
	   * @param str The message to print on the panel
	   * @param rc Size of the rectangle to draw the string on
	   * @param g Graphics
	   */
	  public void drawOntoPanel(String str, Rectangle rc, Graphics g)
	  {
	     g.setFont(F);
	     g.setColor(new Color(225,179,55));
	     int lineSep=3;
	     int strWidth=FM.stringWidth(str);
	     int strHeight=FM.getHeight();
	     int strLength=str.length();
	     int charPerLine=(int)(strLength*rc.width/(double)strWidth);

	     if(charPerLine>=strLength) {
	        g.drawString(str,rc.x,rc.y+strHeight);
	     }
	     else {
	        int lines=strLength/charPerLine;
	        int skip=0;
	        for(int i=1; i<=lines; i++) {
	           String sTemp=str.substring(skip,skip+charPerLine-1);
	           if(!str.substring(skip+charPerLine-1,skip+charPerLine).equals(" ")&& !str.substring(skip+charPerLine-2,skip+charPerLine-1).equals(" ")) {
	              sTemp+="-";
	           }
	           g.drawString(sTemp.trim(),rc.x,rc.y+i*strHeight+(i-1)*lineSep);
	           skip+=charPerLine-1;
	        }
	        g.drawString(str.substring(skip,strLength).trim(),rc.x,rc.y+(lines+1)*strHeight+(lines)*lineSep);
	     }
	  }

}
