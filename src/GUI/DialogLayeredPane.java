package GUI;

import javax.swing.JLayeredPane;

/**
 * This class layers two panels on top of each other (DialogPanel and DialogBackground)
 * 
 * @author Ewan Moshi
 *
 */


public class DialogLayeredPane extends JLayeredPane {

	int width;
	int height;
	
	public DialogLayeredPane(GameDialog gd,String msg) {
		width = gd.getWidth();
		height = gd.getHeight();
		
		/*Declare and initialise a new DialogPanel and set the layering position underneath */
		DialogPanel dialogPanel = new DialogPanel(gd);
		dialogPanel.setBounds(0,0,400, 375);
		this.add(dialogPanel,new Integer(0),0);
		
		/*Declare and initialise a new DialogBackground and set the layering position on top */
		DialogBackground dialogBackground = new DialogBackground(msg);
		dialogBackground.setBounds(12,12,376,351);
		this.add(dialogBackground,new Integer(1),0);
	}
	
}
