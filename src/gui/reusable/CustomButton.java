package gui.reusable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import tools.KCImage;


/**
 * Create a custom button that has a default icon and a hover icon for when the user
 * clicks/hovers on the button.
 *
 * @author Ewan Moshi
 *
 */
public class CustomButton extends JButton implements MouseListener {

	private ImageIcon defaultIcon;
	private ImageIcon mousePressedIcon;
	private ImageIcon mouseHoverIcon;

	public CustomButton(BufferedImage saveGameDefault, BufferedImage saveGamePressed, BufferedImage saveGameHover) {
		this.setBorderPainted(false); //removes the border around buttons
        this.setPreferredSize(new Dimension(saveGameDefault.getWidth(), saveGameDefault.getHeight())); //set button size to image width/height
		this.setMaximumSize(this.getPreferredSize());
		this.setMinimumSize(this.getPreferredSize());
        
        this.setOpaque(false);
		this.setContentAreaFilled( false );
		this.defaultIcon = new ImageIcon(saveGameDefault); //this is the image of the button (default)
		this.mousePressedIcon = new ImageIcon(saveGamePressed); //this is the image of the button when mouse is pressed on button
		
		
		/*Some buttons don't have a hover icon so we must check if the parameter is null to prevent null pointer when trying to set the icon for hover to null*/
		if(saveGameHover == null) {
			return;
		}
		else {
			this.mouseHoverIcon = new ImageIcon(saveGameHover);
		}
		setIcon(defaultIcon);
		addMouseListener(this);
	}


	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==this) {
			this.setIcon(this.defaultIcon);
		}
	}

	public void mousePressed(MouseEvent e) {
		if(e.getSource()==this) {
			this.setIcon(this.mousePressedIcon);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getSource()==this) {
			this.setIcon(this.defaultIcon);
		}
	}

	public void mouseEntered(MouseEvent e) {
		if(mouseHoverIcon == null) {
			return;
		}
		else {
			this.setIcon(this.mouseHoverIcon);
		}
	}

	public void mouseExited(MouseEvent e) {
		if(e.getSource()==this) {
			this.setIcon(this.defaultIcon);
		}
	}
}
