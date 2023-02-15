/**
 * Program Name: map2.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Jul 9, 2022
 */
package maps;

import tiles.Floor;
import tiles.Wall;

public class Map2 extends Map {
	// end main
	
	public Map2() {
		super(40, 40, 16, 18, 52);
		for (int i = 0; i < super.getWidth(); i++) {
			for (int j = 0; j < super.getHeight(); j++) {
				super.setTile(i, j, new Floor());
			}
		}
	}
}
 // end class