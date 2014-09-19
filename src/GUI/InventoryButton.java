package GUI;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class InventoryButton extends JButton implements MouseListener {
	
	private ImageIcon defaultColor;
	private ImageIcon mouseOverColor;
	 
		 		 
	public InventoryButton(String text, BufferedImage inventoryDefaultIcon, BufferedImage inventoryHoverIcon) {
		super(text);
		this.defaultColor = new ImageIcon(inventoryDefaultIcon);
		this.mouseOverColor = new ImageIcon(inventoryHoverIcon);
		setIcon(defaultColor);
		addMouseListener(this);
	}
		 
	 
	public void mouseClicked(MouseEvent e) { }
	 
	public void mousePressed(MouseEvent e) { }
	 
	public void mouseReleased(MouseEvent e) { }
	 
	
	
	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==this) { 
			this.setIcon(this.mouseOverColor);
		}
	}
		 
	public void mouseExited(MouseEvent e) {
		if(e.getSource()==this) { 
			this.setIcon(this.defaultColor);
		}
	}
}
