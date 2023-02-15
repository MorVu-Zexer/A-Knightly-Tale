/**
 * Program Name: Silent_Knight.java
 * Purpose: To store information about Silent Knight
 * Coder: Jaden Duong
 * Date: Aug. 21, 2021
 */
package characters;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import weapons.*;

public class Silent_Knight extends Character {
	
	// Variables
	private BufferedImage spriteSheet = null;

	public Silent_Knight() {
		// Base stats
		super(50, 50, 50, "Silent Knight", 25, 7, 3, 12, 8, 4, true, false);
		super.setWeapon(new Pavise());
		bufferImages();
		super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*0, super.getW(), super.getH())); // Idle 1
	}
	
	public Silent_Knight(BufferedImage img, int x, int y, int size, int hp, int str, int mag, int def, int res, int mov, boolean ally) {
		super(x, y, size, "Silent Knight", hp, str, mag, def, res, mov, true, false);
		super.setWeapon(new Pavise());
		bufferImages();
		super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*0, super.getW(), super.getH())); // Idle 1
	}
	
	private void bufferImages() {
		try {
			spriteSheet = ImageIO.read(new File("src/images/characters/silent_knight.png"));
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