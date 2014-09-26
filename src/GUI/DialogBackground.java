package GUI;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import tools.GlobalConstants;
import tools.ImageLoader;

/**
 * This class draws a background image for the GameDialog
 * 
 * @author Ewan Moshi
 *
 */

public class DialogBackground extends JPanel implements ActionListener {
	
	/* Initialize buttons */
	CustomButton declineButton;
	CustomButton confirmButton;
	
	GameDialog gameDialog;
	
	Font F= new Font("Franklin Gothic Medium", Font.BOLD, 20);
	FontMetrics FM=getFontMetrics(F);
	
	private String message;
	private BufferedImage backgroundImage;
	
	public DialogBackground(GameDialog gd, String msg) {
		this.message = msg;
		this.gameDialog = gd;
		//this.setPreferredSize(new Dimension(200,200));
		this.setLayout(new GridBagLayout());
		/*Initialize the image for the dialog background*/
		backgroundImage = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "dialogBackground.png");
		this.setOpaque(true);
		
		/*Set up the grid bag constraints and insets */
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);

		/*Declare and initialize the images for the button */
		BufferedImage confirmButtonDefault = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "dialogConfirmButton.png");
		BufferedImage confirmButtonHover = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "dialogConfirmButtonPressed.png");
		confirmButton = new CustomButton(confirmButtonDefault, confirmButtonHover);
		c.gridx = 0;
		c.gridy = 1;
		this.add(confirmButton,c);
		
		
		/*Declare and initialize the images for the button */
		BufferedImage declineButtonDefault = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "dialogDeclineButton.png");
		BufferedImage declineButtonHover = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "dialogDeclineButtonPressed.png");
		declineButton = new CustomButton(declineButtonDefault, declineButtonHover);
		c.gridx = 1;
		c.gridy = 1;
		this.add(declineButton,c);
		
		/*Set up the action listener for the buttons */
		confirmButton.addActionListener(this);
		declineButton.addActionListener(this);
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


	@Override
	public void actionPerformed(ActionEvent e) {
			gameDialog.dispose(); 
	}


}
	

