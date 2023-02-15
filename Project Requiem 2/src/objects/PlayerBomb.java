package objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PlayerBomb extends Bullet {

//	Variables
	
	private double x, y;
	
	private double angle; // The literal angle
	private double angleX; // How much the bullet needs to move along the x axis to match angle
	private double angleY; // How much the bullet needs to move along the y axis to match angle
	
	private double speed;
	
	BufferedImage normal;
	
	AffineTransform at = new AffineTransform();
	
//	Constructor
	
	PlayerBomb(int x, int y, int w, int h, double speed){
		super(x, y, w, h);
		this.x = x;
		this.y = y;
		this.speed = speed;
		bufferImages();
	}
	
//	Methods
	
	public void bufferImages() {
//		Default
		try {
		    normal = ImageIO.read(new File("src/images/PlayerBomb.png"));
		} catch (IOException e) { 
		}
	}
	
	public void move() {
		x -= angleX * speed;
		y -= angleY * speed;
		
		super.setX((int)x);
		super.setY((int)y);
	}
	
	public void collision(ArrayList<BossBullet> bullets){
		for (int i = 0; i < bullets.size(); i++) {
			if (Math.pow((((bullets.get(i).getX()+(bullets.get(i).getW()/4))+((bullets.get(i).getW()/2)/2))-(getX()+(getW()/2))), 2) + Math.pow((((bullets.get(i).getY()+(bullets.get(i).getH()/4))+((bullets.get(i).getH()/2)/2))-((getY()+(getH()/2)))), 2) <= Math.pow((getW()/2)+((bullets.get(i).getW()/2)/2), 2)) {
				bullets.remove(i);
			}
		}
	}
	
	public void explosion(ArrayList<BossBullet> bullet) {
		
	}
	
//	Graphics
	
	public void draw(Graphics g, Graphics2D g2d) {
		at.rotate(Math.toRadians(angle), super.getX() + (super.getW()/2), super.getY() + (super.getH()/2));
		g2d.transform(at);

		g.drawImage(normal, super.getX(), super.getY(), super.getW(), super.getH(), null);
		
		try{
	        g2d.transform(at.createInverse());
	    }catch(NoninvertibleTransformException e){
	        //...
	    }
		at.setToIdentity(); 
	}
	
//	Getters
	
	public double getAngle() {
		return angle;
	}
	
	public double getAngleX() {
		return angleX;
	}
	
	public double getAngleY() {
		return angleY;
	}
	
	public double getSpeed() {
		return speed;
	}	
	
//	Setters
	
	public void setAngle(double a) {
		angle = a;
	}

	public void setAngleX(double aX) {
		angleX = aX;
	}

	public void setAngleY(double aY) {
		angleY = aY;
	}
	
	public void setSpeed(double s) {
		speed = s;
	}
	
}
