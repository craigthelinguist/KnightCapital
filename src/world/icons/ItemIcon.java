package world.icons;

import world.tiles.Tile;
import game.items.Item;

/**
 * An icon on the world that stores an item.
 * @author craigthelinguist
 *
 */
public class ItemIcon extends WorldIcon {

	private final Item item;
	
	public ItemIcon(String imgName, Item item){
		super(imgName);
		this.item = item;
	}
	
	public boolean pickup(Party p){
		return p.addItem(item);
	}
	
}
