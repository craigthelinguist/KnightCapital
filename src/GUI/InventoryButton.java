package GUI;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class InventoryButton extends JButton implements MouseListener {
	
	private ImageIcon defaultIcon;
	private ImageIcon mouseOverIcon;
	 
		 		 
	public InventoryButton( BufferedImage inventoryDefaultIcon, BufferedImage inventoryHoverIcon) {
		
		this.setOpaque(false);
		this.setContentAreaFilled( false );
		this.defaultIcon = new ImageIcon(inventoryDefaultIcon);
		this.mouseOverIcon = new ImageIcon(inventoryHoverIcon);
		setIcon(defaultIcon);
		addMouseListener(this);
	}
		 
	 
	public void mouseClicked(MouseEvent e) { }
	 
	public void mousePressed(MouseEvent e) { }
	 
	public void mouseReleased(MouseEvent e) { }
	 
	
	
	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==this) { 
			this.setIcon(this.mouseOverIcon);
		}
	}
		 
	public void mouseExited(MouseEvent e) {
		if(e.getSource()==this) { 
			this.setIcon(this.defaultIcon);
		}
	}
}
