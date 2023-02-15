/**
 * Program Name: Character.java
 * Purpose: To act as a template for various characters
 * Coder: Jaden Duong
 * Date: Aug. 21, 2021
 */
package characters;

import java.awt.image.BufferedImage;

import weapons.Weapon;

public abstract class Character {
	
	// Variables
	
	private int x, y; // Character's position on screen
	private final int WIDTH = 15, HEIGHT = 16; // Character's dimensions on screen
	private int locationX, locationY; // Tile coordinates of the character
	private int locationXOrigin, locationYOrigin;
	private int size;
	
	// Statistics
	private String name;
	private int health, maxHealth; // Health - Character dies when this reaches 0
	private int strength; // Strength - How much damage this character can do with physical attacks
	private int magic; // Magic - How much damage this character can do with magical attacks
	private int defense; // Defense - How much damage is reduced from physical attacks
	private int resistance; // Resistance - How much damage is reduced from magical attacks
	private int movement; // Movement - How many spaces this unit can move from its origin point
	private int power; // Power - Sum of all stats (excluding HP and movement)
	
	// Character states
	private boolean ally; // Whether the character is allied or not
	private boolean selected; // Whether the character is currently being selected
	private boolean walking; // Whether the character currently has movement options
	private boolean attacking; // Whether or not the character is attacking
	private boolean moved; // Whether the character has already acted this turn
	private boolean unfocused; // Whether the character is unfocused; Occurs when another character is doing an action. 
	
	private BufferedImage sprite = null;
	
	private Weapon weapon;
	
	// Constructors
	
	Character() {
		this.x = 0;
		this.y = 0;
		this.size = 0;
		this.name = "";
		this.health = 0;
		this.maxHealth = 0;
		this.strength = 0;
		this.magic = 0;
		this.defense = 0;
		this.resistance = 0;
		this.movement = 0;
		this.power = this.strength + this.magic + this.defense + this.resistance;
		this.ally = false;
		this.moved = false;
	}
	
	Character(int x, int y, int size, String name, int hp, int str, int mag, int def, int res, int mov, boolean ally, boolean moved) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.name = name;
		this.health = hp;
		this.maxHealth = hp;
		this.strength = str;
		this.magic = mag;
		this.defense = def;
		this.resistance = res;
		this.movement = mov;
		this.power = this.strength + this.magic + this.defense + this.resistance;
		this.ally = ally;
		this.moved = moved;
	}
	
	abstract public void animateIdle(int t, final int f);
	
	abstract public void drawMovedUnfocused();
	
	abstract public void drawMoved();
	
	abstract public void animateUnfocused(int t, final int f);
	
	abstract public void animateWalking(int t, final int f);
	
	// Getters
	public BufferedImage getSprite() {
		return this.sprite;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getW() {
		return this.WIDTH;
	}
	
	public int getH() {
		return this.HEIGHT;
	}
	
	public int getCoordsX() {
		return this.locationX;
	}
	
	public int getCoordsY() {
		return this.locationY;
	}
	
	public int getCoordsXOrigin() {
		return this.locationXOrigin;
	}
	
	public int getCoordsYOrigin() {
		return this.locationYOrigin;
	}
	
	public int getSize() {
		return size;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public int getMaxHealth() {
		return this.maxHealth;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public int getMagic() {
		return this.magic;
	}
	
	public int getDefense() {
		return this.defense;
	}
	
	public int getResistance() {
		return this.resistance;
	}
	
	public int getMovement() {
		return this.movement;
	}
	
	public int getPower() {
		return this.power;
	}
	
	public boolean getAllied() {
		return this.ally;
	}
	
	public boolean getSelected() {
		return this.selected;
	}
	
	public boolean getWalking() {
		return this.walking;
	}
	
	public boolean getAttacking() {
		return this.attacking;
	}
	
	public boolean getMoved() {
		return this.moved;
	}
	
	public boolean getUnfocused() {
		return this.unfocused;
	}
	
	public Weapon getWeapon() {
		return this.weapon;
	}
	
	
	//Setters
	public void setSprite(BufferedImage img) {
		this.sprite = img;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setCoordsX(int x) {
		this.locationX = x;
	}
	
	public void setCoordsY(int y) {
		this.locationY = y;
	}
	
	public void setCoordsXOrigin(int x) {
		this.locationXOrigin = x;
	}
	
	public void setCoordsYOrigin(int y) {
		this.locationYOrigin = y;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void setHealth(int hp) {
		this.health = hp;
	}
	
	public void setMaxHealth(int hp) {
		this.maxHealth = hp;
	}
	
	public void setStrength(int str) {
		this.strength = str;
	}
	
	public void setMagic(int mag) {
		this.magic = mag;
	}
	
	public void setDefense(int def) {
		this.defense = def;
	}
	
	public void setResistance(int res) {
		this.resistance = res;
	}
	
	public void setMovement(int mov) {
		this.movement = mov;
	}
	
	public void setPower(int pow) {
		this.power = pow;
	}
	
	public void setAllied(boolean ally) {
		this.ally = ally;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public void setWalking(boolean walking) {
		this.walking = walking;
	}
	
	public void setAttacking(boolean att) {
		this.attacking = att;
	}
	
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
	public void setUnfocused(boolean unfocused) {
		this.unfocused = unfocused;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
}
 // end class