package GUI;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tools.GlobalConstants;

public class Canvas extends JPanel implements MouseListener{

	public Canvas() {
		//this.setBorder(BorderFactory.createLineBorder(Color.red)); //draws a border around canvas (just to show where the canvas is) (delete later)
		this.setPreferredSize(new Dimension(GlobalConstants.WINDOW_WD, GlobalConstants.WINDOW_HT -200));

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
