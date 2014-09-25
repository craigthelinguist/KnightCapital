package GUI;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CustomButton extends JButton implements MouseListener {
	
	private ImageIcon defaultIcon;
	private ImageIcon mouseOverIcon;
	 
		 		 
	public CustomButton( BufferedImage inventoryDefaultIcon, BufferedImage inventoryHoverIcon) {
		
		this.setOpaque(false); 
		this.setContentAreaFilled( false );
		this.defaultIcon = new ImageIcon(inventoryDefaultIcon); //this is the image of the button (default)
		this.mouseOverIcon = new ImageIcon(inventoryHoverIcon); //this is the image of the button when mouse is over button
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
