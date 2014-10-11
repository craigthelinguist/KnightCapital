package GUI.EscapeDialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import tools.Constants;
import tools.ImageLoader;
import GUI.CustomButton;
import GUI.GameDialog;

public class EscapeDialogBackground extends JPanel implements ActionListener{
	/* Initialize buttons */
	private CustomButton declineButton;
    private CustomButton confirmButton;

    private CustomButton newGame;
    private CustomButton resumeGame;
    private CustomButton saveGame;
    private CustomButton loadGame;
    private CustomButton quitGame;


	EscapeDialog escapeDialog;


	private String message;
	private BufferedImage backgroundImage;

	public EscapeDialogBackground(EscapeDialog ed) {
		this.escapeDialog = ed;
		//this.setPreferredSize(new Dimension(200,200));
		this.setLayout(new GridBagLayout());
		/*Initialize the image for the dialog background*/
		backgroundImage = ImageLoader.load(Constants.GUI_FILEPATH + "dialogBackground.png");
		this.setOpaque(true);

		/*Set up the grid bag constraints and insets */
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);


		/*Declare and initialize the images for the buttons */
		BufferedImage newGameDefault = ImageLoader.load(Constants.GUI_BUTTONS + "newGameButton.png");
		BufferedImage newGamePressed = ImageLoader.load(Constants.GUI_BUTTONS + "newGameButtonClicked.png");
		BufferedImage newGameHover = ImageLoader.load(Constants.GUI_BUTTONS + "newGameButtonHover.png");
		newGame = new CustomButton(newGameDefault, newGamePressed, newGameHover);
		c.gridx = 0;
		c.gridy = 0;
		this.add(newGame,c);

		BufferedImage resumeGameDefault = ImageLoader.load(Constants.GUI_BUTTONS + "resumeButton.png");
		BufferedImage resumeGamePressed = ImageLoader.load(Constants.GUI_BUTTONS + "resumeButtonClicked.png");
		BufferedImage resumeGameHover = ImageLoader.load(Constants.GUI_BUTTONS + "resumeButtonHover.png");
		resumeGame = new CustomButton(resumeGameDefault, resumeGamePressed, resumeGameHover);
		c.gridx = 0;
		c.gridy = 1;
		this.add(resumeGame,c);


		BufferedImage saveGameDefault = ImageLoader.load(Constants.GUI_BUTTONS + "saveButton.png");
		BufferedImage saveGamePressed = ImageLoader.load(Constants.GUI_BUTTONS + "saveButtonClicked.png");
		BufferedImage saveGameHover = ImageLoader.load(Constants.GUI_BUTTONS + "saveButtonHover.png");
		saveGame = new CustomButton(saveGameDefault, saveGamePressed, saveGameHover);
		c.gridx = 0;
		c.gridy = 2;
		this.add(saveGame,c);

		BufferedImage loadGameDefault = ImageLoader.load(Constants.GUI_BUTTONS + "loadButton.png");
		BufferedImage loadGamePressed = ImageLoader.load(Constants.GUI_BUTTONS + "loadButtonClicked.png");
		BufferedImage loadGameHover = ImageLoader.load(Constants.GUI_BUTTONS + "loadButtonHover.png");
		loadGame = new CustomButton(loadGameDefault, loadGamePressed, loadGameHover);
		c.gridx = 0;
		c.gridy = 3;
		this.add(loadGame,c);

		BufferedImage quitGameDefault = ImageLoader.load(Constants.GUI_BUTTONS + "quitButton.png");
		BufferedImage quitGamePressed = ImageLoader.load(Constants.GUI_BUTTONS + "quitButtonClicked.png");
		BufferedImage quitGameHover = ImageLoader.load(Constants.GUI_BUTTONS + "quitButtonHover.png");
		quitGame = new CustomButton(quitGameDefault, quitGamePressed, quitGameHover);
		c.gridx = 0;
		c.gridy = 4;
		this.add(quitGame,c);


		/*Set up the action listener for the buttons */
		quitGame.addActionListener(this);
	}


	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	  }

	@Override
	public void actionPerformed(ActionEvent e) {

			this.escapeDialog.frame.disableCloseDialog();
			escapeDialog.dispose();

	}

}
