package menu;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Program Name: Interaction_Menu.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Jul 13, 2022
 */

public class Interaction_Menu {

	// Variables
	private int x, y;
	
	private boolean interactable = true;
	private boolean attackable = false;
	
	ArrayList<Section> section = new ArrayList<Section>();
	
	private boolean exists;
	
	// Constructor
	
	public Interaction_Menu() {
		this.x = 0;
		this.y = 0;
		this.exists = false;
	}
	
	public Interaction_Menu(int x, int y, boolean exists) {
		this.x = x;
		this.y = y;
		this.exists = exists;
	}
	
	// Methods
	
	public void drawInteractionMenu(int x, int y, Graphics g) {
		section.clear();
		
		// Interact
		if (getInteractable()) {
			section.add(new Section_Interact(x, y));
			section.get(section.size()-1).drawSection(x, y + (section.get(section.size()-1).getHeight() * (section.size()-1)), g);
		}
		
		// Attack
		if (getAttackable()) {
			section.add(new Section_Attack(x, y));
			section.get(section.size()-1).drawSection(x, y + (section.get(section.size()-1).getHeight() * (section.size()-1)), g);
		}
		
		// Inventory
		section.add(new Section_Inventory(x, y));
		section.get(section.size()-1).drawSection(x, y + (section.get(section.size()-1).getHeight() * (section.size()-1)), g);
		
		// Wait
		section.add(new Section_Wait(x, y));
		section.get(section.size()-1).drawSection(x, y + (section.get(section.size()-1).getHeight() * (section.size()-1)), g);
	}
	
	public void addInteract() {
		setInteractable(true);
	}
	
	public void removeInteractable() {
		setInteractable(false);
	}
	
	public void addAttack() {
		setAttackable(true);
	}
	
	public void removeAttack() {
		setAttackable(false);
	}
	
	// Getters
	
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public boolean getInteractable() {
		return this.interactable;
	}
	
	public boolean getAttackable() {
		return this.attackable;
	}
	
	public boolean getExists() {
		return this.exists;
	}
	
	public ArrayList<Section> getSectionArray() {
		return this.section;
	}
	
	// Setters
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setInteractable(boolean interact) {
		this.interactable = interact;
	}
	
	public void setAttackable(boolean att) {
		this.attackable = att;
	}
	
	public void setExists(boolean exists) {
		this.exists = exists;
	}
	
}
 // end class