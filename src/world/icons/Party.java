package world.icons;

import game.items.Item;
import game.items.PassiveItem;
import game.units.AttackType;
import game.units.Creature;
import game.units.Hero;
import game.units.Stat;
import game.units.Unit;
import game.units.UnitStats;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import player.Player;

/**
 * Party Icon
 *
 * Party Icon is used to represent the current position of a party within the world screen.
 *
 * @author craigthelinguist
 *
 */
public class Party extends WorldIcon implements Iterable<Creature>{

	public static final int PARTY_ROWS = 2;
	public static final int PARTY_COLS = 3;
	public static final int INVENTORY_ROWS = 2;
	public static final int INVENTORY_COLS = 3;
	public static final int INVENTORY_SIZE = INVENTORY_ROWS*INVENTORY_COLS;

	private Player owner;
	private int movementPoints;

	private Item[][] inventory;
	private Creature[][] members;
	private Hero hero;

	/**
	 * Create a new empty array for creatures
	 * @return empty creature array
	 */
	public static Creature[][] newEmptyPartyArray() {
		return new Creature[Party.PARTY_ROWS][Party.PARTY_COLS];
	}

	/**
	 * Creates a new empty array for items, aka an empty inventory
	 * @return empty inventory array
	 */
	public static Item[][] newEmptyInventory() {
		return new Item[Party.INVENTORY_ROWS][Party.INVENTORY_COLS];
	}

	/**
	 * Creates and returns an empty party, including empty creature array
	 * @param player who owns this
	 * @return new empty party
	 */
	public static Party newEmptyParty(Player player){
		return new Party(null,player,newEmptyPartyArray());
	}

	/**
	 * Contructs a new Party
	 * @param hero member of party
	 * @param player who owns this party
	 * @param members : creature[][] of units
	 */
	// TODO: should be instantiated with a hero; no party is without one
	public Party(Hero hero, Player player, Creature[][] members) {
		this.hero = hero;
		owner = player;
		this.members = members;
		if (members == null) this.members = Party.newEmptyPartyArray();
		inventory = Party.newEmptyInventory();
	}

	/**
	 * Switch the order of two party members.
	 * @param p1: position of first party member.
	 * @param p2: position of second party member.
	 */
	public void swap(Point p1, Point p2){
		Creature c1 = members[p1.y][p1.x];
		members[p1.y][p1.x] = members[p2.y][p2.x];
		members[p2.y][p2.x] = c1;
	}

	/**
	 * Return the party member at the specified position.
	 * @param x: column
	 * @param y: row
	 */
	public Creature getMember(int x, int y){
		return members[y][x];
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
		movementPoints = hero.get(Stat.MOVEMENT);
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
				if (inventory[y][x] == null){
					inventory[y][x] = item;
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
		if(inventory[y][x] != null) {
			return inventory[y][x];
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

	/**
	 * Get portrait image of this party
	 */
	@Override
	public BufferedImage getPortrait(){
		return hero.getPortrait();
	}

	/**
	 * Get animation name
	 */
	@Override
	public void setAnimationName(String name) {
		hero.setAnimation(name);
	}

	@Override
	public String getAnimationName() {
		return hero.getAnimationName();
	}

	public void setMember(Creature c, int x, int y){
		this.members[y][x] = c;
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
	 * -myles
	 * @return true if all members are deceased
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
		inventory[y][x] = i1;
	}

	@Override
	public Iterator<Creature> iterator() {
		return new PartyIterator();
	}

	/**
	 * Iterate through party
	 */
	private class PartyIterator implements Iterator<Creature>{

		int row = 0;
		int col = 0;

		@Override
		public boolean hasNext() {
			int y = row; int x = col;

			for ( ; y < Party.PARTY_ROWS; y++){
				for ( ; x < Party.PARTY_COLS; x++){
					if (members[y][x] != null) return true;
				}
				x = 0;
			}

			return false;

		}

		@Override
		public Creature next() {

			int y = row; int x = col;

			for ( ; y < Party.PARTY_ROWS; y++){
				for ( ; x < Party.PARTY_COLS; x++){

					if (members[y][x] != null){
						row = y;
						col = x+1;
						if (col == Party.PARTY_COLS){
							col=0;
							row++;
						}

						return members[y][x];
					}
				}
				x=0;
			}

			return null;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * Returns a 2-d array of the Creatures inside this party.
	 * @return Creature[][]
	 */
	public Creature[][] getMembers() {
		return members;
	}

	/**
	 * Return true if this party has space.
	 * @return: true if this party has space left, false if full.
	 */
	public boolean hasSpace() {
		for (int i = 0; i < members.length; i++){
			for (int j = 0; j < members[i].length; j++){
				if (members[i][j] == null) return true;
			}
		}
		return false;
	}

	/**
	 * Attempt to add the unit to the first empty slot in the party.
	 * @param unit: unit to add
	 * @return: true if successfully added, false otherwise.
	 */
	public boolean addUnit(Unit unit) {
		for (int i = 0; i < members.length; i++){
			for (int j = 0; j < members[i].length; j++){
				if (members[i][j] == null){
					members[i][j] = unit;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Return true if this party is empty.
	 * @return
	 */
	@SuppressWarnings("unused")
	public boolean isEmpty() {
		for (Creature creature : this){
			return false;
		}
		return true;
	}

	/**
	 * Return the healthiness of this party as a %.
	 * @return an int
	 */
	public int healthiness() {

		double sum = 0;
		int count = 0;

		for (Creature c : this){
			sum += c.healthiness();
			count++;
		}

		return (int) ((sum/count)*100);
	}

	/**
	 * Create a quick creature array for quick testing os partys
	 * - myles
	 **/
	public static Creature[][] quickKnightParty(Player p) {
		Creature[][] creatures = new Creature[Party.PARTY_ROWS][Party.PARTY_COLS];
		System.out.println("[Party] Creating a full knight array. This is a test feature, if you see this is final code, lord help us");
		for( int x = 0; x < Party.PARTY_ROWS; x++) {
			for( int y = 0; y < Party.PARTY_COLS; y++) {
				System.out.println("[Party](quickKnightParty) Adding knight in position "+x+", "+y);
				creatures[x][y] = new Unit("Knight", "knight", p, new UnitStats(10, 10, 1, 10, AttackType.MELEE));
			}
		}

		return creatures;
	}

	/**
	 * Return true if there is no more space in this party's inventory.
	 * @return
	 */
	public boolean hasFullInventory() {
		for (int i = 0; i < inventory.length; i++){
			for (int j = 0; j < inventory[i].length; j++){
				if (inventory[i][j] == null) return false;
			}
		}
		return true;
	}

}
