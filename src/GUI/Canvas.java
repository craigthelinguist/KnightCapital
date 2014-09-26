package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tools.GlobalConstants;
import tools.ImageLoader;

public class Canvas extends JPanel implements MouseListener{

	private MainFrame mainFrame;
	
	public Canvas(MainFrame frame) {
		//this.setBorder(BorderFactory.createLineBorder(Color.red)); //draws a border around canvas (just to show where the canvas is) (delete later)
		mainFrame = frame;
		this.setPreferredSize(new Dimension(frame.getWidth(),frame.getHeight()-200));
		
		/*Load the images for the border*/
	    Border border = new Border(new ImageIcon(GlobalConstants.GUI_FILEPATH +"upperCenter.png").getImage(),
		        new ImageIcon(GlobalConstants.GUI_FILEPATH +"upperLeft.png").getImage(), 
		        new ImageIcon(GlobalConstants.GUI_FILEPATH +"upperRight.png").getImage(),
		        new ImageIcon(GlobalConstants.GUI_FILEPATH +"leftCenter.png").getImage(),
		        new ImageIcon(GlobalConstants.GUI_FILEPATH +"rightCenter.png").getImage(),
		        new ImageIcon(GlobalConstants.GUI_FILEPATH +"bottomLeft.png").getImage(), 
		        new ImageIcon(GlobalConstants.GUI_FILEPATH +"bottomCenter.png").getImage(),
		        new ImageIcon(GlobalConstants.GUI_FILEPATH +"bottomRight.png").getImage());
	    /*set the panel's border*/
	    this.setBorder(border);
	}
	
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {	}

	@Override
	public void mouseEntered(MouseEvent arg0) {	}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {	}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	

}
