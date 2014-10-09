package game.items;

public class FloorItem extends Item{

	private Item item; //the item that this chest contains

	public FloorItem(String imgName, String description,Item item) {
		super(imgName, description);
		this.item = item;
	}

	public Item getItem() {
		return this.item;
	}

}
