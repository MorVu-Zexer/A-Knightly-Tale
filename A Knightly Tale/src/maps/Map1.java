/**
 * Program Name: Map1.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Sep. 13, 2021
 */
package maps;

import characters.*;
import tiles.Floor;
import tiles.Wall;
import weapons.*;

public class Map1 extends Map {
	
	public Map1() {
		super(75, 40, 11, 13, 70);
		for (int i = 0; i < super.getWidth(); i++) {
			for (int j = 0; j < super.getHeight(); j++) {
				super.setTile(i, j, new Floor());
			}
		}
		
		super.setTile(3, 0, new Wall());
		super.setTile(7, 0, new Wall());
		super.setTile(3, 1, new Wall());
		super.setTile(7, 1, new Wall());
		super.setTile(3, 2, new Wall());
		super.setTile(7, 2, new Wall());
		
		super.setTile(3, 4, new Wall());
		super.setTile(7, 4, new Wall());
		super.setTile(3, 5, new Wall());
		super.setTile(7, 5, new Wall());
		super.setTile(3, 6, new Wall());
		super.setTile(7, 6, new Wall());

		super.setTile(0, 7, new Wall());
		super.setTile(1, 7, new Wall());
		super.setTile(2, 7, new Wall());
		super.setTile(3, 7, new Wall());
		
		super.setTile(7, 7, new Wall());
		super.setTile(8, 7, new Wall());
		super.setTile(9, 7, new Wall());
		super.setTile(10, 7, new Wall());
		
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
		super.setAllySpawnX(2);
		super.setAllySpawnY(3);
		
		// Ally 4
		super.setAllyType(new Good_Knight());
		super.setAllySpawnX(0);
		super.setAllySpawnY(2);
		
		super.setEnemyType(new Antlar_Commander());
		super.getEnemyType(super.getEnemyArray().size()-1).setWeapon(new Steel_Flail());
		super.setEnemySpawnX(5);
		super.setEnemySpawnY(12);
		
		super.setEnemyType(new Antlar_Warrior());
		super.getEnemyType(super.getEnemyArray().size()-1).setWeapon(new Iron_Sword());
		super.setEnemySpawnX(4);
		super.setEnemySpawnY(4);
		
		super.setEnemyType(new Antlar_Warrior());
		super.getEnemyType(super.getEnemyArray().size()-1).setWeapon(new Iron_Sword());
		super.setEnemySpawnX(9);
		super.setEnemySpawnY(9);
		
		super.setEnemyType(new Antlar_Warrior());
		super.getEnemyType(super.getEnemyArray().size()-1).setWeapon(new Iron_Sword());
		super.setEnemySpawnX(0);
		super.setEnemySpawnY(10);
		
		super.setEnemyType(new Antlar_Archer());
		super.getEnemyType(super.getEnemyArray().size()-1).setWeapon(new Wooden_Bow());
		super.setEnemySpawnX(6);
		super.setEnemySpawnY(6);
		
		super.setEnemyType(new Antlar_Archer());
		super.getEnemyType(super.getEnemyArray().size()-1).setWeapon(new Wooden_Bow());
		super.setEnemySpawnX(6);
		super.setEnemySpawnY(10);
	}
	
	// end main
}
 // end class