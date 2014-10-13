package GUI.EscapeDialog;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tools.Constants;
import GUI.world.Border;
import GUI.world.GameDialog;

public class EscapeDialogBorder extends JPanel{

	private EscapeDialog  ed;

	public EscapeDialogBorder(EscapeDialog ed) {
		this.ed = ed;
		this.setOpaque(false);
		/*Load the images for the border*/
	    Border border = new Border(new ImageIcon(Constants.GUI_FILEPATH +"dTopLeft.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dTop.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dTopRight.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dLeft.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dRight.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dBottomLeft.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dBottom.png").getImage(),
		        new ImageIcon(Constants.GUI_FILEPATH +"dBottomRight.png").getImage());
	    this.setBorder(border);
	    //this.setPreferredSize(new Dimension(410,410));
	    //this.setBackground(new Color(0,0,0,0));

	}

	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
        g.setColor(getBackground());
        Rectangle r = g.getClipBounds();
        g.fillRect(r.x, r.y, r.width, r.height);

	  }

}
