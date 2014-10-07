package world.icons;

import world.tiles.Tile;
import game.items.Item;

/**
 * An icon on the world that stores an item.
 * @author craigthelinguist
 *
 */
public class ItemIcon {

	private final Item item;
	
	public ItemIcon(String imgName, Item item){
		this.item = item;
	}
	
	public boolean pickup(Party p){
		return p.addItem(item);
	}
	
}
