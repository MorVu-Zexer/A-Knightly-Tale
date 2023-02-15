package objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BossBullet extends Bullet {

//	Variables
	
	private double x, y;
	
	private double angle; // The literal angle
	private double angleX; // How much the bullet needs to move along the x axis to match angle
	private double angleY; // How much the bullet needs to move along the y axis to match angle
	
	private double speed;
	
	private String type;
	
	private BufferedImage normal;
	private BufferedImage inverse;
	private BufferedImage orb;
	private BufferedImage[] fireball = new BufferedImage[4];
	private BufferedImage missile;
	private BufferedImage ringed;
	private BufferedImage star;
	
	
	private int animationTimer = 0;
	private int frame = 0;
	
	private boolean graze;
	
	AffineTransform at = new AffineTransform();
	
//	Constructor
	
	BossBullet(int x, int y, int w, int h, double speed, String type) {
		super(x, y, w, h);
		this.x = x;
		this.y = y;
		this.type = type;
		this.speed = speed;
		graze = false;
		bufferImages();
	}
	
//	Methods
	
	public void bufferImages(){
//		Default
		try {
		    normal = ImageIO.read(new File("src/images/BossBulletDefault.png"));
		} catch (IOException e) { 
		}
		
//		Inverse
		try {
		    inverse = ImageIO.read(new File("src/images/BossBullet1.png"));
		} catch (IOException e) { 
		}
		
//		Orb
		try {
		    orb = ImageIO.read(new File("src/images/BossBulletOrb.png"));
		} catch (IOException e) { 
		}
		
//		Fireball
		for (int i = 0; i < fireball.length; i++) {
			try {
			    fireball[i] = ImageIO.read(new File("src/images/SpellBullet" + i + ".png"));
			} catch (IOException e) { 
			}
		}
		
//		Missile
		try {
		    missile = ImageIO.read(new File("src/images/BossBulletMissile.png"));
		} catch (IOException e) { 
		}
		
//		Ringed
		try {
		    ringed = ImageIO.read(new File("src/images/BossBulletRinged.png"));
		} catch (IOException e) { 
		}
		
//		Star
		try {
		    star = ImageIO.read(new File("src/images/BossBulletStar.png"));
		} catch (IOException e) { 
		}
	}
	
	public void draw(Graphics g, Graphics2D g2d) {
		at.rotate(Math.toRadians(angle), super.getX() + (super.getW()/2), super.getY() + (super.getH()/2));
		g2d.transform(at);
		
//		This switch determines what type of bullet is being drawn, then draws it.
		switch (type) {
			case "Inverse":
				g.drawImage(inverse, super.getX(), super.getY(), super.getW(), super.getH(), null);
				break;
			case "Orb":
				g.drawImage(orb, super.getX(), super.getY(), super.getW(), super.getH(), null);
				break;
			case "Fireball":
				g.drawImage(fireball[frame], super.getX(), super.getY(), super.getW(), super.getH(), null);
//			Animates the fireball
				animationTimer++;
				if (animationTimer == 5) {
					frame++;
					if (frame >= 4) {
						frame = 0;
					}
					animationTimer = 0;
				}
				break;
			case "Missile":
				g.drawImage(missile, super.getX(), super.getY(), super.getW(), super.getH(), null);
				break;
			case "Ringed":
				g.drawImage(ringed, super.getX(), super.getY(), super.getW(), super.getH(), null);
				break;
			case "Star":
				g.drawImage(star, super.getX(), super.getY(), super.getW(), super.getH(), null);
				break;
			default :
				g.drawImage(normal, super.getX(), super.getY(), super.getW(), super.getH(), null);
				break;
		}
		
		try{
	        g2d.transform(at.createInverse());
	    }catch(NoninvertibleTransformException e){
	        //...
	    }

		at.setToIdentity(); 

	}
	
	public void move() {
		x -= angleX * speed;
		y -= angleY * speed;
		
		super.setX((int)x);
		super.setY((int)y);
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
	
	public String getType() {
		return type;
	}
	
	public boolean getGraze() {
		return graze;
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
	
	public void setType(String t) {
		type = t;
	}
	
	public void setGraze(boolean g) {
		graze = g;
	}
}
