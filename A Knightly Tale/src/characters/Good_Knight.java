/**
 * Program Name: Good_Knight.java
 * Purpose: To store information about Good Knight
 * Coder: Jaden Duong
 * Date: Aug. 24, 2021
 */
package characters;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import weapons.*;

public class Good_Knight extends Character {

	// Variables
	private BufferedImage spriteSheet = null;
	
	public Good_Knight() {
		// Base stats
		super(260, 50, 50, "Good Knight", 20, 6, 10, 9, 5, 5, true, false);
		super.setWeapon(new Slumber());
		bufferImages();
		super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*0, super.getW(), super.getH())); // Idle 1
	}
	
	public Good_Knight(int x, int y, int size, int hp, int str, int mag, int def, int res, int mov, boolean ally) {
		super(x, y, size, "Good Knight", hp, str, mag, def, res, mov, true, false);
		super.setWeapon(new Slumber());
		bufferImages();
		super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*0, super.getW(), super.getH())); // Idle 1
	}
	
	private void bufferImages() {
		try {
			spriteSheet = ImageIO.read(new File("src/images/characters/good_knight.png"));
		} catch (IOException e) {
			System.out.println(super.getName() + "\'s Image not found");
		}
	}
	
	public void animateIdle(int t, final int f) {
		bufferImages();
		
		if (t < f * 1) {
			super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*0, super.getW(), super.getH())); // Idle 1
		} else if (t < f * 2) {
			super.setSprite(spriteSheet.getSubimage(super.getW()*1, super.getH()*0, super.getW(), super.getH())); // Idle 2
		}
	}
	
	public void drawMovedUnfocused() {
		bufferImages();
		
		super.setSprite(spriteSheet.getSubimage(super.getW()*1, super.getH()*2, super.getW(), super.getH())); // Moved & Unfocused
	}
	
	public void drawMoved() {
		bufferImages();

		super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*2, super.getW(), super.getH())); // Moved
	}
	
	public void animateUnfocused(int t, final int f) {
		bufferImages();
		
		if (t < f * 1) {
			super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*3, super.getW(), super.getH())); // Unfocused 1
		} else if (t < f * 2) {
			super.setSprite(spriteSheet.getSubimage(super.getW()*1, super.getH()*3, super.getW(), super.getH())); // Unfocused 2
		}
	}
	
	public void animateWalking(int t, final int f) {
		bufferImages();
		
		if (t < f * 1) {
			super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*1, super.getW(), super.getH())); // Walk 1
		} else if (t < f * 2) {
			super.setSprite(spriteSheet.getSubimage(super.getW()*1, super.getH()*1, super.getW(), super.getH())); // Walk 2
		}
	}
}
 // end class