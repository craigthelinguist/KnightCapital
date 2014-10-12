package world.icons;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;

import game.effects.Buff;
import game.items.Item;
import game.items.PassiveItem;
import game.units.Creature;
import game.units.Hero;
import game.units.Stat;
import game.units.Unit;
import player.Player;

/**
 *
 * @author craigthelinguist
 *
 */
public class Party extends WorldIcon implements Iterable<Creature>{

	public static final int PARTY_ROWS = 2;
	public static final int PARTY_COLS = 3;
	public static final int INVENTORY_ROWS = 2;
	public static final int INVENTORY_COLS = 3;

	private Player owner;
	private int movementPoints;

	private Item[][] inventory;
	private Creature[][] members;
	private Hero hero;

	// TODO: should be instantiated with a hero; no party is without one
	public Party(Hero hero, Player player, Creature[][] members) {
		this.hero = hero;
		owner = player;
		this.members = members;
		if (members == null) this.members = Party.newEmptyParty();
		inventory = new Item[INVENTORY_COLS][INVENTORY_ROWS];
	}

	/**
	 * Switch the order of two party members.
	 * @param p1: position of first party member.
	 * @param p2: position of second party member.
	 */
	public void swap(Point p1, Point p2){
		Creature c1 = members[p1.x][p1.y];
		members[p1.x][p1.y] = members[p2.x][p2.y];
		members[p2.x][p2.y] = c1;
	}

	/**
	 * Return the party member at the specified position.
	 * @param x: column
	 * @param y: row
	 */
	public Creature getMember(int x, int y){
		return members[x][y];
	}

	/**
	 * Return true if this Party is owned by the specified player.
	 * @param p: player you suspect owns this party.
	 * @return true if the player owns this party.
	 */
	public boolean ownedBy(Player p){
		return owner == p;
	}

	/**
	 * Return the number of movement points this party has left.
	 * @return number of movement points remaining.
	 */
	public int getMovePoints(){
		return movementPoints;
	}

	/**
	 * Add to this parties movement points. A party's movement points can go over their maximum.s
	 * @param amount
	 */
	public void addMovementPoints(int amount) {
		if (amount < 0) return;
		this.movementPoints += amount;
	}

	/**
	 * Decrease the remaining movement points by the specified amount. A party's movement points cannot go
	 * below zero.
	 * @param amount: amount to decrease by.
	 */
	public void decreaseMovePoints(int amount){
		if (amount < 0) return;
		this.movementPoints = Math.max(0,movementPoints-amount);
	}

	/**
	 * Set the hero leading the party
	 * @param newLeader: new hero to lead the party
	 */
	public void setLeader(Hero newLeader){
		hero = newLeader;
	}

	/**
	 * Set the player who owns this party.
	 * @param player: new player to own this party.
	 */
	public void setOwner(Player player){
		owner = player;
	}

	/**
	 * Return the hero leading this party.
	 * @return: a hero.
	 */
	public Hero getHero(){
		return this.hero;
	}

	/**
	 * Return the player that owns this party.
	 * @return: a player
	 */
	public Player getOwner(){
		return this.owner;
	}

	/**
	 * Return true if the specified heroes leads this party.
	 * @param h: hero to check
	 * @return: true if the hero leads this party.
	 */
	public boolean ledBy(Hero h){
		return hero == h;
	}

	/**
	 * 'Refresh' this party. Reset its movement points. All members should regenerate their
	 * hit points and lose all temporary buffs for thie day.
	 */
	public void refresh(){
		movementPoints = hero.getMovePoints();
		for (int i = 0; i < members.length; i++){
			for (int j = 0; j < members[i].length; j++){
				Creature member = members[i][j];
				if (member == null) continue;
				member.removeTempBuffs();
				member.regenHealth();
			}
		}
	}

	/**
	 * Attempt to add the item to this party's inventory if there is space.
	 * @param item: item to add.
	 * @return:
	 */
	public boolean addItem(Item item) {
		for (int y = 0; y < INVENTORY_ROWS; y++){
			for (int x = 0; x < INVENTORY_COLS; x++){
				if (inventory[x][y] == null){
					inventory[x][y] = item;
					if (item instanceof PassiveItem){
						PassiveItem passive = (PassiveItem)item;
						passive.applyEffectsTo(this);
					}
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return the item member at the specified position.
	 * @param x: column
	 * @param y: row
	 */
	public Item getItem(int x, int y){
		if(inventory[x][y] != null) {
			return inventory[x][y];
		}
		else return null;
	}

	public Item[][] getInventory() {
		return this.inventory;
	}

	/**
	 * Return the image needed to represent this party on the mpa.
	 */
	@Override
	public BufferedImage getImage() {
		return hero.getImage();
	}

	@Override
	public BufferedImage getPortrait(){
		return hero.getPortrait();
	}

	@Override
	public void setAnimationName(String name) {
		hero.setAnimation(name);
	}

	@Override
	public String getAnimationName() {
		return hero.getAnimationName();
	}

	public void setMember(Creature c, int x, int y){
		this.members[x][y] = c;
	}

	public static Creature[][] newEmptyParty() {
		return new Creature[Party.PARTY_COLS][Party.PARTY_ROWS];
	}

	public static Item[][] newEmptyInventory() {
		return new Item[Party.INVENTORY_COLS][Party.INVENTORY_ROWS];
	}

	/**
	 * Print the inventory of the player. For testing purposes only.
	 */
	public void printInventory(){
		for (int i = 0; i < inventory.length; i++){
			for (int j = 0; j < inventory[i].length; j++){
				Item itm = inventory[i][j];
				if (itm != null) System.out.println(itm.getName());
				else System.out.println("null");
			}
		}
	}

	/**
	 * Check to see if this party has any memebers still living in it.
	 * @return
	 */
	public boolean isDead() {

		// For each member
		for (int i = 0; i < members.length; i++){
			for (int j = 0; j < members[i].length; j++){
				// if any member is not dead return false
				Creature creature = members[i][j];


				if(creature != null){
					if (!members[i][j].isDead()) return false;
				}
			}
		}
		// else return true
		return true;
	}


	/**
	 * Return the number of members in this party.
	 * @return: an int.
	 */
	public int size(){
		int count = 0;
		for (int i = 0; i < members.length; i++){
			for (int j = 0; i < members[i].length; j++){
				if (members[i][j] != null) count++;
			}
		}
		return count;
	}

	/**
	 * Should not be used to add items! Only use to swap items.
	 * @param i1
	 * @param x
	 * @param y
	 */
	public void setItem(Item i1, int x, int y) {
		inventory[x][y] = i1;
	}

	@Override
	public Iterator<Creature> iterator() {
		return new PartyIterator();
	}

	private class PartyIterator implements Iterator<Creature>{

		int row = 0;
		int col = 0;
		
		@Override
		public boolean hasNext() {
			for (int y = row ; y < Party.PARTY_ROWS; y++){
				for (int x = col ; x < Party.PARTY_COLS; x++){
					if (members[x][y] != null) return true;
				}
			}
			return false;
		}

		@Override
		public Creature next() {
			int newRow = row;
			int newCol = col;
			for (int y = newRow; y < Party.PARTY_ROWS; y++){
				for (int x = newCol; x < Party.PARTY_COLS; x++){
					if (members[x][y] != null){
						row = newRow;
						col = newCol;
						col++;
						if (col == Party.PARTY_COLS){
							col = 0;
							row++;
						}
						return members[x][y];
					}
				}
			}
			return null;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}
