package GUI.MainMenu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import storage.converters.WorldLoader;
import tools.Constants;
import tools.ImageLoader;
import world.World;
import GUI.world.CustomButton;
import controllers.WorldController;

public class MainMenuPanel extends JPanel implements ActionListener{

	private CustomButton newGame;
	private CustomButton loadGame;
	private CustomButton joinGame;
	private CustomButton quitGame;

	private JFrame frame;
	private BufferedImage backgroundImage;

	public MainMenuPanel() {
		backgroundImage = ImageLoader.load(Constants.GUI_FILEPATH + "mainMenuBackground.png");
		frame = new JFrame();
		this.setPreferredSize(new Dimension(650,650));
		/*Set up the grid bag constraints and insets */
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(250,50,0,0);
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.weightx = 1.0;
		//c.weighty = 1.0;

		/*Declare and initialize the images for the buttons */
		BufferedImage newGameDefault = ImageLoader.load(Constants.GUI_BUTTONS + "newGameMain.png");
		BufferedImage newGamePressed = ImageLoader.load(Constants.GUI_BUTTONS + "newGameMainClicked.png");
		BufferedImage newGameHover = ImageLoader.load(Constants.GUI_BUTTONS + "newGameMainHover.png");
		newGame = new CustomButton(newGameDefault, newGamePressed, newGameHover);
		c.gridx = 0;
		c.gridy = 1;
		newGame.addActionListener(this);
		this.add(newGame,c);

		BufferedImage loadGameDefault = ImageLoader.load(Constants.GUI_BUTTONS + "loadMain.png");
		BufferedImage loadGamePressed = ImageLoader.load(Constants.GUI_BUTTONS + "loadMainClicked.png");
		BufferedImage loadGameHover = ImageLoader.load(Constants.GUI_BUTTONS + "loadMainHover.png");
		loadGame = new CustomButton(loadGameDefault, loadGamePressed, loadGameHover);
		c.insets = new Insets(0,50,0,0);
		c.gridx = 0;
		c.gridy = 2;
		loadGame.addActionListener(this);
		this.add(loadGame,c);

		BufferedImage joinGameDefault = ImageLoader.load(Constants.GUI_BUTTONS + "joinMain.png");
		BufferedImage joinGamePressed = ImageLoader.load(Constants.GUI_BUTTONS + "joinMainClicked.png");
		BufferedImage joinGameHover = ImageLoader.load(Constants.GUI_BUTTONS + "joinMainHover.png");
		joinGame = new CustomButton(joinGameDefault, joinGamePressed, joinGameHover);
		c.insets = new Insets(0,50,0,0);
		c.gridx = 0;
		c.gridy = 3;
		joinGame.addActionListener(this);
		this.add(joinGame,c);

		BufferedImage quitGameDefault = ImageLoader.load(Constants.GUI_BUTTONS + "quitMain.png");
		BufferedImage quitGamePressed = ImageLoader.load(Constants.GUI_BUTTONS + "quitMainClicked.png");
		BufferedImage quitGameHover = ImageLoader.load(Constants.GUI_BUTTONS + "quitMainHover.png");
		quitGame = new CustomButton(quitGameDefault, quitGamePressed, quitGameHover);
		c.insets = new Insets(0,50,0,0);
		c.gridx = 0;
		c.gridy = 4;
		quitGame.addActionListener(this);
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


	@Override
	public void actionPerformed(ActionEvent e) {

		// if possible start the file chooser in this directory
		//String fp = new File("").getAbsolutePath() + File.separatorChar + Constants.DATA_LEVELS;

		if(e.getSource() == newGame) {
			String path = Constants.DATA_SCENARIOS;
			File f = new File(path);
			JFileChooser chooser = new JFileChooser(f);
			Filter filter = new Filter(".level");
			chooser.setFileFilter(filter);
			int value = chooser.showOpenDialog(null);
			String filepath = null;
			if (value == JFileChooser.APPROVE_OPTION){
				filepath = chooser.getSelectedFile().getPath();
				try {
					World world = WorldLoader.load(filepath);
					frame.dispose();
					new WorldController(world,world.getPlayers()[0]);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frame, "Error loading " + filepath);
				}
			}
		}

		// if possible start the file chooser in this directory
		//String fp = new File("").getAbsolutePath() + File.separatorChar + Constants.DATA_SAVES;

		else if(e.getSource() == loadGame) {
			String path = Constants.DATA_SAVES;
			File f = new File(path);
			JFileChooser chooser = new JFileChooser(f);
			Filter filter = new Filter(".save");
			chooser.setFileFilter(filter);
			int value = chooser.showOpenDialog(null);
			String filepath = null;
			if (value == JFileChooser.APPROVE_OPTION){
				filepath = chooser.getSelectedFile().getPath();
				World world;
				try {
					world = WorldLoader.load(filepath);
					frame.dispose();
					new WorldController(world,world.getPlayers()[0]);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(frame, "Error loading " + filepath);
				}
			}


		}
		else if(e.getSource() == joinGame) {
			//what to do if button pressed is join game
		}
		else if(e.getSource() == quitGame) {
			System.exit(0);
		}
	}




	private class Filter extends FileFilter{

		private String extension;

		public Filter(String str){
			extension = str;
		}

		@Override
		public boolean accept(File f) {
			return f.getAbsolutePath().endsWith(extension) || f.isDirectory();

		}

		@Override
		public String getDescription() {
			return extension;
		}

	}



}
