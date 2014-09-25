package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;


/**
 * Create a custom button that has a default icon and a hover icon for when the user
 * clicks/hovers on the button.
 * 
 * @author Ewan Moshi
 *
 */
public class CustomButton extends JButton implements MouseListener {
	
	private ImageIcon defaultIcon;
	private ImageIcon mouseOverIcon;
	 
		 		 
	public CustomButton( BufferedImage buttonDefaultIcon, BufferedImage buttonHoverIcon) {
		this.setBorderPainted(false); //removes the border around buttons
        this.setPreferredSize(new Dimension(buttonDefaultIcon.getWidth(), buttonDefaultIcon.getHeight())); //set button size to image width/height
		this.setOpaque(false); 
		this.setContentAreaFilled( false );
		this.defaultIcon = new ImageIcon(buttonDefaultIcon); //this is the image of the button (default)
		this.mouseOverIcon = new ImageIcon(buttonHoverIcon); //this is the image of the button when mouse is over button
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
			this.setIcon(this.mouseOverIcon);
		}
	}
	 
	public void mouseReleased(MouseEvent e) {
		if(e.getSource()==this) { 
			this.setIcon(this.defaultIcon);
		}
	}

	public void mouseEntered(MouseEvent e) { }
		 
	public void mouseExited(MouseEvent e) {
		if(e.getSource()==this) { 
			this.setIcon(this.defaultIcon);
		}
	}
}
