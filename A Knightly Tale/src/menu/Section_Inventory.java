/**
 * Program Name: Section_Inventory.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Jul 14, 2022
 */
package menu;

import java.awt.Color;
import java.util.ArrayList;

import characters.Character;
import maps.Map;

public class Section_Inventory extends Section {

	// Variables
	
	// Constructor
	
	public Section_Inventory() {
		super(0, 0, Color.BLACK, "Inventory");
	}
	
	public Section_Inventory(int x, int y) {
		super(x, y, Color.BLACK, "Inventory");
	}
	
	// Methods

	@Override
	public void activate(ArrayList<Character> allies, ArrayList<Character> foes, Map map) {
		System.out.println("What's this?");
	}
}
 // end class