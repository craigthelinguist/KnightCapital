package GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import networking.Client;
import networking.Server;
import tools.Constants;
import tools.Log;

public class TEMPStartMenu extends JFrame {
	private JPanel mainPanel;

	private JButton startButton;
	private JButton loadButton;
	private JButton joinButton;
	private GridBagConstraints gc;

	public TEMPStartMenu() {

		this.gc = new GridBagConstraints();

		setMainPanel();
		//setJoinPanel();
		//setServerPanel();



		// frame shit

		this.setResizable(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void createServer() {

		System.out.println("Starting New Server");
		// make server
		try {
			new Server();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setMainPanel() {
		//Jpanel
		this.mainPanel = new JPanel();
		this.add(mainPanel);

		this.mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		// Logo
		gc.gridx = 0;
		gc.gridy = 0;
		try{
			BufferedImage logo = ImageIO.read(new File("assets/promo/logo.png"));
			JLabel logoLabel = new JLabel(new ImageIcon(logo));
			this.mainPanel.add(logoLabel, gc);
		} catch(IOException e) {
			e.printStackTrace();
			Log.print("Fuck off");
		}


		// Buttons
		// Start
		gc.gridx = 0;
		gc.gridy = 0;
		this.startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setServerPanel();
			}
		});

		this.loadButton = new JButton("Load Game");
		this.joinButton = new JButton("Join Game");

		this.joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setJoinPanel();
			}
		});

		gc.gridx = 0;
		gc.gridy = 1;
		this.mainPanel.add(startButton, gc);

		gc.gridx = 1;
		gc.gridy = 1;
		this.mainPanel.add(loadButton, gc);

		gc.gridx = 2;
		gc.gridy = 1;
		this.mainPanel.add(joinButton, gc);
	}

	private JTextField ip;
	private JTextField port;
	private JButton join;

	private void setJoinPanel() {
		mainPanel.setVisible(false);

		JPanel joinPanel = new JPanel();
		this.add(joinPanel);
		joinPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		joinPanel.add(new JLabel("IP Address"));
		JTextField ipInput = new JTextField(10);
		joinPanel.add(ipInput);
		joinPanel.add(new JLabel("Port Number"));
		JTextField portInput = new JTextField(10);
		joinPanel.add(portInput);
		join = new JButton("Join Game");



		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				joinGame();
			}

		});

		joinPanel.add(join);

		joinPanel.setVisible(true);
	}



	private void setServerPanel() {
		mainPanel.setVisible(false);

		JPanel serverInfo = new JPanel();
		serverInfo.setLayout(new GridBagLayout());

		try{
			JLabel head = new JLabel("SERVER RUNNING");
			head.setFont(Constants.HeaderFont);
			gc.gridx = 0; gc.gridy = 0;
			serverInfo.add(head, gc);
			gc.gridx = 0; gc.gridy = 1;
			serverInfo.add(new JLabel("IP Address : "+InetAddress.getLocalHost()), gc);
			gc.gridx = 0; gc.gridy = 2;
			serverInfo.add(new JLabel("Port : "+Server.PORT), gc);


		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		this.add(serverInfo);
		serverInfo.setVisible(true);


		try {
			new Server();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void joinGame() {
		new Client("130.195.6.168", 45612);
	}

	private void createClient() {
		///new Client();
	}

	public static void main(String[] gandalf) {
		new TEMPStartMenu();
	}
}
