package world.towns;

import game.items.Item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;

import player.Player;
import renderer.Animation;
import renderer.AnimationMap;
import renderer.Camera;
import tools.Constants;
import tools.ImageLoader;
import tools.Log;
import world.icons.Party;
import world.tiles.CityTile;

/**
 * There are cities on the world. They train units. What units a city can train
 * depends on what buildings the city has. You can construct buildings in a
 * city. On the overworld, a city is represented by a collection of tiles.
 *
 * Let's assume on the overworld all cities are 3x3.
 *
 * @author craigthelinguist
 */
public class City {

	// on the overworld, a city is a collection of 3x3 CityTiles.
	public static final int WIDTH = 3;
	public static final int CITY_INCOME = 100;
	private final CityTile[][] tiles;

	// who owns this city
	private Player owner;

	// buildings inside this city
	private Set<Building> buildings;

	// party inside this city
	private Party garrison;

	// party staying in this city
	private Party visitors;

	// items being stored inside this city
	private LinkedList<Item> items;

	// image representing this city
	private AnimationMap animations;

	private String name;
	private String imageName;


	public City(String name, String imageName, Player player, CityTile[][] overworldTiles){

		// set up owner and fields storing details about this city
		owner = player;
		buildings = new HashSet<>();
		visitors = garrison = null;
		items = new LinkedList<>();
		this.imageName = imageName;
		this.name = name;

		// set up tiles; set tiles so that they have a reference to this.
		tiles = overworldTiles;
		if (overworldTiles != null){
			if (overworldTiles.length != WIDTH) Log.print("Error! City must be " + WIDTH + "x" + WIDTH + "tiles!");
			for (int i = 0; i < WIDTH; i++){
				if (overworldTiles[i].length != WIDTH) Log.print("Error! City must be " + WIDTH + "x" + WIDTH + "tiles!");
				for (int j = 0; j < WIDTH; j++){
					tiles[i][j] = overworldTiles[i][j];
					tiles[i][j].setCity(this);
				}
			}
		}

		// set up images for this city
		animations = new AnimationMap();
		this.animations.addDirectedImages(Constants.CITIES, "basic", player.getColour());
		animations.setImage("north");
		String portrait_filepath = Constants.PORTRAITS + "city_" + imageName;
		animations.addImage("portrait", portrait_filepath, ImageLoader.load(portrait_filepath));
	}

	/**
	 * Attempt to add the specified building, or do nothing if it is already in
	 * this city.
	 *
	 * @param b
	 *            : building to add.
	 * @return: true if the building was added, false otherwise.
	 */
	public boolean addBuilding(Building b) {
		return buildings.add(b);
	}

	/**
	 * Check if the given player owns this city.
	 *
	 * @param player
	 *            : see if this person owns the city.
	 * @return true if the person owns the city.
	 */
	public boolean ownedBy(Player player) {
		return owner == player;
	}

	/**
	 * Set the city to be owned by the specified player.
	 *
	 * @param player
	 *            : player who now owns the city.
	 */
	public void setPlayer(Player player) {
		owner = player;
	}

	/**
	 * Draw this city on the given graphics object at the point (x,y)
	 */
	public void draw(Graphics g, int x, int y) {
		g.drawImage(animations.getImage(), x, y, null);
	}

	/**
	 * Return the tile that would be visually leftmost from the perspective of the given camera.
	 * @param camera: viewing perspective
	 * @return: the leftmost tile.
	 */
	public CityTile getLeftmostTile(Camera camera){
		int orientation = camera.getOrientation();
		if (orientation == Camera.NORTH) return tiles[0][WIDTH-1];
		else if (orientation == Camera.WEST) return tiles[0][0];
		else if (orientation == Camera.SOUTH) return tiles[WIDTH-1][0];
		else if (orientation == Camera.EAST) return tiles[WIDTH-1][WIDTH-1];
		else throw new RuntimeException("unknown camera orientation: " + orientation);
	}

	/**
	 * Gets the entrance tile for the city
	 * @return CityTile
	 */
	public CityTile getEntryTile(){
		return tiles[1][2];
	}

	/**
	 * Change who owns this city. Updates all animations for this city.
	 * @param owner: person who now owns this city.
	 */
	public void changeOwner(Player owner){
		this.owner = owner;
		this.animations.updateColours(owner.getColour());
	}

	/**
	 * Return true if there's nobody in this city (visitors or garrison).
	 * @return: true if no-one in city, false if there are visitors or garrison in city.
	 */
	public boolean isEmpty() {
		boolean garrison = (this.garrison == null || this.garrison.isEmpty());
		boolean visitors = (this.visitors == null || this.visitors.isEmpty());
		return garrison || visitors;
	}

	/**
	 * Return true if there are visitors in this city.
	 * @return
	 */
	public boolean hasVisitors() {
		return this.visitors != null && !this.visitors.isEmpty();
	}

	/**
	 * Return the amount of gold this city provides each turn.
	 * @return
	 */
	public int getIncome() {
		return CITY_INCOME;
	}

	/**
	 * Set the current image for this city.
	 * @param name: name of image to set.
	 */
	public void setAnimationName(String name){
		animations.setImage(name);
	}

	/**
	 * Get name of current image for this city.
	 * @return string
	 */
	public String getAnimationName(){
		return animations.getName();
	}

	/**
	 * Get the city visitors.
	 * @return: party visiting the city.
	 */
	public Party getVisitors() {
		return visitors;
	}

	/**
	 * Get the city garrison.
	 * @return: party stationed in city garrison.
	 */
	public Party getGarrison(){
		return garrison;
	}

	/**
	 * Set the city garrison.
	 * @param party: party now garrisoned in city.
	 */
	public void setGarrison(Party party) {
		this.garrison = party;
	}


	/**
	 * Set the visitors in this city.
	 * @param party: party now in this city.
	 */
	public void setVisitors(Party party) {
		this.visitors = party;
	}


	/**
	 * Return the image representing this city's portrait.
	 * @return: buffered image
	 */
	public BufferedImage getPortrait() {
		return animations.getPortrait();
	}

	/**
	 * Get the player that owns this city.
	 * @return: player/
	 */
	public Player getOwner() {
		return this.owner;
	}

	/**
	 * Get the name of this city.
	 * @return: string
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the base image name for this city.
	 * @return: string
	 */
	public String getImageName(){
		return this.imageName;
	}

	public int getImageHeight() {
		return animations.getImage().getHeight();
	}

	public void rotate(boolean clockwise) {
		this.animations.rotate(clockwise);
	}
	
}
