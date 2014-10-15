package GUI.EscapeDialog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import controllers.WorldController;
import GUI.world.DialogLayeredPane;
import GUI.world.MainFrame;

/**
 * An escape dialog that will show up in the game and allow the user to resume,quit,load,save etc.
 * @author Ewan Moshi
 *
 */
public class EscapeDialog extends JDialog  {

	protected MainFrame frame;

	public EscapeDialog (MainFrame frame, WorldController control) {
		super(frame,true);
		this.frame = frame;
		frame.enableCloseDialog();

		this.setSize(400,500); //widht, height
		this.setUndecorated(true); //removes the border
        this.setLocationRelativeTo(frame); //set location of dialog relative to frame

	    EscapeDialogLayeredPane dLayeredPane = new EscapeDialogLayeredPane(this,frame,control);
		this.setFocusable(true);
	    this.add(dLayeredPane);
		this.setResizable(false);
		this.setVisible(true);
	}

}
