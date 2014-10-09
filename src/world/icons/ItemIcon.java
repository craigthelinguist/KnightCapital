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

	private final Item item;

	public ItemIcon(Item item){
		this.item = item;
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
