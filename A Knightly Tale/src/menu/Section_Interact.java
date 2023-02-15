/**
 * Program Name: Section_Interact.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Jul 14, 2022
 */
package menu;

import java.awt.Color;
import java.util.ArrayList;

import characters.Character;
import maps.Map;

public class Section_Interact extends Section {
	
	// Variables
	
	// Constructor
	
	public Section_Interact() {
		super(0, 0, new Color(72, 217, 18), "Interact");
	}
	
	public Section_Interact(int x, int y) {
		super(x, y, new Color(72, 217, 18), "Interact");
	}
	
	// Methods

	public void activate(ArrayList<Character> allies, ArrayList<Character> foes, Map map) {
		System.out.println("S M A S H");
	}
}
 // end class