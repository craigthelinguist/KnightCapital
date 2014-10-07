package GUI.EscapeDialog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

import GUI.DialogLayeredPane;

public class EscapeDialog extends JDialog  {


	public EscapeDialog (JFrame frame) {
		super(frame,true);
		this.setSize(400,500); //widht, height
		this.setUndecorated(true); //removes the border
        this.setLocationRelativeTo(frame); //set location of dialog relative to frame

	    EscapeDialogLayeredPane dLayeredPane = new EscapeDialogLayeredPane(this);
		this.add(dLayeredPane);
		this.setResizable(false);
		this.setVisible(true);
	}





	/*for testing the dialog*/
	public static void main(String[] args) {
		try {
			EscapeDialog dialog = new EscapeDialog(new JFrame());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
