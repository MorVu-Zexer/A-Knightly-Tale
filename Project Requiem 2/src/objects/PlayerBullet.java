package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PlayerBullet extends Bullet {

	BufferedImage bullet;
	
	PlayerBullet(int x, int y, int w, int h) {
		super(x, y, w, h);
		bufferImages();
	}
	
	public void bufferImages(){
		try {
		    bullet = ImageIO.read(new File("src/images/PlayerBullet.png"));
		} catch (IOException e) { 
		}
	}
	
	public void draw(Graphics g) {
//		g.setColor(Color.BLUE);
//		g.drawRect(super.getX(), super.getY(), super.getW(), super.getH());
		g.drawImage(bullet, super.getX(), super.getY(), super.getW(), super.getH(), null);
	}
	
	public void move() {
		super.setY(super.getY() - 15);
	}

}
