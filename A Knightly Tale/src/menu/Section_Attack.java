/**
 * Program Name: Section_Attack.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Jul 14, 2022
 */
package menu;

import java.awt.Color;
import java.util.ArrayList;

import characters.Character;
import maps.Map;

public class Section_Attack extends Section {
	
	// Variables
	
	// Constructor
	
	public Section_Attack() {
		 super(0, 0, new Color(200, 0, 0), "Attack");
	}
	
	public Section_Attack(int x, int y) {
		super(x, y, new Color(200, 0, 0), "Attack");
	}

	@Override
	public void activate(ArrayList<Character> allies, ArrayList<Character> foes, Map map) {
		for (Character c : allies) {
			if (!c.getSelected()) {
				c.setUnfocused(true);
			} else {
				c.setAttacking(true);
			}
		}				
		for (Character c : foes) {
			if (!map.getTile(c.getCoordsX(), c.getCoordsY()).getSearched()) {
				c.setUnfocused(true);
			}
		}
	}
	// end main
}
 // end class