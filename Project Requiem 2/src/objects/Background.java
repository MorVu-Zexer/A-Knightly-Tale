package objects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Background {

//	Variables
	
	private double backgroundY = 0;
	private double background2Y = backgroundY-1200;
	private double starsY = 0;
	private double stars2Y = starsY-1200;
	
	private Image background;
	private Image stars;
	
	private BufferedImage background2;
	private BufferedImage stars2;
	
	private File music00;
	private AudioInputStream music01;
	private Clip music;
	
//	Constructor
	
	public Background(){
		bufferImages();
		background = background2;
		stars = stars2;
		assignClips();
	}
	
//	Methods
	
	public void bufferImages() {
		try {
		    background2 = ImageIO.read(new File("src/images/Background.jpg"));
		} catch (IOException e) { 
		}
		try {
		    stars2 = ImageIO.read(new File("src/images/Stars.png"));
		} catch (IOException e) { 
		}
	}
	
	public void assignClips() {
		try {
			music00 = new File("src/sounds/magusnight.wav");
			music01 = AudioSystem.getAudioInputStream(music00);
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			music = AudioSystem.getClip();
			music.open(music01);
		} catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	Graphics
	
	public void draw(Graphics g) {
		drawBackground(g);
		drawStars(g);
	}
	
	public void drawBackground(Graphics g) {
		g.drawImage(background, 15, (int)backgroundY, 700, 1200, null);
		g.drawImage(background, 15, (int)background2Y, 700, 1200, null);
		backgroundY+=0.6;
		background2Y+=0.6;
		if (backgroundY > 1200)
			backgroundY = background2Y-1200;
		if (background2Y > 1200)
			background2Y = backgroundY-1200;
	}
	
	public void drawStars(Graphics g) {
		g.drawImage(stars, 0, (int)starsY, 700, 1200, null);
		g.drawImage(stars, 0, (int)stars2Y, 700, 1200, null);
		starsY+=0.8;
		stars2Y+=0.8;
		if (starsY > 1200)
			starsY = stars2Y-1200;
		if (stars2Y > 1200)
			stars2Y = starsY-1200;
	}
	
//	Sounds
	
	public void loopMusic() {
		if (!music.isRunning()) {
			music.setFramePosition(0);
			music.start();
		} else {
			music.loop(music.LOOP_CONTINUOUSLY);;
		}
	}
	
	public void stopMusic() {
		music.stop();
	}
	
}
