package GUI.town;

import game.items.Item;
import game.units.Creature;
import game.units.Hero;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.world.CustomButton;
import tools.Constants;
import tools.ImageLoader;
import world.icons.Party;
import world.towns.City;
import controllers.TownController;

/**
 * TownExchangePanel lets you exchange units and items between the visiting party and garrisoned party.
 * @author craigthelinguist, Ewan Moshi
 *
 */
public class TownExchangePanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener{

	BufferedImage backgroundImage;

	// constants
	private static final int PORTRAIT_WD = Constants.PORTRAIT_DIMENSIONS.width;
	private static final int PORTRAIT_HT = Constants.PORTRAIT_DIMENSIONS.height;
	private static final int HEALTH_BAR_WD = PORTRAIT_WD;
	private static final int HEALTH_BAR_HT = 15;
	private static final Color TRANSPARENT = new Color(0,0,0,0);

	// controller
	private TownController controller;
	private TownGui gui;

	// static components
	private JPanel visitorPanel;
	private JPanel garrisonPanel;

	// dynamic components
	private TownPartyPanel partyGarrison;
	private TownPartyPanel partyVisitors;
	private TownItemPanel itemsGarrison;
	private TownItemPanel itemsVisitors;

	// buttons
	private CustomButton buttonLeave;
	private CustomButton buttonTrain;

	// thing being dragged
	private Point draggedPoint;
	private JPanel draggedPanel;

	protected TownExchangePanel(final TownController controller, final TownGui gui){
		this.controller = controller;
		this.gui = gui;

		JPanel wrapperPanel = new JPanel(){

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
			    g.drawImage(backgroundImage = ImageLoader.load(Constants.GUI_FILEPATH + "dialogBackground.png"), 0, 0, getWidth(), getHeight(), this);
			    TownExchangePanel.this.redraw(g);
			}

		};

		City city = controller.getCity();
		partyVisitors = new TownPartyPanel(this,city);
		partyGarrison = new TownPartyPanel(this,city);
		itemsVisitors = new TownItemPanel(this,controller.getVisitors(),city);
		itemsGarrison = new TownItemPanel(this,controller.getGarrison(),city);

		BoxLayout layout = new BoxLayout(wrapperPanel,BoxLayout.Y_AXIS);
		wrapperPanel.setLayout(layout);

		/*Load the three images for exiting button*/
		BufferedImage leaveGameDefault = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "exit.png");
		BufferedImage leaveGamePressed = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "exitClicked.png");
		BufferedImage leaveGameHover = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "exitHover.png");
		buttonLeave = new CustomButton(leaveGameDefault, leaveGamePressed, leaveGameHover);
		buttonLeave.addActionListener(this);
		JPanel visitorButtons = new JPanel();
		JLabel exitLabel = new JLabel("Exit City");
		exitLabel.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
		exitLabel.setForeground(new Color(225,179,55));
		visitorButtons.setLayout(new BorderLayout());
		visitorButtons.setOpaque(false);
		visitorButtons.add(buttonLeave, BorderLayout.SOUTH);
		visitorButtons.add(exitLabel, BorderLayout.CENTER);

		/*Load the three images for train units button*/
		BufferedImage trainUnitsDefault = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "trainUnits.png");
		BufferedImage trainUnitsPressed = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "trainUnitsClicked.png");
		BufferedImage trainUnitsHover = ImageLoader.load(Constants.GUI_TOWN_BUTTONS + "trainUnitsHover.png");
		buttonTrain = new CustomButton(trainUnitsDefault, trainUnitsPressed, trainUnitsHover);
		buttonTrain.addActionListener(this);
		JLabel trainUnits = new JLabel("Train Units");
		trainUnits.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 20));
		trainUnits.setForeground(new Color(225,179,55));
		JPanel garrisonButtons = new JPanel();
		garrisonButtons.setLayout(new BorderLayout());
		garrisonButtons.setOpaque(false);
		garrisonButtons.add(buttonTrain, BorderLayout.SOUTH);
		garrisonButtons.add(trainUnits, BorderLayout.CENTER);

		// set up button listeners
		buttonTrain.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new TrainingDialog(controller,gui);
			}

		});

		// parties
		JPanel parties = new JPanel();
		parties.setOpaque(false);
		parties.add(visitorButtons);
		parties.add(partyVisitors);
		parties.add(partyGarrison);
		parties.add(garrisonButtons);
		wrapperPanel.add(parties);

		// items
		JPanel items = new JPanel();
		items.setOpaque(false);
		items.add(itemsVisitors);
		items.add(itemsGarrison);
		wrapperPanel.add(items);

		StoragePanel storagePanel = new StoragePanel();
		//Add the storage panel

		wrapperPanel.setOpaque(false);
		wrapperPanel.addMouseListener(this);
		wrapperPanel.addMouseMotionListener(this);

		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(wrapperPanel);
		this.add(storagePanel);
	}

	/**
	 * Return the panel whose element you're dragging around.
	 * @return: a JPanel, or null if nothing is being dragged.
	 */
	public JPanel getDraggedPanel() {
		return this.draggedPanel;
	}

	/**
	 * Return the index that you're currently dragging around.
	 * @return: index being dragged around, or null if none.
	 */
	protected Point getDraggedPoint(){
		return this.draggedPoint;
	}

	/**
	 * Reset the panel and point being dragged around.
	 * Repaint the gui.
	 */
	private void resetDragging(){
		this.draggedPanel = null;
		this.draggedPoint = null;
		this.repaint();
	}

	protected void redraw(Graphics g){
		partyGarrison.repaint();
		partyVisitors.repaint();

		if (draggedPanel == null || draggedPoint == null) return;
		if (draggedPanel instanceof TownPartyPanel){
			TownPartyPanel tpp = (TownPartyPanel)draggedPanel;
			Creature member = getParty(tpp).getMember(draggedPoint.x, draggedPoint.y);
			if (member == null) return;
			Point mousePoint = MouseInfo.getPointerInfo().getLocation();
			BufferedImage portrait = member.getPortrait();
			Point pos = this.getLocationOnScreen();
			mousePoint.x = mousePoint.x - pos.x - portrait.getWidth()/2;
			mousePoint.y = mousePoint.y - pos.y - portrait.getHeight()/2;
			g.drawImage(portrait,mousePoint.x,mousePoint.y,null);
		}
		else if (draggedPanel instanceof TownItemPanel){
			TownItemPanel tip = (TownItemPanel)draggedPanel;
			Item item = this.getParty(tip).getItem(draggedPoint.x, draggedPoint.y);
			if (item == null) return;
			Point mousePoint = MouseInfo.getPointerInfo().getLocation();
			BufferedImage image = item.getImage();
			Point pos = this.getLocationOnScreen();
			mousePoint.x = mousePoint.x - pos.x - image.getWidth()/2;
			mousePoint.y = mousePoint.y - pos.y - image.getHeight()/2;
			g.drawImage(image,mousePoint.x,mousePoint.y,null);
		}

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (draggedPoint != null){
			repaint();
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		resetDragging();
	}

	@Override
	public void mousePressed(MouseEvent e) {

		Point click = new Point(e.getX(),e.getY());
		Point location = this.getLocationOnScreen();

		Point pointVisitorsParty = partyVisitors.getFromPoint(click,location);
		if (pointVisitorsParty != null){
			this.draggedPoint = pointVisitorsParty;
			this.draggedPanel = this.partyVisitors;
			return;
		}

		Point pointGarrisonParty = partyGarrison.getFromPoint(click,location);
		if (pointGarrisonParty != null){
			this.draggedPoint = pointGarrisonParty;
			this.draggedPanel = this.partyGarrison;
			return;
		}

		Point pointVisitorsItems = itemsVisitors.getFromPoint(click,location);
		if (pointVisitorsItems != null){
			this.draggedPoint = pointVisitorsItems;
			this.draggedPanel = this.itemsVisitors;
			return;
		}

		Point pointGarrisonItems = itemsGarrison.getFromPoint(click, location);
		if (pointGarrisonItems != null){
			this.draggedPoint = pointGarrisonItems;
			this.draggedPanel = this.itemsGarrison;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (this.draggedPanel instanceof TownPartyPanel){
			Point eventClick = new Point(e.getX(),e.getY());
			Point position = this.getLocationOnScreen();
			Point ptVisitors = partyVisitors.getFromPoint(eventClick, position);
			Point ptGarrison = partyGarrison.getFromPoint(eventClick, position);
			Point release = ptVisitors == null ? ptGarrison : ptVisitors;
			if (release != null){
				TownPartyPanel releasePanel = (release == ptGarrison) ? partyGarrison : partyVisitors;
				TownPartyPanel draggedPanel = (TownPartyPanel)(this.draggedPanel);
				reorderUnits(draggedPanel,draggedPoint,releasePanel,release);
			}
		}
		else if (this.draggedPanel instanceof TownItemPanel){
			Point eventClick = new Point(e.getX(),e.getY());
			Point position = this.getLocationOnScreen();
			Point ptVisitors = itemsVisitors.getFromPoint(eventClick, position);
			Point ptGarrison = itemsGarrison.getFromPoint(eventClick, position);
			Point release = ptVisitors == null ? ptGarrison : ptVisitors;
			if (release != null){
				TownItemPanel releasePanel = (release == ptGarrison) ? itemsGarrison : itemsVisitors;
				TownItemPanel draggedPanel = (TownItemPanel)(this.draggedPanel);
				reorderItems(draggedPanel,draggedPoint,releasePanel,release);
			}
		}

		this.resetDragging();
	}

	/**
	 * Take the items from point1 in the party at panel1, and from point2 in the party at panel2, and
	 * swap their positions. A hero cannot leave its own party and if you try to do so it will not be moved.
	 * @param panel1: first panel
	 * @param point1: index of item in the party at panel1
	 * @param panel2: second panel
	 * @param point2: index of item in the party at panel2
	 */
	private void reorderItems(TownItemPanel panel1, Point point1, TownItemPanel panel2, Point point2) {

		Party p1 = this.getParty(panel1);
		Item i1 = p1.getItem(point1.x, point1.y);
		Party p2 = this.getParty(panel2);
		Item i2 = p2.getItem(point2.x, point2.y);

		p1.setItem(i2, point1.x, point1.y);
		p2.setItem(i1, point2.x, point2.y);

	}

	/**
	 * Take the creatures from point1 in the party at panel1, and from point2 in the party at panel2, and
	 * swap their positions. A hero cannot leave its own party and if you try to do so it will not be moved.
	 * @param panel1: first panel
	 * @param point1: index of creature in the party at panel1
	 * @param panel2: second panel
	 * @param point2: index of creature in the party at panel2
	 */
	private void reorderUnits(TownPartyPanel panel1, Point point1, TownPartyPanel panel2, Point point2){

		Party p1 = this.getParty(panel1);
		Creature c1 = p1.getMember(point1.x, point1.y);
		Party p2 = this.getParty(panel2);
		Creature c2 = p2.getMember(point2.x, point2.y);

		// heroes aren't allowed to leave their party
		if ((c1 instanceof Hero || c2 instanceof Hero) && (panel1 != panel2)){
			return;
		}

		// not allowed to drag units from garrison to visitors if visitors doesn't have a hero
		if (panel1 == this.partyGarrison && panel2 == this.partyVisitors){
			if (p2.getHero() == null){
				return;
			}
		}

		p1.setMember(c2, point1.x, point1.y);
		p2.setMember(c1, point2.x, point2.y);

	}

	/**
	 * Dead methods. Needed for mouse listener interfaces.
	 */
	public void mouseMoved(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == buttonLeave) {
			controller.buttonPressed("exit city");
		}
	}

	public Party getParty(JPanel panel) {
		if (panel == this.partyVisitors || panel == this.itemsVisitors) return controller.getVisitors();
		else return controller.getGarrison();
	}

}
