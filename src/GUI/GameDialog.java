package GUI;


import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import tools.GlobalConstants;
import tools.ImageLoader;


/**
 * This class extends JDialog and contains a layeredPane to display a border and a panel 
 * for the buttons.
 * 
 * @author Ewan Moshi
 *
 */
public class GameDialog extends JDialog {

	private DialogPanel panel;
	
	public GameDialog (MainFrame frame,String msg) {
		super(frame,true);
		this.setSize(400,400);
		//this.setUndecorated(true); //removes the border 
		
	    DialogLayeredPane dLayeredPane = new DialogLayeredPane(this,msg);
		this.add(dLayeredPane);
		this.setResizable(false);
		this.setVisible(true);
	}

	
	/*for testing the dialog*/
	public static void main(String[] args) {
		try {
			GameDialog dialog = new GameDialog(new MainFrame(),"Rescue Neal, he was last seen inside the wizard's tower");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
