/**
 * Program Name: Tile.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Aug. 30, 2021
 */
package tiles;

public abstract class Tile {
	
	// Variables
	private int movementValue; // How much movement it costs to move to this tile
	private int remainingMov; // What the character's remaining movement value would be if they moved to this tile
	
		// Movement
	private boolean valid; // Whether or not this tile can be moved to
	private boolean validPending; // Used to help prevent a recursive bug
	
		// Shows maximum attack range
	private boolean attackable; // Whether or not this tile can be attacked
	private boolean attackablePending; // Used to help prevent a recursive bug

		// Invisible search to see if character is within range to attack
	private boolean searched;
	private boolean searchedPending; // Used to prevent a recursive bug
	
	// Constructor
	public Tile() {
		movementValue = 0;
		valid = false;
		validPending = false;
		attackable = false;
		attackablePending = false;
		searched = false;
	}
	
	// Getters
	public int getMovementValue() {
		return this.movementValue;
	}
	
	public int getRemainingMov() {
		return this.remainingMov;
	}
	
	public boolean getValid() {
		return this.valid;
	}
	
	public boolean getValidPending() {
		return this.validPending;
	}
	
	public boolean getAttackable() {
		return this.attackable;
	}
	
	public boolean getAttackablePending() {
		return this.attackablePending;
	}
	
	public boolean getSearched() {
		return this.searched;
	}
	
	public boolean getSearchedPending() {
		return this.searchedPending;
	}
	
	// Setters
	public void setMovementValue(int mv) {
		this.movementValue = mv;
	}

	public void setRemainingMov(int remMv) {
		this.remainingMov = remMv;
	}
	
	public void setValid(boolean val) {
		this.valid = val;
	}
	
	public void setValidPending(boolean valPend) {
		this.validPending = valPend;
	}
	
	public void setAttackable(boolean att) {
		this.attackable = att;
	}
	
	public void setAttackablePending(boolean attPend) {
		this.attackablePending = attPend;
	}
	
	public void setSearched(boolean searched) {
		this.searched = searched;
	}
	
	public void setSearchedPending(boolean searchedPend) {
		this.searchedPending = searchedPend;
	}
	
}
 // end class