/**
 * Program Name: Map.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Aug. 30, 2021
 */
package maps;

import java.util.ArrayList;

import tiles.Tile;
import characters.Character;

public abstract class Map {
	
	// Variables
	
	private int x, y;
	private int width, height;
	private int tileSize;
	private Tile[][] map;
	
	private ArrayList<Integer> allySpawnXArrayList = new ArrayList<Integer>();
	private ArrayList<Integer> allySpawnYArrayList = new ArrayList<Integer>();

	private ArrayList<Character> allyType = new ArrayList<Character>();
	
	private ArrayList<Integer> enemySpawnXArrayList = new ArrayList<Integer>();
	private ArrayList<Integer> enemySpawnYArrayList = new ArrayList<Integer>();

	private ArrayList<Character> enemyType = new ArrayList<Character>();
	
	// Constructor
	
	public Map() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.tileSize = 0;
		this.map = new Tile[this.width][this.height];
	}
	
	public Map(int x, int y, int width, int height, int tileSize) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
		this.map = new Tile[this.width][this.height];
	}
	
	// Methods
	
	public void deselectTiles() {
		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				// Deselect all previously valid tiles
				this.getTile(i, j).setRemainingMov(0);
				this.getTile(i, j).setValid(false);
				this.getTile(i, j).setValidPending(false);
				this.getTile(i, j).setAttackable(false);
				this.getTile(i, j).setAttackablePending(false);
				this.getTile(i, j).setSearched(false);
				this.getTile(i, j).setSearchedPending(false);
			}
		}
	}
	
	public void validateTiles() {
		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				if (this.getTile(i, j).getValidPending()) {
					this.getTile(i, j).setValid(true);
				}
			}
		}
	}
	
	public void validateAttackTiles() {
		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				if (this.getTile(i, j).getAttackablePending()) {
					this.getTile(i, j).setAttackable(true);
				}
			}
		}
	}
	
	public void validateSearchTiles() {
		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				if (this.getTile(i, j).getSearchedPending()) {
					this.getTile(i, j).setSearched(true);
					this.getTile(i, j).setSearchedPending(false);
				}
			}
		}
	}
	
	// Getters
	
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getTileSize() {
		return this.tileSize;
	}
	
	public Tile[][] getMap() {
		return this.map;
	}
	
	public Tile getTile(int x, int y) {
		return this.map[x][y];
	}
	
	public int getAllySpawnX(int allyNo) {
		return this.allySpawnXArrayList.get(allyNo);
	}
	
	public int getAllySpawnY(int allyNo) {
		return this.allySpawnYArrayList.get(allyNo);
	}
	
	public Character getAllyType(int allyNo) {
		return this.allyType.get(allyNo);
	}
	
	public ArrayList<Character> getAllyArray() {
		return this.allyType;
	}
	
	public int getEnemySpawnX(int enemyNo) {
		return this.enemySpawnXArrayList.get(enemyNo);
	}
	
	public int getEnemySpawnY(int enemyNo) {
		return this.enemySpawnYArrayList.get(enemyNo);
	}
	
	public Character getEnemyType(int enemyNo) {
		return this.enemyType.get(enemyNo);
	}
	
	public ArrayList<Character> getEnemyArray() {
		return this.enemyType;
	}
	
	// Setters
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public void setMap(Tile[][] map) {
		this.map = map;
	}
	
	public void setTile(int x, int y, Tile tile) {
		this.map[x][y] = tile;
	}
	
	public void setAllySpawnX(int x) {
		this.allySpawnXArrayList.add(x);
	}
	
	public void setAllySpawnY(int y) {
		this.allySpawnYArrayList.add(y);
	}
	
	public void setAllyType(Character ally) {
		this.allyType.add(ally);
	}
	
	public void setEnemySpawnX(int x) {
		this.enemySpawnXArrayList.add(x);
	}
	
	public void setEnemySpawnY(int y) {
		this.enemySpawnYArrayList.add(y);
	}
	
	public void setEnemyType(Character enemy) {
		this.enemyType.add(enemy);
	}
}
 // end class