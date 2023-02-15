package objects;

import java.awt.Graphics;

public abstract class Bullet {

//	Variables
	
	private Point coordinates;
	private int h, w;
	
//	Constructor
	
	Bullet(int x, int y, int w, int h){
		coordinates = new Point(x, y);
		this.w = w;
		this.h = h;
	}
	
//	Methods
	
//	Getters
	
	public int getX() {
		return coordinates.getX();
	}
	
	public int getY() {
		return coordinates.getY();
	}
	
	public int getW() {
		return w;
	}
	
	public int getH() {
		return h;
	}
	
//	Setters
	
	public void setX(int x) {
		coordinates.setX(x);
	}
	
	public void setY(int y) {
		coordinates.setY(y);
	}
	
	public void setW(int w) {
		this.w = w;
	}
	
	public void setH(int h) {
		this.h = h;
	}
}
