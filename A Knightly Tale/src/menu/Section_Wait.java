/**
 * Program Name: Section_Wait.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Jul 13, 2022
 */
package menu;

import java.awt.Color;
import java.util.ArrayList;

import characters.Character;
import maps.Map;

public class Section_Wait extends Section {

	// Variables
	
	// Constructor
	
	public Section_Wait() {
		super(0, 0, Color.BLACK, "Wait");
	}
	
	public Section_Wait(int x, int y) {
		super(x, y, Color.BLACK, "Wait");
	}
	
	// Methods
	
	public void activate(ArrayList<Character> allies, ArrayList<Character> foes, Map map) {
		for (Character c : allies) {
			if (c.getSelected()) {
				c.setMoved(true);
				c.setCoordsXOrigin(c.getCoordsX());
				c.setCoordsYOrigin(c.getCoordsY());
				map.deselectTiles();
			}
		}
	}
}
 // end class