package gui.escape;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.world.GameFrame;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * In-game, when you push escape a dialog comes up with some buttons like Load, save,
 * exit, etc. this class encapsulates that dialog.
 * @author craigthelinguist
 *
 */
public class EscapeDialog extends JDialog {

	private GameFrame main;
	
	public EscapeDialog(GameFrame gameframe){
		super(gameframe.getWindow(), true);
		main = gameframe;
		gameframe.enableCloseDialog();
		setupContents();
		this.setUndecorated(true);
		this.setLocationRelativeTo(gameframe);
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Create the components of this EscapeDialog and apply layout and formatting
	 * to them.
	 */
	private void setupContents(){
		
		// make buttons
		JButton buttonReturn = returnButton();
		JButton buttonSave = saveButton();
		JButton buttonLoad = loadButton();
		JButton buttonEndScenario = endScenarioButton();
		JButton buttonQuitGame = quitGameButton();
		
		// create layout manager
		JPanel contentPane = (JPanel)this.getContentPane();
		GroupLayout layout = new GroupLayout(contentPane);
		contentPane.setLayout(layout);
		GroupLayout.SequentialGroup horizontal = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical = layout.createSequentialGroup();
		layout.setHorizontalGroup(horizontal);
		layout.setVerticalGroup(vertical);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
	
		// add buttons in one column
		horizontal.addGroup(
			layout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(buttonReturn)
				.addComponent(buttonSave)
				.addComponent(buttonLoad)
				.addComponent(buttonEndScenario)
				.addComponent(buttonQuitGame)
			);

		// add a row per button
		vertical.addComponent(buttonReturn);
		vertical.addComponent(buttonSave);
		vertical.addComponent(buttonLoad);
		vertical.addComponent(buttonEndScenario);
		vertical.addComponent(buttonQuitGame);
	}
	
	/**
	 * Create and return a button. When clicked on, it exits the EscapeDialog and takes you
	 * back to the World.
	 * @return JButton
	 */
	private JButton returnButton(){
		JButton button = new JButton("Return");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EscapeDialog.this.dispose();
			}
		});
		return button;
	}

	/**
	 * Create and return a button. When clicked on you are prompted to save your game.
	 * @return JButton
	 */
	private JButton saveButton(){
		JButton button = new JButton("Save");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		return button;
	}
	
	/**
	 * Create and return a button. When clicked on you are prompted to select a game to
	 * load.
	 * @return JButton
	 */
	private JButton loadButton(){
		JButton button = new JButton("Return");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		return button;
	}	
	
	/**
	 * Create and return a button. When clicked on it takes you back to the main menu.
	 * @return JButton
	 */
	private JButton endScenarioButton(){
		JButton button = new JButton("End Scenario");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		return button;
	}
	
	/**
	 * Create and return a button. When clicked on it quits the game.
	 * @return JButton
	 */
	private JButton quitGameButton(){
		JButton button = new JButton("Quit Game");
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				main.getWindow().dispose();
				System.exit(0);
			}
		});
		return button;
	}
	
}
