package GUI.EscapeDialog;

import javax.swing.JLayeredPane;

import controllers.WorldController;
import GUI.world.DialogBackground;
import GUI.world.DialogPanel;
import GUI.world.GameDialog;
import GUI.world.MainFrame;

/**
 * A layered pane that stores the background image and the background panel that holds all of the buttons.
 * @author Ewan Moshi
 *
 */
public class EscapeDialogLayeredPane extends JLayeredPane{

	int width;
	int height;

	public EscapeDialogLayeredPane(EscapeDialog ed, MainFrame frame, WorldController control) {

		width = ed.getWidth();
		height = ed.getHeight();

		/*Declare and initialise a new DialogPanel and set the layering position underneath */
		EscapeDialogBackground escapeDialogBackground = new EscapeDialogBackground(ed,frame,control);
		escapeDialogBackground.setBounds(12,12,376, 477);
		this.add(escapeDialogBackground,new Integer(1),0);

		/*Declare and initialise a new DialogBackground and set the layering position on top */
		EscapeDialogBorder escapeDialogBorder = new EscapeDialogBorder(ed);
		escapeDialogBorder.setBounds(0,0,400,500);
		this.add(escapeDialogBorder,new Integer(0),0);
	}
}
