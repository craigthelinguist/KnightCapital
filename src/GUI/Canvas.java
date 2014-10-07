package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controllers.WorldController;

import renderer.Camera;
import renderer.WorldRenderer;
import tools.GlobalConstants;
import world.World;

public class Canvas extends JPanel implements MouseListener{

	private WorldController controller;

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
	    //this.setBorder(border);

	    // set up mouse listener
	    this.addMouseListener(this);

	}

	public void setController(WorldController wc){
		controller = wc;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		controller.mousePressed(event,"canvas");
	}

	@Override
	public void mouseEntered(MouseEvent event) {	}

	@Override
	public void mouseExited(MouseEvent event) {}

	@Override
	public void mousePressed(MouseEvent event) {	}

	@Override
	public void mouseReleased(MouseEvent event) {}

	@Override
	protected void paintComponent(Graphics graphics){
		if (controller == null) return;
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0,0,getWidth(),getHeight());
		Dimension resolution = this.getSize();
		WorldRenderer.render(controller, graphics, resolution);
	}

}
