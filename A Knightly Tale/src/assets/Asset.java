/**
 * Program Name: Asset.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Jul 15, 2022
 */
package assets;

import java.awt.image.BufferedImage;

public abstract class Asset {

	// Variables
	
	private int x, y, w, h;
	
	private BufferedImage sprite = null;
	
	// Constructor

	public Asset() {
		this.x = 0;
		this.y = 0;
		this.w = 0;
		this.h = 0;
	}
	
	public Asset(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public abstract void animate(int t, final int f);
	
	// Getters
	
	public BufferedImage getSprite() {
		return this.sprite;
	}
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getW() {
		return this.w;
	}

	public int getH() {
		return this.h;
	}
	
	// Setters
	
	public void setSprite(BufferedImage img) {
		this.sprite = img;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setW(int w) {
		this.w = w;
	}

	public void setH(int h) {
		this.h = h;
	}
}
 // end class