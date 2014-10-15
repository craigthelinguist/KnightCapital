package world.icons;

import java.awt.image.BufferedImage;

import tools.Constants;
import tools.ImageLoader;
import world.tiles.Tile;
import game.items.Item;

/**
 * An icon on the world that stores an item.
 * This is displayed as a chest in the game world.
 * Any party in the world should be able to stand on this icon and gain its contents.
 * @author craigthelinguist
 *
 */
public class ItemIcon extends WorldIcon {

	public final Item item; // item contained by the icon
	private final BufferedImage image;

	/**
	 * Contruct a new Item Icon
	 * @param item to contain within icon
	 */
	public ItemIcon(Item item){
		this.item = item;
		this.image = ImageLoader.load(Constants.ITEMS+"itemChest");
	}

	/**
	 * Give the contents of this item icon to a suppilied party
	 * @param p party to give item to
	 * @return item.
	 */
	public boolean pickup(Party p){
		return p.addItem(item);
	}

	@Override
	public BufferedImage getImage() {
		return image;
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
