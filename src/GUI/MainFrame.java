package GUI;


import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {
	
	public MainFrame() {

		/* These two statements make the frame full screen. (Commented out for now)
		this.setExtendedState(this.MAXIMIZED_BOTH);  
		this.setUndecorated(true);*/
		
		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	
	
	
	
	
	/* Main method to test the MainFrame */
	public static void main(String[] args) {
		new MainFrame();
	}
}
