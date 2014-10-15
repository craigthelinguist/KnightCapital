package GUI.world;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import tools.Constants;
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
	private String FILENAME = Constants.GUI_BUTTONS;

	public GameDialog (MainFrame frame,String msg) {
		super(frame,true);
		this.setSize(400,374); //width, height
		this.setUndecorated(true); //removes the border
		// TODO: if i don't uncommenet this i can't exit the frame.... need to do this to test end turn
		// maybe there should be butotn on the gamedialog u click ok and it exits dialog - Aaron

        this.setLocationRelativeTo(frame); //set location of dialog relative to frame

	    DialogLayeredPane dLayeredPane = new DialogLayeredPane(this,msg);
		this.add(dLayeredPane);
		this.setResizable(false);
		this.setVisible(true);
	}


	/**
	 * Test method for the dialog
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GameDialog dialog = new GameDialog(new MainFrame(),"Rescue Neal, he was last seen inside the wizard's tower");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
