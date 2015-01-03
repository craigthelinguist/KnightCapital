package gui.world;

import gui.reusable.CustomButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import main.GameWindow;

import tools.Constants;
import tools.ImageLoader;

public class GameDialog extends JDialog{

	private WorldPanel main;
	
	public GameDialog(GameWindow window, WorldPanel worldPanel, String message){
		super(window,true); // true arg makes this Dialog the focus
		main = worldPanel;
		worldPanel.setCurrentDialog(this);
		GameDialog gameDialog = this;
		gameDialog.setLayout(new BorderLayout());
		gameDialog.setUndecorated(true); //true means borderless window
			
		// set up components
		JTextPane pane = makeTextPane("\n"+message);
		CustomButton button = makeButton();
		gameDialog.add(pane, BorderLayout.CENTER);
		gameDialog.add(button, BorderLayout.SOUTH);
		
		// put padding on bottom to make button look netter
		JPanel contentPane = (JPanel)gameDialog.getContentPane();
		contentPane.setBorder(new EmptyBorder(0,0,20,0));
		contentPane.setBackground(Color.BLACK);
		
		gameDialog.setSize(300,250);
		gameDialog.setLocationRelativeTo(worldPanel);
		gameDialog.setResizable(false);
		gameDialog.setVisible(true);
	}
	
	/**
	 * Make and set up the JTextPane to display on this dialog.
	 * @param msg: the string to display in the JTextPane
	 */
	private JTextPane makeTextPane(String msg){
		
		// make text pane, set properties
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		
		// set font, text colour, and message
		textPane.setForeground(Color.WHITE);
		Font font = new Font("Franklin Gothic Medium", Font.BOLD, 20);
		textPane.setFont(font);
		textPane.setText(msg);
		
		// background colour will be derived from parent contanier
		textPane.setOpaque(false);
		
		// stops you being able to higlight the text
		textPane.setHighlighter(null);
		
		// align text in centre
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0,  doc.getLength(), center, false);
		
		// pad left and right margins a bit
		textPane.setMargin(new Insets(0,10,10,0));
		return textPane;
		
	}
	
	/**
	 * Make and set up the button to display on this dialog.
	 * @return
	 */
	private CustomButton makeButton(){
		BufferedImage buttonDefault = ImageLoader.load(Constants.GUI_FILEPATH + "dialogConfirmButton.png");
		BufferedImage buttonPressed = ImageLoader.load(Constants.GUI_FILEPATH + "dialogConfirmButtonPressed.png");
		CustomButton button = new CustomButton(buttonDefault, buttonPressed, buttonDefault);
		button.setBackground(Color.BLACK);
	
		// this button disposes of the GameDialog on click
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				main.endCurrentDialog();
			}
		});
		
		return button;
		
	}
	
}
