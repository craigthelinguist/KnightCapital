package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import tools.GlobalConstants;
import tools.ImageLoader;

public class MainMenuPanel extends JPanel implements ActionListener{

	private BufferedImage backgroundImage;
	
	private MainFrame mainFrame;
	
	private InventoryButton inventoryButton;
	
	public MainMenuPanel(MainFrame frame) {
		this.setBorder(BorderFactory.createLineBorder(Color.black)); //draws a border around canvas (just to show where the canvas is) (delete later)
		this.setPreferredSize(new Dimension(frame.getWidth(),frame.getHeight()));
		mainFrame = frame;
		
		/*Declare and initialize the images for the button */
		BufferedImage inventoryDefaultIcon = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "inventoryDefaultTemp.png");
		BufferedImage inventoryHoverIcon = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "inventoryHoverTemp.png");
		inventoryButton = new InventoryButton(inventoryDefaultIcon, inventoryHoverIcon);
		add(inventoryButton);
		
		/*Initialize the image for the main menu panel*/
		backgroundImage = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "mainMenuPanel.png");
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
	
	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }

}
