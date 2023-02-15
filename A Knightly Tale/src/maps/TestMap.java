/**
 * Program Name: TestMap.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Aug. 30, 2021
 */
package maps;

import characters.*;
import tiles.*;
import weapons.*;

public class TestMap extends Map {
	
	// Constructor
	public TestMap() {
		super(40, 40, 12, 12, 70);
		for (int i = 0; i < super.getWidth(); i++) {
			for (int j = 0; j < super.getHeight(); j++) {
				super.setTile(i, j, new Plains());
			}
		}
		
		super.setTile(4, 2, new Forest());
		super.setTile(4, 3, new Forest());
		super.setTile(5, 3, new Forest());
		super.setTile(10, 6, new Forest());
		super.setTile(5, 4, new Water());
		
		// Ally 1
		super.setAllyType(new Silent_Knight());
		super.setAllySpawnX(9);
		super.setAllySpawnY(2);
		
		// Ally 2
		super.setAllyType(new Holy_Knight());
		super.setAllySpawnX(10);
		super.setAllySpawnY(4);
		
		// Ally 3
		super.setAllyType(new Black_Knight());
		super.setAllySpawnX(10);
		super.setAllySpawnY(6);
		
		// Ally 4
		super.setAllyType(new Good_Knight());
		super.setAllySpawnX(2);
		super.setAllySpawnY(9);
		
		// Enemy 1
		super.setEnemyType(new Prisoner_Mage());
		super.getEnemyType(super.getEnemyArray().size()-1).setWeapon(new Spark());
		super.setEnemySpawnX(5);
		super.setEnemySpawnY(10);
		
		// Enemy 2
		super.setEnemyType(new Prisoner_Warrior());
		super.getEnemyType(super.getEnemyArray().size()-1).setWeapon(new Shank());
		super.setEnemySpawnX(6);
		super.setEnemySpawnY(4);
	}
	
}
 // end class