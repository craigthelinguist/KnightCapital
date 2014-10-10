package world.icons;

import java.awt.image.BufferedImage;

import world.tiles.Tile;
import game.items.Item;

/**
 * An icon on the world that stores an item.
 * @author craigthelinguist
 *
 */
public class ItemIcon extends WorldIcon {

	public final Item item;
	public final String descr;

	public ItemIcon(String description, Item item){
		this.item = item;
		this.descr = description;
	}

	public boolean pickup(Party p){
		return p.addItem(item);
	}

	@Override
	public BufferedImage getImage() {
		return item.getImage();
	}

	@Override
	public BufferedImage getPortrait() {
		return item.getPortrait();
	}

	@Override
	public void setAnimationName(String name) {
		item.setAnimation(name);
	}

	@Override
	public String getAnimationName() {
		return item.getAnimationName();
	}

}
