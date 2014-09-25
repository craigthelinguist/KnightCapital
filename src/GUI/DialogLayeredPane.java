package GUI;

import javax.swing.JLayeredPane;

public class DialogLayeredPane extends JLayeredPane {

	int width;
	int height;
	
	public DialogLayeredPane(GameDialog gd) {
		width = gd.getWidth();
		height = gd.getHeight();
		
		DialogPanel dialogPanel = new DialogPanel(gd);
		dialogPanel.setBounds(0,0,400, 375);
		this.add(dialogPanel,new Integer(0),0);
		
		DialogBackground dialogBackground = new DialogBackground();
		dialogBackground.setBounds(12,12,376,351);
		this.add(dialogBackground,new Integer(1),0);
	}
	
}
