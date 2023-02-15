package objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpellCircle {

//	Variables
	
	Point coordinates;
	int w, h;
	boolean clockwise;
	
	private double spellCircleRotation1; // Rotation that Spell Circle 1 is based on
	private double spellCircleRotation2; // Rotation that Spell Circle 2 is based on
	
	private BufferedImage circle1;
	private BufferedImage circle2;
	
	AffineTransform at = new AffineTransform();
	
//	Constructor
	
	SpellCircle(int x, int y, int w, int h, boolean clockwise){
		coordinates = new Point(x, y);
		this.w = w;
		this.h = h;
		this.clockwise = clockwise;
		bufferImages();
	}
	
//	Methods
	
	public void bufferImages() {
		try {
			circle1 = ImageIO.read(new File("src/images/MagicCircle1.png"));
			circle2 = ImageIO.read(new File("src/images/MagicCircle2.png"));
		} catch (IOException e) {
		}
	}

	public void drawCircle1(int x, int y, int w, int h, boolean clockwise,  Graphics g, Graphics2D g2d) {
		at.rotate(Math.toRadians(spellCircleRotation1), getX() + (getW()/2), getY() + (getH()/2));
		g2d.transform(at);
		g.drawImage(circle1, x, y, w, h, null);
		try{
	        g2d.transform(at.createInverse());
	    }catch(NoninvertibleTransformException e){
	        //...
	    }
		at.setToIdentity(); 
		
		if (clockwise)
			spellCircleRotation1++;
		else
			spellCircleRotation1--;
	}
	
	public void drawCircle2(int x, int y, int w, int h, boolean clockwise, Graphics g, Graphics2D g2d) {
		at.rotate(Math.toRadians(spellCircleRotation2), getX() + (getW()/2), getY() + (getH()/2));
		g2d.transform(at);
		g.drawImage(circle2, x, y, w, h, null);
		try{
	        g2d.transform(at.createInverse());
	    }catch(NoninvertibleTransformException e){
	        //...
	    }
		at.setToIdentity();
		
		if (clockwise)
			spellCircleRotation2++;
		else
			spellCircleRotation2--;
	}
	
	public void orbit(int x, int y, double angle, double h) {
//		angleX = getX() + (Math.sin(angle)*h);
//		angleY = getY() + (Math.cos(angle)*h);
		
		double moveX = x + (Math.sin(angle)*h);
		double moveY = y + (Math.cos(angle)*h);
		
		setX((int)moveX);
		setY((int)moveY);
	}
	
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
	
	public boolean getClockwise() {
		return clockwise;
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
	
	public void setClockwise(boolean c) {
		clockwise = c;
	}
	
}
