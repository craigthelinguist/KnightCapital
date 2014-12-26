package gui.main;

import gui.reusable.CustomButton;

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

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import main.GameWindow;
import networking.Server;
import storage.converters.WorldLoader;
import tools.Constants;
import tools.ImageLoader;
import tools.Log;
import world.World;
import controllers.WorldController;

/**
 * This panel has the buttons for the main menu and the backgrund image.
 * @author moshiewan
 *
 */
public class MainMenu extends JPanel{
	
	private GameWindow window;
	
	public MainMenu(GameWindow window) {
	
		this.window = window;
		JButton buttonNewGame = newGameButton();
		JButton buttonLoadGame = loadGameButton();
		JButton buttonQuitGame = quitGameButton();
		
		// create layout manager
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);

		// add buttons in one column
		horizontal.addGroup(
			layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(buttonNewGame)
				.addComponent(buttonLoadGame)
				.addComponent(buttonQuitGame)
			);

		// add a row per button
		vertical.addComponent(buttonNewGame);
		vertical.addComponent(buttonLoadGame);
		vertical.addComponent(buttonQuitGame);
		
	}
	
	private JButton newGameButton(){
		JButton button = new JButton("New Game");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
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
						window.newWorldScene(world);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(window, "Error loading " + filepath);
					}
				}
			}		
		});
		return button;
	}
	
	private JButton loadGameButton(){
		JButton button = new JButton("Load Game");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String path = Constants.DATA_SAVES;
				File f = new File(path);
				JFileChooser chooser = new JFileChooser(f);
				Filter filter = new Filter(".save");
				chooser.setFileFilter(filter);
				int value = chooser.showOpenDialog(null);
				String filepath = null;
				if (value == JFileChooser.APPROVE_OPTION){
					filepath = chooser.getSelectedFile().getPath();
					try {
						World world = WorldLoader.load(filepath);
						window.newWorldScene(world);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(window, "Error loading " + filepath);
					}
				}
			}		
		});
		return button;
	}
	
	private JButton quitGameButton(){
		JButton button = new JButton("Quit Game");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}		
		});
		return button;
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
