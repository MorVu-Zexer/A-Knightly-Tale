/**
 * Program Name: Section.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Jul 13, 2022
 */
package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import characters.Character;
import maps.Map;

public abstract class Section {

	// Variables
	private int x, y;
	private final int WIDTH = 120, HEIGHT = 25;
	private Color color;
	
	private String section;
	
	// Constructor
	
	public Section() {
		this.x = 0;
		this.y = 0;
		this.section = "";
	}
	
	public Section(int x, int y, Color color, String section) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.section = section;
	}
	
	// Methods
	
	public void drawSection(int x, int y, Graphics g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, 23));
		g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, HEIGHT);
		g.setColor(color);
		g.drawString(section, x + 3, y + 21);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, WIDTH, HEIGHT);
	}
	
	public abstract void activate(ArrayList<Character> allies, ArrayList<Character> foes, Map map);
	
	// Getters
	
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.WIDTH;
	}
	
	public int getHeight() {
		return this.HEIGHT;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public String getSection() {
		return this.section;
	}
	
	// Setters
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setSection(String sect) {
		this.section = sect;
	}
	
}
 // end class