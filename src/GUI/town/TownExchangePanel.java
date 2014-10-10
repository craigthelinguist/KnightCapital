package GUI.town;

import game.units.Creature;
import game.units.Hero;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import tools.Constants;
import world.icons.Party;

import controllers.TownController;

public class TownExchangePanel extends JPanel implements MouseListener, MouseMotionListener{

	// constants
	private static final int PORTRAIT_WD = Constants.PORTRAIT_DIMENSIONS.width;
	private static final int PORTRAIT_HT = Constants.PORTRAIT_DIMENSIONS.height;
	private static final int HEALTH_BAR_WD = PORTRAIT_WD;
	private static final int HEALTH_BAR_HT = 15;
	
	// controller
	private TownController controller;
	
	// static components
	private JPanel visitorPanel;
	private JPanel garrisonPanel;
	
	// dynamic components
	private TownPartyPanel partyGarrison;
	private TownPartyPanel partyVisitors;
	private JPanel itemsGarrison;
	private JPanel itemsVisitors;
	
	// buttons
	private JButton buttonLeave;
	private JButton trainUnit;
	
	// thing being dragged
	private Point draggedPoint;
	private JPanel draggedPanel;
	
	protected TownExchangePanel(TownController controller){
		this.controller = controller;
		
		partyGarrison = new TownPartyPanel(this,controller.getGarrison());
		partyVisitors = new TownPartyPanel(this,controller.getVisitors());
		
		int WIDTH = partyGarrison.getPreferredSize().width + partyVisitors.getPreferredSize().width;
		int HEIGHT = partyGarrison.getPreferredSize().height + partyVisitors.getPreferredSize().height;
		

		this.add(partyVisitors);
		this.add(partyGarrison);
		
		// setup visitor panel
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	protected Point getDraggedPoint(){
		return this.draggedPoint;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		partyGarrison.repaint();
		partyVisitors.repaint();
		
		if (draggedPanel != null && draggedPoint != null){
			TownPartyPanel tpp = (TownPartyPanel)draggedPanel;
			Creature member = tpp.getParty().getMember(draggedPoint.x, draggedPoint.y);
			if (member == null) return;
			Point mousePoint = MouseInfo.getPointerInfo().getLocation();
			BufferedImage portrait = member.getPortrait();
			Point pos = this.getLocationOnScreen();
			mousePoint.x = mousePoint.x - pos.x - portrait.getWidth()/2;
			mousePoint.y = mousePoint.y - pos.y - portrait.getHeight()/2;
			g.drawImage(portrait,mousePoint.x,mousePoint.y,null);
		}
		
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (draggedPoint != null){
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		resetDragging();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		Point click = new Point(e.getX(),e.getY());
		
		Point pointVisitorsParty = partyVisitors.getFromPoint(click,this.getLocationOnScreen());
		if (pointVisitorsParty != null){
			this.draggedPoint = pointVisitorsParty;
			this.draggedPanel = this.partyVisitors;
			return;
		}
		
		Point pointGarrisonParty = partyGarrison.getFromPoint(click,this.getLocationOnScreen());
		if (pointGarrisonParty != null){
			this.draggedPoint = pointGarrisonParty;
			this.draggedPanel = this.partyGarrison;
			return;
		}
		
	}

	private void resetDragging(){
		this.draggedPanel = null;
		this.draggedPoint = null;
		this.repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
		if (this.draggedPanel instanceof TownPartyPanel){
			Point eventClick = new Point(e.getX(),e.getY());
			Point position = this.getLocationOnScreen();
			Point ptVisitors = partyVisitors.getFromPoint(eventClick, position);
			Point ptGarrison = partyGarrison.getFromPoint(eventClick, position);
			Point release = ptVisitors == null ? ptGarrison : ptVisitors;
			if (release == null) return;
			TownPartyPanel releasePanel = (release == ptGarrison) ? partyGarrison : partyVisitors;
			TownPartyPanel draggedPanel = (TownPartyPanel)(this.draggedPanel);
			reorderUnits(draggedPanel,draggedPoint,releasePanel,release);
		}
		
		this.resetDragging();
	}
	
	private void reorderUnits(TownPartyPanel panel1, Point point1, TownPartyPanel panel2, Point point2){
		
		Party p1 = panel1.getParty();
		Creature c1 = p1.getMember(point1.x, point1.y);
		Party p2 = panel2.getParty();
		Creature c2 = p2.getMember(point2.x, point2.y);
		
		// heroes aren't allowed to leave their party
		if ((c1 instanceof Hero || c2 instanceof Hero) && (panel1 != panel2)){
			return;
		}
		
		p1.setMember(c2, point1.x, point1.y);
		p2.setMember(c1, point2.x, point2.y);
		
	}

	public JPanel getDraggedPanel() {
		return this.draggedPanel;
	}

}
