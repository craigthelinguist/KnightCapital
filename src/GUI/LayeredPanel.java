package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;

import tools.Constants;
import tools.ImageLoader;
import world.tiles.Tile;


/**
 * This class extends teh JLayeredPane which allows components to be layered on top 
 * of each other. The two components on this layered panel is the MainPanelMaster (responsible for 
 * holding all the other panels) and the MainPanelBorder which is on top of the MainPanelMaster to create
 * the effect of having a border around the MainPanelMaster
 * 
 * @author Ewan Moshi
 *
 */
public class LayeredPanel extends JLayeredPane{
	
	private MainFrame mainFrame;
	
	/* Declare all the panels on this panel */
	private MainPanelMaster masterPanel;
	private MainPanelBorder panelBorder;
	
	private CustomButton inventoryButton;
	
	public LayeredPanel(MainFrame frame) {
		this.setPreferredSize(new Dimension(frame.getWidth(),200));  
		mainFrame = frame;
				
		panelBorder = new MainPanelBorder(frame);
		panelBorder.setBounds(0,0,frame.getWidth(),200);
		this.add(panelBorder, new Integer(1),0);
		
		/*Initialize the inventory panel and place it bottom right*/
		masterPanel = new MainPanelMaster(frame);
		masterPanel.setBounds(0,0,frame.getWidth(),200);
		this.add(masterPanel,new Integer(0), 0);

	}
	
    @Override
    public void doLayout() {
    	/*Resize all the components on this layeredPane*/
        synchronized(getTreeLock()) {
            int w = getWidth();
            for(Component c : getComponents()) {
                c.setBounds(0, 0, w, 200);
            }
        }
    }

    /**
     * Update the info displayed in playerInfoPanel.
     * @param tile: tile whose info should be displayed.
     */
	public void updateInfo(Tile tile) {
		masterPanel.updateInfo(tile);
	}

}
