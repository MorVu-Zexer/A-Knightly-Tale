/**
 * Program Name: Arrow.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Jul 15, 2022
 */
package assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import characters.Character;

public class Arrow extends Asset {

	// Variables
	
	private BufferedImage spriteSheet;
	
	// Constructor
	
	public Arrow(int x, int y) {
		super(x, y, 12, 11);
		bufferImage(); 
		super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*0, super.getW(), super.getH())); // Image 1
	}
	
	public Arrow(int x, int y, int w, int h) {
		super(x, y, w, h);
		bufferImage();
		super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*0, super.getW(), super.getH())); // Image 1
	}
	
	// Methods
	
	public void bufferImage() {
		try {
			spriteSheet = ImageIO.read(new File("src/images/assets/arrow.png"));
		} catch (IOException e) {
			System.out.println("Arrow image not found");
		}
	}
	
	public void animate(int t, final int f) {
		bufferImage();
		
		if (t < f * 1) {
			super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*0, super.getW(), super.getH())); // Image 1
		} else if (t < f * 2) {
			super.setSprite(spriteSheet.getSubimage(super.getW()*1, super.getH()*0, super.getW(), super.getH())); // Image 2
		}
	}
	
}
 // end class