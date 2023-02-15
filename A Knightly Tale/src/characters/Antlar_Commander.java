/**
 * Program Name: Antlar_Commander.java
 * Purpose: PUT SOMETHING USEFUL HERE!
 * Coder: Jaden Duong
 * Date: Jul 13, 2022
 */
package characters;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Antlar_Commander extends Character {

	// Variables
	private BufferedImage spriteSheet = null;
	
	public Antlar_Commander() {
		// Base stats
		super(0, 0, 0, "Antlar Commander", 20, 10, 3, 7, 5, 0, false, false);
		bufferImages();
		super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*0, super.getW(), super.getH())); // Idle 1
	}
	
	public Antlar_Commander(int x, int y, int size, int hp, int str, int mag, int def, int res, int mov, boolean ally) {
		super(x, y, size, "Antlar Commander", hp, str, mag, def, res, mov, false, false);
		bufferImages();
		super.setSprite(spriteSheet.getSubimage(super.getW()*0, super.getH()*0, super.getW(), super.getH())); // Idle 1
	}
	
	private void bufferImages() {
		try {
			spriteSheet = ImageIO.read(new File("src/images/characters/antlar_commander.png"));
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