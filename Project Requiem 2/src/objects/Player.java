package objects;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Player {

//	Variables
	
	private Point coordinates;
	private int w, h;
	
	private Point hitboxCoordinates;
	private int hitboxW, hitboxH;
	
	private int lives;
	private boolean alive;
	private boolean invincible;
	private int invincibleTimer;
	
	private int bombs;
	private Point bombOriginCoordinates;
	boolean bomb;
	private double bombAngle;
	private int bombTimer;
	
	private boolean up, down;
	private boolean left, right;
	private boolean slow;
	
	private boolean shooting;
	private int shootTimer;
	
	private int animationTimer;
	
	private long score, hiScore;
	private int graze;
	
	private Image player;
	private Image hitbox;

	private BufferedImage player1, player1Invincible;
	private BufferedImage player2, player2Invincible;
	private BufferedImage playerLeft, playerLeftInvincible;
	private BufferedImage playerRight, playerRightInvincible;
	private BufferedImage hitBox;
	
	private File graze00;
	private AudioInputStream graze01;
	private Clip grazeSFX;
	private File death00;
	private AudioInputStream death01;
	private Clip deathSFX;
	
//	Constructor
	
	public Player(int x, int y, int w, int h, int lives){
		coordinates = new Point(x, y);
		this.w = w;
		this.h = h;
		hitboxW = 10;
		hitboxH = 10;
		this.lives = lives;
		bombs = 3;
		bombOriginCoordinates = new Point(x, y);
		bomb = false;
		bombAngle = 0;
		bombTimer = 300;
		alive = true;
		invincible = false;
		invincibleTimer = 300;
		up = false;
		down = false;
		left = false;
		right = false;
		slow = false;
		shooting = false;
		hitboxCoordinates = new Point(x+(w/2)-6, y+(h-25));
		shootTimer = 0;
		animationTimer = 0;
		score = 0;
		graze = 0;
		
		bufferImages();
		player = player1;
		hitbox = hitBox;
		assignClips();
	}
	
//	Methods
	
	public void bufferImages() {
		try {
		    player1 = ImageIO.read(new File("src/images/Player1.png"));
		    player1Invincible = ImageIO.read(new File("src/images/Player1Invincible.png"));
		} catch (IOException e) { 
		}
		try {
		    player2 = ImageIO.read(new File("src/images/Player2.png"));
		    player2Invincible = ImageIO.read(new File("src/images/Player2Invincible.png"));
		} catch (IOException e) { 
		}
		try {
		    playerLeft = ImageIO.read(new File("src/images/PlayerLeft.png"));
		    playerLeftInvincible = ImageIO.read(new File("src/images/PlayerLeftInvincible.png"));
		} catch (IOException e) { 
		}
		try {
		    playerRight = ImageIO.read(new File("src/images/PlayerRight.png"));
		    playerRightInvincible = ImageIO.read(new File("src/images/PlayerRightInvincible.png"));
		} catch (IOException e) { 
		}
		try {
		    hitBox = ImageIO.read(new File("src/images/Hitbox.png"));
		} catch (IOException e) { 
		}
	}
	
	public void assignClips() {
//		Graze
		try {
			graze00 = new File("src/sounds/graze.wav");
			graze01 = AudioSystem.getAudioInputStream(graze00);
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			grazeSFX = AudioSystem.getClip();
			grazeSFX.open(graze01);
		} catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Death
		try {
			death00 = new File("src/sounds/death.wav");
			death01 = AudioSystem.getAudioInputStream(death00);
		} catch (UnsupportedAudioFileException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			deathSFX = AudioSystem.getClip();
			deathSFX.open(death01);
		} catch (LineUnavailableException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	Logic
	
	public void movement() {
		if (getHitboxY() > 15) {
			if (getUp()) {
				setY(getY() - 3);
				setHitboxY(getHitboxY() - 3);
				if (getSlow()) {
					setY(getY() + 2);
					setHitboxY(getHitboxY() + 2);
				}
			}
		}
		if (getHitboxY() + getHitboxH() < 746) {
			if (getDown()) {
				setY(getY() + 3);
				setHitboxY(getHitboxY() + 3);
				if (getSlow()) {
					setY(getY() - 2);
					setHitboxY(getHitboxY() - 2);
				}
			}
		}
		if (getHitboxX() > 15) {
			if (getLeft()) {
				setX(getX() - 3);
				setHitboxX(getHitboxX() - 3);
				if (getSlow()) {
					setX(getX() + 2);
					setHitboxX(getHitboxX() + 2);
				}
			}	
		}
		if (getX() + getHitboxW() < 700) {
			if (getRight()) {
				setX(getX() + 3);
				setHitboxX(getHitboxX() + 3);
				if (getSlow()) {
					setX(getX() - 2);
					setHitboxX(getHitboxX() - 2);
				}
			}	
		}
	}
	
	public void animation() {
		animationTimer++;
		if (animationTimer <= 20) {
			if (invincible)
				player = player1Invincible;
			else
				player = player1;
		} else if (animationTimer <= 20*2){
			if (invincible)
				player = player2Invincible;
			else
				player = player2;
		} else {
			animationTimer = 0;
		}
		
		if (left && !right)
			if (invincible)
				player = playerLeftInvincible;
			else
				player = playerLeft;
		else if (right && !left)
			if (invincible)
				player = playerRightInvincible;
			else
				player = playerRight;
	}
	
	public void collision(ArrayList<BossBullet> bullets) {
		for (int i = 0; i < bullets.size(); i++) {
			if (!getInvincible()) {
				if (Math.pow((((bullets.get(i).getX()+(bullets.get(i).getW()/4))+((bullets.get(i).getW()/2)/2))-(getHitboxX()+(getHitboxW()/2))), 2) + Math.pow((((bullets.get(i).getY()+(bullets.get(i).getH()/4))+((bullets.get(i).getH()/2)/2))-((getHitboxY()+(getHitboxH()/2)))), 2) <= Math.pow((getHitboxW()/2)+((bullets.get(i).getW()/2)/2), 2)) {
					bullets.remove(i);
					setLives(getLives() - 1);
					setInvincible(true);
					setX(340);
					setY(550);
					setHitboxX(getX()+(getW()/2)-6);
					setHitboxY(getY()+(getH()-25));
					soundDeath();
					// TODO add death consequences
				} else if (bullets.get(i).getX() + bullets.get(i).getW() > getX() && bullets.get(i).getY() + bullets.get(i).getH() > getY()
						&& getX() + getW() > bullets.get(i).getX() && getY() + getH() > bullets.get(i).getY()) {
					if (!bullets.get(i).getGraze()) {
						setScore(getScore() + 1020);
						soundGraze();
						graze++;
					}
					bullets.get(i).setGraze(true);
				}
			}
		}
	}
	
	public void shoot(ArrayList<PlayerBullet> bullets) {
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).move();
		}
		if (shooting) {
			shootTimer++;
		} else {
			shootTimer = 0;
		}
		if (shootTimer == 5) {
			bullets.add(0, new PlayerBullet((getX()+(getW()/2)-30), getY() - 70, 32, 94));
			bullets.add(0, new PlayerBullet((getX()+(getW()/2)-4), getY() - 70, 32, 94));
			bullets.add(0, new PlayerBullet((getX()-getW()+6), getY() - 40, 32, 94));
			bullets.add(0, new PlayerBullet(getX()+getW(), getY() - 40, 32, 94));
			shootTimer = 0;
		}
	}

	public void bomb(ArrayList<PlayerBomb> bullet) {
//		Slowest
			
		bullet.add(new PlayerBomb(getBombOriginX()+5, getBombOriginY()+5, 30, 30, 1.2));
			
			bullet.get(bullet.size()-1).setAngle(bombAngle+90);
			while (bullet.get(bullet.size()-1).getAngle() > 360) {
				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
			}
			
			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(bombAngle)));     
			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(bombAngle)));

//		Moderate
			
			bullet.add(new PlayerBomb(getBombOriginX()+5, getBombOriginY()+5, 30, 30, 1.9));
			
			bullet.get(bullet.size()-1).setAngle(bombAngle+90);
			while (bullet.get(bullet.size()-1).getAngle() > 360) {
				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
			}
			
			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(bombAngle)));     
			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(bombAngle)));
			
//		Fastest
			
			bullet.add(new PlayerBomb(getBombOriginX()+5, getBombOriginY()+5, 30, 30, 2.6));
			
			bullet.get(bullet.size()-1).setAngle(bombAngle+90);
			while (bullet.get(bullet.size()-1).getAngle() > 360) {
				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
			}
			
			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(bombAngle)));     
			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(bombAngle)));
			
//			Slowest
				
			bullet.add(new PlayerBomb(getBombOriginX()+5, getBombOriginY()+5, 30, 30, 1.2));
			
			bullet.get(bullet.size()-1).setAngle(-bombAngle+90);
			while (bullet.get(bullet.size()-1).getAngle() > 360) {
				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
			}
			
			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(-bombAngle)));     
			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(-bombAngle)));
			
//			Moderate
			
			bullet.add(new PlayerBomb(getBombOriginX()+5, getBombOriginY()+5, 30, 30, 1.9));
			
			bullet.get(bullet.size()-1).setAngle(-bombAngle+90);
			while (bullet.get(bullet.size()-1).getAngle() > 360) {
				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
			}
			
			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(-bombAngle)));     
			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(-bombAngle)));
				
//		Fastest
				
			bullet.add(new PlayerBomb(getBombOriginX()+5, getBombOriginY()+5, 30, 30, 2.6));
			
			bullet.get(bullet.size()-1).setAngle(-bombAngle+90);
			while (bullet.get(bullet.size()-1).getAngle() > 360) {
				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
			}
				
			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(-bombAngle)));     
			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(-bombAngle)));
			
			bombAngle += 90.75;
			if (bombAngle > 360)
				bombAngle -= 360;
	}
	
	public void bombCollision(ArrayList<PlayerBomb> bomb, ArrayList<BossBullet> bullet) {
		for (int i = 0; i < bomb.size(); i++) {
			bomb.get(i).collision(bullet);
		}
	}
	
	public void removeBullets(ArrayList<PlayerBullet> bullets) {
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).getX() + bullets.get(i).getW() < 0 || bullets.get(i).getY() + bullets.get(i).getH() < 0 || bullets.get(i).getX() > 1200 || bullets.get(i).getY() > 800) {
				bullets.remove(i);
			}
		}
	}

	public void removeBomb(ArrayList<PlayerBomb> bullets) {
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).getX() + bullets.get(i).getW() < 0 || bullets.get(i).getY() + bullets.get(i).getH() < 0 || bullets.get(i).getX() > 1200 || bullets.get(i).getY() > 800) {
				bullets.remove(i);
			}
		}
	}

//	Graphics
	
	public void draw(Graphics g) {
		animation();
		g.drawImage(player, getX(), getY(), getW(), getH(), null);
		if (getSlow())
			g.drawImage(hitbox, getHitboxX(), getHitboxY(), getHitboxW(), getHitboxH(), null);
	}
	
	public void drawBullets(ArrayList<PlayerBullet> bullets, Graphics g) {
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
	}	
	
	public void drawBomb(ArrayList<PlayerBomb> bullets, Graphics g, Graphics2D g2d) {
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g, g2d);
		}
	}
	
	public void drawScore(int x, int y, Graphics g) {
		Font f = new Font("Times New Roman", 30, 30);
		
		g.setFont(f);
		
//		Absolute max score: 9999999999
		
		g.drawString("HiScore:", x, y);
		if (getHiScore() < 10) {
			g.drawString("000000000" + Long.toString(getHiScore()), x + 120, y);
		} else if (getHiScore() < 100) {
			g.drawString("00000000" + Long.toString(getHiScore()), x + 120, y);
		} else if (getHiScore() < 1000) {
			g.drawString("0000000" + Long.toString(getHiScore()), x + 120, y);
		} else if (getHiScore() < 10000) {
			g.drawString("000000" + Long.toString(getHiScore()), x + 120, y);
		} else if (getHiScore() < 100000) {
			g.drawString("00000" + Long.toString(getHiScore()), x + 120, y);
		} else if (getHiScore() < 1000000) {
			g.drawString("0000" + Long.toString(getHiScore()), x + 120, y);
		} else if (getHiScore() < 10000000) {
			g.drawString("000" + Long.toString(getHiScore()), x + 120, y);
		} else if (getHiScore() < 100000000) {
			g.drawString("00" + Long.toString(getHiScore()), x + 120, y);
		} else if (getHiScore() < 1000000000) {
			g.drawString("0" + Long.toString(getHiScore()), x + 120, y);
		} else {
			g.drawString(Long.toString(getHiScore()), x + 120, y);
		}
		
		g.drawString("Score:", x, y+30);
		if (getScore() < 10) {
			g.drawString("000000000" + Long.toString(getScore()), x + 120, y+30);
		} else if (getScore() < 100) {
			g.drawString("00000000" + Long.toString(getScore()), x + 120, y+30);
		} else if (getScore() < 1000) {
			g.drawString("0000000" + Long.toString(getScore()), x + 120, y+30);
		} else if (getScore() < 10000) {
			g.drawString("000000" + Long.toString(getScore()), x + 120, y+30);
		} else if (getScore() < 100000) {
			g.drawString("00000" + Long.toString(getScore()), x + 120, y+30);
		} else if (getScore() < 1000000) {
			g.drawString("0000" + Long.toString(getScore()), x + 120, y+30);
		} else if (getScore() < 10000000) {
			g.drawString("000" + Long.toString(getScore()), x + 120, y+30);
		} else if (getScore() < 100000000) {
			g.drawString("00" + Long.toString(getScore()), x + 120, y+30);
		} else if (getScore() < 1000000000) {
			g.drawString("0" + Long.toString(getScore()), x + 120, y+30);
		} else {
			g.drawString(Long.toString(getScore()), x + 120, y+30);
		}
	}
	
	public void drawLives(int x, int y, Graphics g) {
		Font f = new Font("Times New Roman", 30, 30);
		
		g.setFont(f);
		
		g.drawString("Lives: " + getLives(), x, y);
	}
	
	public void drawBombs(int x, int y, Graphics g) {
		Font f = new Font("Times New Roman", 30, 30);
		
		g.setFont(f);
		
		g.drawString("Bombs: " + getBombs(), x, y);
	}
	
	public void drawGraze(int x, int y, Graphics g) {
		Font f = new Font("Times New Roman", 30, 30);
		
		g.setFont(f);
		
		g.drawString("Graze:" + getGraze(), x, y);
	}
	
//	Sounds
	
	public void soundGraze() {
		grazeSFX.stop();
		grazeSFX.setFramePosition(0);
		grazeSFX.start();
	}

	public void soundDeath() {
		deathSFX.stop();
		deathSFX.setFramePosition(0);
		deathSFX.start();
	}
	
//	Getters
	
	public int getX() {
		return coordinates.getX();
	}
	
	public int getHitboxX() {
		return hitboxCoordinates.getX();
	}
	
	public int getY() {
		return coordinates.getY();
	}
	
	public int getHitboxY() {
		return hitboxCoordinates.getY();
	}
	
	public int getW() {
		return w;
	}
	
	public int getHitboxW() {
		return hitboxW;
	}
	
	public int getH() {
		return h;
	}
	
	public int getHitboxH() {
		return hitboxH;
	}
	
	public int getLives() {
		return lives;
	}
	
	public boolean getAlive() {
		return alive;
	}
	
	public int getBombs() {
		return bombs;
	}
	
	public int getBombOriginX() {
		return bombOriginCoordinates.getX();
	}

	public int getBombOriginY() {
		return bombOriginCoordinates.getY();
	}
	
	public boolean getBomb() {
		return bomb;
	}
	
	public double getBombAngle() {
		return bombAngle;
	}
	
	public int getBombTimer() {
		return bombTimer;
	}
	
	public boolean getInvincible() {
		return invincible;
	}
	
	public int getInvincibleTimer() {
		return invincibleTimer;
	}
	
	public boolean getUp() {
		return up;
	}
	
	public boolean getDown() {
		return down;
	}
	
	public boolean getLeft() {
		return left;
	}
	
	public boolean getRight() {
		return right;
	}
	
	public boolean getSlow() {
		return slow;
	}
	
	public boolean getShooting() {
		return shooting;
	}
	
	public long getScore() {
		return score;
	}
	
	public long getHiScore() {
		return hiScore;
	}
	
	public int getGraze() {
		return graze;
	}
	
//	Setters
	
	public void setX(int x) {
		coordinates.setX(x);
	}
	
	public void setHitboxX(int x) {
		hitboxCoordinates.setX(x);
	}
	
	public void setY(int y) {
		coordinates.setY(y);
	}
	
	public void setHitboxY(int y) {
		hitboxCoordinates.setY(y);
	}
	
	public void setW(int w) {
		this.w = w;
	}
	
	public void setHitboxW(int w) {
		hitboxW = w;
	}
	
	public void setH(int h) {
		this.h = h;
	}
	
	public void setHitboxH(int h) {
		hitboxH = h;
	}
	
	public void setLives(int l) {
		lives = l;
	}
	
	public void setAlive(boolean a) {
		alive = a;
	}
	
	public void setBombs(int b) {
		bombs = b;
	}
	
	public void setBombOriginX(int x) {
		bombOriginCoordinates.setX(x);
	}
	
	public void setBombOriginY(int y) {
		bombOriginCoordinates.setY(y);
	}
	
	public void setBomb(boolean b) {
		bomb = b;
	}
	
	public void setBombAngle(double bA) {
		bombAngle = bA;
	}

	public void setBombTimer(int bT) {
		bombTimer = bT;
	}
	
	public void setInvincible(boolean i) {
		invincible = i;
	}
	
	public void setInvincibleTimer(int iT) {
		invincibleTimer = iT;
	}
	
	public void setUp(boolean u) {
		up = u;
	}
	
	public void setDown(boolean d) {
		down = d;
	}
	
	public void setLeft(boolean l) {
		left = l;
	}
	
	public void setRight(boolean r) {
		right = r;
	}
	
	public void setSlow(boolean s) {
		slow = s;
	}
	
	public void setShooting(boolean s) {
		shooting = s;
	}
	
	public void setScore(long s) {
		score = s;
	}
	
	public void setHiScore(long s) {
		hiScore = s;
	}
	
	public void setGraze(int g) {
		graze = g;
	}
	
}
