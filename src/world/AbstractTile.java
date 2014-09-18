package world;

import java.awt.image.BufferedImage;


/**
 * Represents one cell on the World. Different implementations of Tile might have different rules (e.g.:
 * parties can stand on it, tile belongs to a city, so on).
 */
public abstract class AbstractTile {

	protected BufferedImage image;
	
}
