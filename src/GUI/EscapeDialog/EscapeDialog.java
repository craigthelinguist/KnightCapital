package GUI.EscapeDialog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import GUI.world.DialogLayeredPane;
import GUI.world.MainFrame;

public class EscapeDialog extends JDialog  {

	protected MainFrame frame;

	public EscapeDialog (MainFrame frame) {
		super(frame,true);
		this.frame = frame;
		frame.enableCloseDialog();

		this.setSize(400,500); //widht, height
		this.setUndecorated(true); //removes the border
        this.setLocationRelativeTo(frame); //set location of dialog relative to frame

	    EscapeDialogLayeredPane dLayeredPane = new EscapeDialogLayeredPane(this);
		this.add(dLayeredPane);
		this.setResizable(false);
		this.setVisible(true);
	}

}
