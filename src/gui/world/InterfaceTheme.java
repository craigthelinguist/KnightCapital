package gui.world;

import java.awt.image.BufferedImage;

public class InterfaceTheme {

	private final BufferedImage minimap;
	private final BufferedImage infopanel;
	
	public InterfaceTheme(BufferedImage minimap, BufferedImage infopanel){
		this.minimap = minimap;
		this.infopanel = infopanel;
	}
	
	public BufferedImage getMinimap(){
		return minimap;
	}
	
	public BufferedImage getInfopanel(){
		return infopanel;
	}
	
}
