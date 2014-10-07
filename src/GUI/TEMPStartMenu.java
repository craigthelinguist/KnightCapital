package GUI;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import tools.Log;

public class TEMPStartMenu extends JFrame {
	private JPanel panel;

	private JButton startButton;
	private JButton loadButton;
	private JButton joinButton;

	public TEMPStartMenu() {


		//Jpanel
		this.panel = new JPanel();
		this.add(panel);

		// Logo
		try{
			BufferedImage logo = ImageIO.read(new File("assets/promo/logo.png"));
			JLabel logoLabel = new JLabel(new ImageIcon(logo));
			this.panel.add(logoLabel, BorderLayout.NORTH);
		} catch(IOException e) {
			e.printStackTrace();
			Log.print("Fuck off");
		}


		// Buttons
		this.startButton = new JButton("Start Game");
		this.loadButton = new JButton("Load Game");
		this.joinButton = new JButton("Join Game");
		this.panel.add(startButton);
		this.panel.add(loadButton);
		this.panel.add(joinButton);

		// frame shit

		this.setResizable(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] gandalf) {
		new TEMPStartMenu();
	}
}
