package GUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tools.GlobalConstants;
import tools.ImageLoader;

/**
 * This class draws a background image for the GameDialog
 * 
 * @author Ewan Moshi
 *
 */

public class DialogBackground extends JPanel {
	
	Font F= new Font("Franklin Gothic Medium", Font.BOLD, 20);
	FontMetrics FM=getFontMetrics(F);
	
	private String message;
	private BufferedImage backgroundImage;
	
	public DialogBackground(String msg) {
		this.message = msg;
		//this.setPreferredSize(new Dimension(200,200));
		
		/*Initialize the image for the dialog background*/
		backgroundImage = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "dialogBackground.png");
		this.setOpaque(true);
	}
	
	
	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 16));
	    // paint the background image and scale it to fill the entire space

	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	    //g.drawString(message,50,50);
	    drawOntoPanel(message,new Rectangle(15,15,300,300), g);
	  }

	  
	  
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
