package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controllers.BattleController;
import controllers.WorldController;
import renderer.BattleRenderer;
import renderer.Camera;
import renderer.WorldRenderer;
import tools.Constants;
import world.World;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener{

	private WorldController controller;
	private BattleController battleController;
	private boolean battle;
	private boolean click = false;
	private Point lastDrag = null;

	public Canvas() {

		//this.setBorder(BorderFactory.createLineBorder(Color.red)); //draws a border around canvas (just to show where the canvas is) (delete later)
		this.setPreferredSize(new Dimension(Constants.WINDOW_WD, Constants.WINDOW_HT -200));

		/*Load the images for the border*/
	    Border border = new Border(new ImageIcon(Constants.GUI_FILEPATH +"upperCenter.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"upperLeft.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"upperRight.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"leftCenter.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"rightCenter.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"bottomLeft.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"bottomCenter.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"bottomRight.png").getImage());

	    /*set the panel's border*/
	    //this.setBorder(border);

	    // set up mouse listener
	    this.addMouseListener(this);
	    this.addMouseMotionListener(this);

	}

	public void setController(WorldController wc){
		controller = wc;
		battle = false;
	}

	public void setController(BattleController bc){
		battleController = bc;
		battle = true;
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		controller.mousePressed(event);
	}

	@Override
	public void mouseEntered(MouseEvent event) {	}

	@Override
	public void mouseExited(MouseEvent event) {}

	@Override
	public void mousePressed(MouseEvent event) {
		this.click = true;
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		this.click = false;
		this.lastDrag = null;
	}

	@Override
	protected void paintComponent(Graphics graphics){
		if (controller == null && !battle) return;

		graphics.setColor(Color.WHITE);
		graphics.fillRect(0,0,getWidth(),getHeight());
		Dimension resolution = this.getSize();
		if(battle) BattleRenderer.render(battleController, graphics, resolution);
		else WorldRenderer.render(controller, graphics, resolution);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!click) return;

		if(lastDrag != null) {
			if(battle) battleController.mouseDragged(lastDrag, new Point(e.getX(),e.getY()));
			else controller.mouseDragged(lastDrag, new Point(e.getX(),e.getY()));
		}

		lastDrag = new Point(e.getX(),e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(battle) {
			battleController.mouseMoved(e);
		}
		else {
			if(controller != null)
				controller.mouseMoved(e);
		}
	}

}
