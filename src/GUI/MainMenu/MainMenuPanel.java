package GUI.MainMenu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.CustomButton;
import tools.GlobalConstants;
import tools.ImageLoader;
import tools.Log;

public class MainMenuPanel extends JPanel {
	
    private CustomButton newGame;
    private CustomButton loadGame;
    private CustomButton joinGame;
    private CustomButton quitGame;
	
    private JFrame frame;
    private BufferedImage backgroundImage;
    
	public MainMenuPanel() {
		backgroundImage = ImageLoader.load(GlobalConstants.GUI_FILEPATH + "mainMenuBackground.png");
		frame = new JFrame();
		this.setPreferredSize(new Dimension(650,650));
		/*Set up the grid bag constraints and insets */
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(250,30,0,0);
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.weightx = 1.0;
		//c.weighty = 1.0;

		/*Declare and initialize the images for the buttons */
		BufferedImage newGameDefault = ImageLoader.load(GlobalConstants.GUI_BUTTONS + "newGameMain.png");
		BufferedImage newGamePressed = ImageLoader.load(GlobalConstants.GUI_BUTTONS + "newGameMainClicked.png");
		BufferedImage newGameHover = ImageLoader.load(GlobalConstants.GUI_BUTTONS + "newGameMainHover.png");
		newGame = new CustomButton(newGameDefault, newGamePressed, newGameHover);
		c.gridx = 0;
		c.gridy = 1;
		this.add(newGame,c);

		BufferedImage loadGameDefault = ImageLoader.load(GlobalConstants.GUI_BUTTONS + "loadMain.png");
		BufferedImage loadGamePressed = ImageLoader.load(GlobalConstants.GUI_BUTTONS + "loadMainClicked.png");
		BufferedImage loadGameHover = ImageLoader.load(GlobalConstants.GUI_BUTTONS + "loadMainHover.png");
		loadGame = new CustomButton(loadGameDefault, loadGamePressed, loadGameHover);
		c.insets = new Insets(0,30,0,0);
		c.gridx = 0;
		c.gridy = 2;
		this.add(loadGame,c);
		
		BufferedImage joinGameDefault = ImageLoader.load(GlobalConstants.GUI_BUTTONS + "joinMain.png");
		BufferedImage joinGamePressed = ImageLoader.load(GlobalConstants.GUI_BUTTONS + "joinMainClicked.png");
		BufferedImage joinGameHover = ImageLoader.load(GlobalConstants.GUI_BUTTONS + "joinMainHover.png");
		joinGame = new CustomButton(joinGameDefault, joinGamePressed, joinGameHover);
		c.insets = new Insets(0,30,0,0);
		c.gridx = 0;
		c.gridy = 3;
		this.add(joinGame,c);
		
		BufferedImage quitGameDefault = ImageLoader.load(GlobalConstants.GUI_BUTTONS + "quitMain.png");
		BufferedImage quitGamePressed = ImageLoader.load(GlobalConstants.GUI_BUTTONS + "quitMainClicked.png");
		BufferedImage quitGameHover = ImageLoader.load(GlobalConstants.GUI_BUTTONS + "quitMainHover.png");
		quitGame = new CustomButton(quitGameDefault, quitGamePressed, quitGameHover);
		c.insets = new Insets(0,30,0,0);
		c.gridx = 0;
		c.gridy = 4;
		this.add(quitGame,c);
		
		
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(this,BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	

	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

	  }
	
	public static void main(String[] args) {
		new MainMenuPanel();
	}
}
