package networking;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Neal Hartley Runnable thread implemented by the client in order to
 *         listen to responses form the server port. Will hold functionality to
 *         deal with different types of incoming messages and how they will
 *         affect the clients current board state.
 */
public class ClientMessagingProtocol implements Runnable {


	//lets set up a chat window.
	private JFrame frame;
	private JPanel panel;
	private JTextArea text;
	private JTextField textIn;



	private static DataInputStream in;
	private boolean clean = true;
	private static DataOutputStream out;

	private boolean changed = false;

	/**
	 * needs to be initialised with a DataInputStream from an already
	 * initialised port Stream is constantly checked for incoming messages.
	 *
	 * @param in
	 */
	public ClientMessagingProtocol(DataInputStream in, final DataOutputStream out) {
		this.in = in;
		this.out = out;

		frame = new JFrame("Messenger");
		panel = new JPanel();
		text = new JTextArea("talk some trash:");
		
		textIn = new JTextField("enter input here!");
		

		text.setSize(150, 150);

		KeyListener listen = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){

					try {
						out.writeUTF(textIn.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		};

		textIn.addKeyListener(listen);
		

		frame.add(panel);
		panel.add(text);
		panel.add(textIn);

		frame.setSize(200, 200);


		frame.setVisible(true);



	}

	/**
	 * consistently checks for incoming data from socket, deals with it
	 * accordingly.
	 */
	@Override
	public void run() {

		String message;

		while (clean) {
			System.out.println("clientProtocol running");

			if (changed) {

				try {
					out.writeUTF("a change has occured");
					changed = false;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}




			try {
				// currently checks for incoming string messages, functionality
				// for
				// reading byte stream for board changes will be added.
				message = in.readUTF();
				System.out.println("global message: " + message);
				text.setText(message);

			} catch (EOFException e) {
				this.clean = false;
			}

			catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	public Boolean getStatus() {
		return clean;

	}

	public void notifyChange() {
		System.out.println("change has been notified!");
		this.changed = true;

	}


	public JTextField getInput(){

		return textIn;
	}
}
