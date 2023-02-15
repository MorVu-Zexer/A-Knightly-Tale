package oryx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Display extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	// String path = System.getProperty("home.dir");

	/*
	 * SpyEye - fires in all directions MudMuk - fires straight at the player in a
	 * shotgun pattern Hugsbert - predicts where the player will be MadMask -
	 * periodically rapid fires at the player
	 */

	Oryx oryx = new Oryx();
	Enemies enemy = new Enemies();

	int x = (oryx.frame.getWidth() / 2) - 10, y = (oryx.frame.getHeight() / 2) - 10;
	int locationX = x, locationY = y;
	int movementTrackerX = 0, movementTrackerY = 0;

	boolean playerAlive = true;
	int playerHP = 100;
	boolean up = false, down = false, left = false, right = false;
	boolean shooting = false;

	boolean ability = false;
	double mana = 100;

	int regen = 0;
	int regenUp = 0;
	int manaUp = 0;
	int damageUp = 0;
	int defenseUp = 0;
	int powerUpAnimation = 0;

	int shootingCounter = 0;

	int hiScore, killCount = 0;

	int mouseX = 0, mouseY = 0;

	int barrierX = -2000, barrierY = -2000, barrierW = 5000, barrierH = 5000;

	int spawnRate;

	String[] enemyNames = { "SpyEye", "MudMuk", "Hugsbert", "MadMask" };
	ArrayList<String> enemyName = new ArrayList<String>();
	Random random = new Random();

	ArrayList<Boolean> playerShot = new ArrayList<Boolean>();
	ArrayList<Integer> bulletLife = new ArrayList<Integer>();
	ArrayList<Double> bulletX = new ArrayList<Double>();
	ArrayList<Double> bulletY = new ArrayList<Double>();
	ArrayList<Double> bulletXAngle = new ArrayList<Double>();
	ArrayList<Double> bulletYAngle = new ArrayList<Double>();
	ArrayList<Double> bulletAimAngle = new ArrayList<Double>();

	ArrayList<Boolean> powerUp = new ArrayList<Boolean>();
	ArrayList<String> powerUpType = new ArrayList<String>();
	ArrayList<Integer> powerUpX = new ArrayList<Integer>();
	ArrayList<Integer> powerUpY = new ArrayList<Integer>();

	ArrayList<Integer> gravesX = new ArrayList<Integer>();
	ArrayList<Integer> gravesY = new ArrayList<Integer>();

	ArrayList<Boolean> enemyAlive = new ArrayList<Boolean>();
	ArrayList<Boolean> enemyShot = new ArrayList<Boolean>();
	ArrayList<Boolean> enemyShooting = new ArrayList<Boolean>();
	ArrayList<Double> enemyX = new ArrayList<Double>();
	ArrayList<Double> enemyY = new ArrayList<Double>();
	ArrayList<Double> enemyW = new ArrayList<Double>();
	ArrayList<Double> enemyH = new ArrayList<Double>();
	ArrayList<String> enemyBulletOrigin = new ArrayList<String>();
	ArrayList<Double> enemyBulletX = new ArrayList<Double>();
	ArrayList<Double> enemyBulletY = new ArrayList<Double>();
	ArrayList<Double> enemyBulletW = new ArrayList<Double>();
	ArrayList<Double> enemyBulletH = new ArrayList<Double>();
	ArrayList<Double> enemyBulletXAngle = new ArrayList<Double>();
	ArrayList<Double> enemyBulletYAngle = new ArrayList<Double>();
	ArrayList<Double> enemyBulletAimAngle = new ArrayList<Double>();
	ArrayList<Integer> enemyBulletLife = new ArrayList<Integer>();
	ArrayList<Double> enemyBulletSpeed = new ArrayList<Double>();
	ArrayList<Integer> enemyShootingCounter = new ArrayList<Integer>();
	ArrayList<Integer> enemyHP = new ArrayList<Integer>();
	ArrayList<Integer> enemyDirectionCounter = new ArrayList<Integer>();
	ArrayList<Double> movementAngleX = new ArrayList<Double>();
	ArrayList<Double> movementAngleY = new ArrayList<Double>();

	ArrayList<Double> storedX = new ArrayList<Double>();
	ArrayList<Double> storedY = new ArrayList<Double>();
	ArrayList<Double> storedX2 = new ArrayList<Double>();
	ArrayList<Double> storedY2 = new ArrayList<Double>();
	ArrayList<Double> predictiveAimAngle = new ArrayList<Double>();

	Timer t;

	AffineTransform at = new AffineTransform();
	
	BufferedImage playerBullet = null;
	BufferedImage madMaskBullet = null;
	BufferedImage SpyEyeBullet = null;
	BufferedImage HugsbertBullet = null;
	BufferedImage MudMukBullet = null;
	
	// Image player = Toolkit.getDefaultToolkit().getImage(path +
	// "/src/images/BulletHellShip.png");

	public Display() throws IOException {
		initComponents();
		t = new Timer(1, this);
		t.start();

		previousDeaths();
		
		bufferImages();
	}

	public void	bufferImages() {
		try {
		    playerBullet = ImageIO.read(new File("src/images/OryxBullet1.png"));
		} catch (IOException e) { 
		}
		try {
		    madMaskBullet = ImageIO.read(new File("src/images/MadMaskBullet.png"));
		} catch (IOException e) { 
		}
		try {
		    SpyEyeBullet = ImageIO.read(new File("src/images/SpyEyeBullet.png"));
		} catch (IOException e) { 
		}
		try {
		    HugsbertBullet = ImageIO.read(new File("src/images/HugsbertBullet.png"));
		} catch (IOException e) { 
		}
		try {
		    MudMukBullet = ImageIO.read(new File("src/images/MudMukBullet.png"));
		} catch (IOException e) { 
		}
	}
	
	public void highScoreWriter(int highScore) throws IOException {
		String path = System.getProperty("user.dir");
		File file = new File(path + "/src/oryx/hiScore.txt");
		Scanner s = new Scanner(file);
		String line;
		line = s.nextLine();
		highScore = Integer.parseInt(line);
		if (killCount > highScore) {
			highScore = killCount;
			FileWriter output = new FileWriter(file);
			output.write(new Integer(highScore).toString());
			output.close();
		}
		this.hiScore = highScore;
	}

	public void previousDeaths() throws IOException {
		String path = System.getProperty("user.dir");
		File file = new File(path + "/src/oryx/deadPlayers.txt");
		Scanner s = new Scanner(file);
		if (s.hasNextLine()) {
			String line = s.nextLine();
			String[] coordinates = line.split(" ");
			for (int i = 0; i < coordinates.length; i++) {
				String[] chopped = coordinates[i].split(",");
				gravesX.add(Integer.parseInt(chopped[0]));
				gravesY.add(Integer.parseInt(chopped[1]));
			}
		}

		// while (s.hasNext()) {
		// line = s.nextLine();
		// chopped = line.split(", ");
		// gravesX.add(Integer.parseInt(chopped[0]));
		// gravesY.add(Integer.parseInt(chopped[1]));
		// }
		s.close();
	}

	public void recordDeath(int x, int y) throws IOException {
		String path = System.getProperty("user.dir");
		File file = new File(path + "/src/oryx/deadPlayers.txt");
		FileWriter output = new FileWriter(file);
		for (int i = 0; i < gravesX.size(); i++) {
			output.write(new Integer(gravesX.get(i) + movementTrackerX).toString());
			output.write(",");
			output.write(new Integer(gravesY.get(i) + movementTrackerY).toString());
			output.write(" ");
		}
		output.write(new Integer(x).toString());
		output.write(",");
		output.write(new Integer(y).toString());
		output.write(" ");
		output.close();
	}

	public void drawPreviousDeaths(Graphics g) {
		int[] gravesX2 = new int[gravesX.size()];
		int[] gravesY2 = new int[gravesY.size()];
		for (int i = 0; i < gravesX.size(); i++) {
			gravesX2[i] = gravesX.get(i);
			gravesY2[i] = gravesY.get(i);
			drawDeadPlayer(gravesX2[i], gravesY2[i], g);
		}
	}

	public void spawning() {
		spawnRate = random.nextInt(500) + 1;

		if (spawnRate == 500) {
			double spawnPointX = random.nextInt((barrierX + barrierW) - 100) + barrierX + 100;
			double spawnPointY = random.nextInt((barrierY + barrierH) - 100) + barrierY + 100;

			enemyAlive.add(true);
			enemyName.add(enemyNames[random.nextInt(4)]);
			enemyX.add(spawnPointX);
			enemyY.add(spawnPointY);
			predictiveAimAngle.add(0.0);
			enemyShootingCounter.add(0);
			for (int i = 0; i < enemyAlive.size(); i++) {
				if (enemyName.get(i) == "SpyEye") {
					enemyW.add(210.0);
					enemyH.add(140.0);
				} else if (enemyName.get(i) == "MudMuk") {
					enemyW.add(180.0);
					enemyH.add(160.0);
				} else if (enemyName.get(i) == "Hugsbert") {
					enemyW.add(156.0);
					enemyH.add(168.0);
				} else if (enemyName.get(i) == "MadMask") {
					enemyW.add(144.0);
					enemyH.add(160.0);
				}
				if (enemyAlive.size() > enemyHP.size() && i == enemyHP.size()) {
					if (enemyName.get(i) == "SpyEye") {
						enemyHP.add(500);
					} else if (enemyName.get(i) == "MudMuk") {
						enemyHP.add(700);
					} else if (enemyName.get(i) == "Hugsbert") {
						enemyHP.add(600);
					} else if (enemyName.get(i) == "MadMask") {
						enemyHP.add(500);
					}
				}
			}
		}
	}

	public void drawPlayer(int x, int y, Graphics g) {

		g.setColor(Color.BLACK);

		g.fillRect(x, y, 20, 20);

		g.setColor(Color.RED);
		g.fillRect(x, y + 25, playerHP / 5, 5);
		g.setColor(Color.BLACK);
		g.drawRect(x, y + 25, 19, 5);

		if (mana >= 100) {
			g.setColor(Color.BLUE);
		} else {
			g.setColor(Color.CYAN);
		}
		g.fillRect(x, y + 32, (int) mana / 5, 5);

		g.setColor(Color.BLACK);
		g.drawRect(x, y + 32, 19, 5);

	}

	public void drawDeadPlayer(int x, int y, Graphics g) {
		int[] graveX = { x + 4, x + 4, x + 20, x + 20, x + 24, x + 24, x, x };
		int[] graveY = { y + 4, y, y, y + 4, y + 4, y + 28, y + 28, y + 4 };
		Color graveGray = new Color(200, 200, 200);
		g.setColor(graveGray);
		g.fillRect(x, y + 4, 4, 24);
		g.fillRect(x + 4, y, 4, 28);
		g.fillRect(x + 8, y, 4, 28);
		g.fillRect(x + 12, y, 4, 28);
		g.fillRect(x + 16, y, 4, 28);
		Color graveShade = new Color(152, 152, 152);
		g.setColor(graveShade);
		g.fillRect(x + 4, y + 8, 12, 4);
		g.fillRect(x + 4, y + 16, 12, 4);
		g.fillRect(x + 16, y, 4, 4);
		g.fillRect(x + 20, y + 4, 4, 24);
		g.setColor(Color.BLACK);
		g.drawPolygon(graveX, graveY, 8);
	}

	public void drawBullets(Graphics g, Graphics2D g2d) {
		for (int i = 0; i < playerShot.size(); i++) {
			double[] bulletX2 = new double[bulletX.size()];
			double[] bulletY2 = new double[bulletY.size()];
			bulletX2[i] = bulletX.get(i);
			bulletY2[i] = bulletY.get(i);
			
			at.rotate(-bulletAimAngle.get(i), bulletX2[i] + ((playerBullet.getWidth()*4)/2), bulletY2[i] + ((playerBullet.getHeight()*4) /2));
			g2d.transform(at);
			g2d.drawImage(playerBullet, (int) bulletX2[i] + 5, (int) bulletY2[i] + 5, 12, 20, null);
			
			try{
		        g2d.transform(at.createInverse());
		    }catch(NoninvertibleTransformException e){
		        //...
		    }
			
			at.setToIdentity();
		}
	}

	public void drawEnemies(Graphics g) {
		g.setColor(Color.RED);

		for (int i = 0; i < enemyAlive.size(); i++) {
			double[] enemyX2 = new double[enemyX.size()];
			double[] enemyY2 = new double[enemyY.size()];
			enemyX2[i] = enemyX.get(i);
			enemyY2[i] = enemyY.get(i);
			if (enemyName.get(i) == "SpyEye") {
				enemy.drawSpyEye((int) enemyX2[i], (int) enemyY2[i], g);
				g.setColor(Color.RED);
				g.fillRect((int) enemyX2[i] + 55, (int) enemyY2[i] + 150, enemyHP.get(i) / 5, 10);
				g.setColor(Color.BLACK);
				g.drawRect((int) enemyX2[i] + 55, (int) enemyY2[i] + 150, enemyHP.get(i) / 5, 10);
			} else if (enemyName.get(i) == "MudMuk") {
				enemy.drawMudMuk((int) enemyX2[i], (int) enemyY2[i], g);
				g.setColor(Color.RED);
				g.fillRect((int) enemyX2[i] + 40, (int) enemyY2[i] + 170, enemyHP.get(i) / 7, 10);
				g.setColor(Color.BLACK);
				g.drawRect((int) enemyX2[i] + 40, (int) enemyY2[i] + 170, enemyHP.get(i) / 7, 10);
			} else if (enemyName.get(i) == "Hugsbert") {
				enemy.drawHugsbert((int) enemyX2[i], (int) enemyY2[i], g);
				g.setColor(Color.RED);
				g.fillRect((int) enemyX2[i] + 28, (int) enemyY2[i] + 178, enemyHP.get(i) / 6, 10);
				g.setColor(Color.BLACK);
				g.drawRect((int) enemyX2[i] + 28, (int) enemyY2[i] + 178, enemyHP.get(i) / 6, 10);
			} else if (enemyName.get(i) == "MadMask") {
				enemy.drawMadMask((int) enemyX2[i], (int) enemyY2[i], g);
				g.setColor(Color.RED);
				g.fillRect((int) enemyX2[i] + 22, (int) enemyY2[i] + 178, enemyHP.get(i) / 5, 10);
				g.setColor(Color.BLACK);
				g.drawRect((int) enemyX2[i] + 22, (int) enemyY2[i] + 178, enemyHP.get(i) / 5, 10);
			}
			// g.fillRect((int) enemyX2[i], (int) enemyY2[i], 50, 50);
		}
	}
	
	public void drawMadMaskBullets(Graphics g, Graphics2D g2d) {
		for (int i = 0; i < enemyShot.size(); i++) {
			if (enemyShot.get(i) && enemyBulletOrigin.get(i) == "MadMask") {
				double[] enemyBulletX2 = new double[enemyBulletX.size()];
				double[] enemyBulletY2 = new double[enemyBulletY.size()];
				double[] enemyBulletW2 = new double[enemyBulletW.size()];
				double[] enemyBulletH2 = new double[enemyBulletH.size()];
				enemyBulletX2[i] = enemyBulletX.get(i);
				enemyBulletY2[i] = enemyBulletY.get(i);
				enemyBulletW2[i] = enemyBulletW.get(i);
				enemyBulletH2[i] = enemyBulletH.get(i);
				
				if (enemyBulletX2[i] + enemyBulletW2[i] > 0 && enemyBulletX2[i] < 1000 && enemyBulletY2[i] + enemyBulletH2[i] > 0 && enemyBulletY2[i] < 650) {
					at.rotate(-enemyBulletAimAngle.get(i) + Math.toRadians(-45), enemyBulletX2[i] + ((madMaskBullet.getWidth()*4)/2), enemyBulletY2[i] + ((madMaskBullet.getHeight()*4) /2));
					g2d.transform(at);
					g.drawImage(madMaskBullet, (int)enemyBulletX2[i], (int)enemyBulletY2[i], (int)enemyBulletW2[i], (int)enemyBulletH2[i], null);
					
					try{
				        g2d.transform(at.createInverse());
				    } catch(NoninvertibleTransformException e){
				        //...
				    }
					
					at.setToIdentity(); 
				} 
				
				}
			}
	}

	public void drawSpyEyeBullets(Graphics g, Graphics2D g2d) {
		for (int i = 0; i < enemyShot.size(); i++) {
			if (enemyShot.get(i) && enemyBulletOrigin.get(i) == "SpyEye") {
				double[] enemyBulletX2 = new double[enemyBulletX.size()];
				double[] enemyBulletY2 = new double[enemyBulletY.size()];
				double[] enemyBulletW2 = new double[enemyBulletW.size()];
				double[] enemyBulletH2 = new double[enemyBulletH.size()];
				enemyBulletX2[i] = enemyBulletX.get(i);
				enemyBulletY2[i] = enemyBulletY.get(i);
				enemyBulletW2[i] = enemyBulletW.get(i);
				enemyBulletH2[i] = enemyBulletH.get(i);
				
				if (enemyBulletX2[i] + enemyBulletW2[i] > 0 && enemyBulletX2[i] < 1000 && enemyBulletY2[i] + enemyBulletH2[i] > 0 && enemyBulletY2[i] < 650) {
					at.rotate(-enemyBulletAimAngle.get(i) + Math.toRadians(-45), enemyBulletX2[i] + ((SpyEyeBullet.getWidth()*4)/2), enemyBulletY2[i] + ((SpyEyeBullet.getHeight()*4) /2));
					g2d.transform(at);
					g.drawImage(SpyEyeBullet, (int)enemyBulletX2[i], (int)enemyBulletY2[i], (int)enemyBulletW2[i], (int)enemyBulletH2[i], null);
					
					try{
				        g2d.transform(at.createInverse());
				    }catch(NoninvertibleTransformException e){
				        //...
				    }
					
					at.setToIdentity(); 
				}
				}
			}
	}

	public void drawHugsbertBullets(Graphics g, Graphics2D g2d) {
		for (int i = 0; i < enemyShot.size(); i++) {
			if (enemyShot.get(i) && enemyBulletOrigin.get(i) == "Hugsbert") {
				double[] enemyBulletX2 = new double[enemyBulletX.size()];
				double[] enemyBulletY2 = new double[enemyBulletY.size()];
				double[] enemyBulletW2 = new double[enemyBulletW.size()];
				double[] enemyBulletH2 = new double[enemyBulletH.size()];
				enemyBulletX2[i] = enemyBulletX.get(i);
				enemyBulletY2[i] = enemyBulletY.get(i);
				enemyBulletW2[i] = enemyBulletW.get(i);
				enemyBulletH2[i] = enemyBulletH.get(i);
				
				if (enemyBulletX2[i] + enemyBulletW2[i] > 0 && enemyBulletX2[i] < 1000 && enemyBulletY2[i] + enemyBulletH2[i] > 0 && enemyBulletY2[i] < 650) {
					at.rotate(-enemyBulletAimAngle.get(i) + Math.toRadians(-45), enemyBulletX2[i] + ((HugsbertBullet.getWidth()*4)/2), enemyBulletY2[i] + ((HugsbertBullet.getHeight()*4) /2));
					g2d.transform(at);
					g.drawImage(HugsbertBullet, (int)enemyBulletX2[i], (int)enemyBulletY2[i], (int)enemyBulletW2[i], (int)enemyBulletH2[i], null);
					
					try{
				        g2d.transform(at.createInverse());
				    }catch(NoninvertibleTransformException e){
				        //...
				    }
					
					at.setToIdentity(); 
				}
				}
			}
	}

	public void drawMudMukBullets(Graphics g, Graphics2D g2d) {
		for (int i = 0; i < enemyShot.size(); i++) {
			if (enemyShot.get(i) && enemyBulletOrigin.get(i) == "MudMuk") {
				double[] enemyBulletX2 = new double[enemyBulletX.size()];
				double[] enemyBulletY2 = new double[enemyBulletY.size()];
				double[] enemyBulletW2 = new double[enemyBulletW.size()];
				double[] enemyBulletH2 = new double[enemyBulletH.size()];
				enemyBulletX2[i] = enemyBulletX.get(i);
				enemyBulletY2[i] = enemyBulletY.get(i);
				enemyBulletW2[i] = enemyBulletW.get(i);
				enemyBulletH2[i] = enemyBulletH.get(i);
				
				if (enemyBulletX2[i] + enemyBulletW2[i] > 0 && enemyBulletX2[i] < 1000 && enemyBulletY2[i] + enemyBulletH2[i] > 0 && enemyBulletY2[i] < 650) {
					at.rotate(-enemyBulletAimAngle.get(i), enemyBulletX2[i] + ((MudMukBullet.getWidth()*4)/2), enemyBulletY2[i] + ((MudMukBullet.getHeight()*4) /2));
					g2d.transform(at);
					g.drawImage(MudMukBullet, (int)enemyBulletX2[i], (int)enemyBulletY2[i], (int)enemyBulletW2[i], (int)enemyBulletH2[i], null);
					
					try{
				        g2d.transform(at.createInverse());
				    }catch(NoninvertibleTransformException e){
				        //...
				    }
					
					at.setToIdentity(); 
				}
				}
			}
	}
	
	public void drawHealthUp(int x, int y, Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y + 4, 4, 12);
		g.fillRect(x + 4, y, 8, 4);
		g.fillRect(x + 4, y + 16, 4, 4);
		g.fillRect(x + 8, y + 20, 4, 4);
		g.fillRect(x + 12, y + 4, 4, 4);
		g.fillRect(x + 12, y + 24, 4, 4);
		g.fillRect(x + 16, y, 8, 4);
		g.fillRect(x + 16, y + 20, 4, 4);
		g.fillRect(x + 20, y + 16, 4, 4);
		g.fillRect(x + 24, y + 4, 4, 12);
		g.setColor(Color.RED);
		g.fillRect(x + 4, y + 4, 4, 12);
		g.fillRect(x + 8, y + 4, 4, 16);
		g.fillRect(x + 12, y + 8, 4, 16);
		g.fillRect(x + 16, y + 4, 4, 16);
		g.fillRect(x + 20, y + 4, 4, 12);
		g.setColor(Color.WHITE);
		g.fillRect(x + 20, y + 4, 4, 4);
		for (int i = 0; i < powerUp.size(); i++) {
			drawPowerUpArrow(x + 24, y - 16, powerUpAnimation, Color.RED, g);
		}
	}

	public void drawManaUp(int x, int y, Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x + 4, y + 4, 20, 20);
		g.setColor(Color.BLACK);
		g.fillRect(x, y + 8, 4, 12);
		g.fillRect(x + 4, y + 4, 4, 4);
		g.fillRect(x + 4, y + 20, 4, 4);
		g.fillRect(x + 8, y, 12, 4);
		g.fillRect(x + 8, y + 24, 12, 4);
		g.fillRect(x + 20, y + 4, 4, 4);
		g.fillRect(x + 20, y + 20, 4, 4);
		g.fillRect(x + 24, y + 8, 4, 12);
		g.setColor(Color.WHITE);
		g.fillRect(x + 16, y + 8, 4, 4);
		for (int i = 0; i < powerUp.size(); i++) {
			drawPowerUpArrow(x + 24, y - 16, powerUpAnimation, Color.BLUE, g);
		}
	}
	
	public void drawDamageUp(int x, int y, Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x + 4, y + 4, 20, 20);
		g.setColor(Color.BLACK);
		g.fillRect(x, y + 4, 4, 16);
		g.fillRect(x + 4, y, 20, 4);
		g.fillRect(x + 4, y + 12, 12, 4);
		g.fillRect(x + 4, y + 20, 4, 8);
		g.fillRect(x + 4, y + 24, 20, 4);
		g.fillRect(x + 8, y, 4, 16);
		g.fillRect(x + 16, y, 4, 12);
		g.fillRect(x + 16, y + 8, 12, 4);
		g.fillRect(x + 24, y + 4, 4, 16);
		g.fillRect(x + 16, y + 16, 4, 4);
		g.fillRect(x + 20, y + 20, 4, 4);
		for (int i = 0; i < powerUp.size(); i++) {
			drawPowerUpArrow(x + 24, y - 16, powerUpAnimation, Color.RED, g);
		}
	}
	
	public void drawDefenseUp(int x, int y, Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x, y, 4, 16);
		g.fillRect(x, y, 32, 4);
		g.fillRect(x + 4, y + 16, 4, 4);
		g.fillRect(x + 8, y + 20, 4, 4);
		g.fillRect(x + 12, y + 24, 8, 4);
		g.fillRect(x + 20, y + 20, 4, 4);
		g.fillRect(x + 24, y + 16, 4, 4);
		g.fillRect(x + 28, y, 4, 16);
		Color shieldShade = new Color(130, 130, 130);
		g.setColor(shieldShade);
		g.fillRect(x + 4, y + 4, 12, 12);
		g.fillRect(x + 8, y + 16, 8, 4);
		g.fillRect(x + 12, y + 20, 4, 4);
		Color shield = new Color(201, 201, 201);
		g.setColor(shield);
		g.fillRect(x + 16, y + 20, 4, 4);
		g.fillRect(x + 16, y + 16, 8, 4);
		g.fillRect(x + 16, y + 4, 12, 12);
		for (int i = 0; i < powerUp.size(); i++) {
			drawPowerUpArrow(x + 24, y - 16, powerUpAnimation, shield, g);
		}
	}
	
	public void drawPowerUpArrow(int x, int y, int counter, Color color, Graphics g) {
		if (counter > 500 && counter < 1000) {
			y += 4;
		}
		g.setColor(Color.BLACK);
		g.fillRect(x, y + 8, 4, 4);
		g.fillRect(x + 4, y + 4, 4, 4);
		g.fillRect(x + 4, y + 12, 4, 12);
		g.fillRect(x + 8, y, 4, 4);
		g.fillRect(x + 8, y + 20, 4, 4);
		g.fillRect(x + 12, y + 4, 4, 4);
		g.fillRect(x + 12, y + 12, 4, 12);
		g.fillRect(x + 16, y + 8, 4, 4);
		g.setColor(color);
		g.fillRect(x + 4, y + 8, 12, 4);
		g.fillRect(x + 8, y + 4, 4, 16);
	}

	public void movement() {
		if (up == true) {
			if (y > barrierY) {
				barrierY++;
				locationY--;
				movementTrackerY--;

				for (int i = 0; i < enemyAlive.size(); i++) {
					enemyY.set(i, 1 + enemyY.get(i));
				}
				for (int i = 0; i < enemyShot.size(); i++) {
					enemyBulletY.set(i, 1 + enemyBulletY.get(i));
				}
				for (int i = 0; i < gravesY.size(); i++) {
					gravesY.set(i, 1 + gravesY.get(i));
				}
				for (int i = 0; i < powerUpY.size(); i++) {
					powerUpY.set(i, 1 + powerUpY.get(i));
				}
			}
		}

		if (down == true) {
			// y++;
			if (y < barrierY + barrierH - 20) {
				barrierY--;
				locationY++;
				movementTrackerY++;
				
				for (int i = 0; i < enemyAlive.size(); i++) {
					enemyY.set(i, -1 + enemyY.get(i));
				}
				for (int i = 0; i < enemyShot.size(); i++) {
					enemyBulletY.set(i, -1 + enemyBulletY.get(i));
				}
				for (int i = 0; i < gravesY.size(); i++) {
					gravesY.set(i, -1 + gravesY.get(i));
				}
				for (int i = 0; i < powerUpY.size(); i++) {
					powerUpY.set(i, -1 + powerUpY.get(i));
				}
			}
		}
		if (left == true) {
			//x--;
			if (x > barrierX) {
				barrierX++;
				locationX--;
				movementTrackerX--;
				
				for (int i = 0; i < enemyAlive.size(); i++) {
					enemyX.set(i, 1 + enemyX.get(i));
				}
				for (int i = 0; i < enemyShot.size(); i++) {
					enemyBulletX.set(i, 1 + enemyBulletX.get(i));
				}
				for (int i = 0; i < gravesY.size(); i++) {
					gravesX.set(i, 1 + gravesX.get(i));
				}
				for (int i = 0; i < powerUpX.size(); i++) {
					powerUpX.set(i, 1 + powerUpX.get(i));
				}
			}
		}
		if (right == true) {
			// x++;
			if (x < barrierX + barrierW - 20) {
				barrierX--;
				locationX++;
				movementTrackerX++;
				
				for (int i = 0; i < enemyAlive.size(); i++) {
					enemyX.set(i, -1 + enemyX.get(i));
				}
				for (int i = 0; i < enemyShot.size(); i++) {
					enemyBulletX.set(i, -1 + enemyBulletX.get(i));
				}
				for (int i = 0; i < gravesX.size(); i++) {
					gravesX.set(i, -1 + gravesX.get(i));
				}
				for (int i = 0; i < powerUpX.size(); i++) {
					powerUpX.set(i, -1 + powerUpX.get(i));
				}
			}
		}

		for (int i = 0; i < playerShot.size(); i++) {
			if (up) {
				bulletY.set(i, 1 + bulletY.get(i));
			}
			if (down) {
				bulletY.set(i, -1 + bulletY.get(i));
			}
			if (left) {
				bulletX.set(i, 1 + bulletX.get(i));
			}
			if (right) {
				bulletX.set(i, -1 + bulletX.get(i));
			}
		}
	}

	public void shootingMechanic() {

		double angle = Math.atan2((x + 15) - mouseX, (y + 35) - mouseY);
		
		if (shooting == true) {
			shootingCounter++;
			if (shootingCounter == 1) {
				playerShot.add(true);
				bulletLife.add(950);
				bulletX.add((double) x);
				bulletY.add((double) y);
				bulletXAngle.add(Math.sin(angle));
				bulletYAngle.add(Math.cos(angle));
				bulletAimAngle.add(angle);
			}
			if (shootingCounter == 50)
				shootingCounter = 0;
		} else {
			shootingCounter = 0;
		}

		for (int i = 0; i < playerShot.size(); i++) {
			bulletLife.set(i, -5 + bulletLife.get(i));
			if (playerShot.get(i)) {
				bulletX.set(i, bulletX.get(i) - bulletXAngle.get(i) * 2.5);
				bulletY.set(i, bulletY.get(i) - bulletYAngle.get(i) * 2.5);
			}
			if (bulletLife.get(i) <= 0) {
				bulletCleanup(i);
			}
		}
		if (ability) {
			abilityMechanic();
		} else if (mana < 100) {
			mana += 0.025 + (manaUp * 0.00625);
		}
	}

	public void abilityMechanic() {
		mana = 0;
		for (int i = 0; i < 30; i++) {
			double angle = Math.toRadians(0 + (i * 12));
			bulletXAngle.add(Math.sin(angle));
			bulletYAngle.add(Math.cos(angle));
			bulletAimAngle.add(angle);
			playerShot.add(true);
			bulletLife.add(950);
			bulletX.add((double) mouseX);
			bulletY.add((double) mouseY);
		}
		ability = false;
	}

	public void enemyLogic() {

		for (int i = 0; i < enemyAlive.size(); i++) {
			for (int i2 = 0; i2 < playerShot.size(); i2++) {
				if (enemyHit(i, i2)) {
					bulletCleanup(i2);
					enemyHP.set(i, (-10 - (damageUp * 2)) + enemyHP.get(i));
				}
			}
			if (enemyAlive.size() > 0) {
				enemyMovement(enemyX, enemyY, enemyW, enemyH, i);
				predictiveAiming(i);
			}

			if (enemyAlive.size() > 100) {
				enemyX.remove(i);
				enemyY.remove(i);
				enemyW.remove(i);
				enemyH.remove(i);
				enemyHP.remove(i);
				enemyName.remove(i);
				enemyShootingCounter.remove(i);
				enemyDirectionCounter.remove(i);
				storedX.remove(i);
				storedY.remove(i);
				storedX2.remove(i);
				storedY2.remove(i);
				predictiveAimAngle.remove(i);
				enemyAlive.remove(i);
			}

			if (enemyHP.get(i) <= 0)
				enemyAlive.set(i, false);
			if (enemyAlive.get(i) == false) {
					killCount++;
					int powerUpSpawn = random.nextInt(11); //TODO
					powerUp(powerUpSpawn, i);
				enemyX.remove(i);
				enemyY.remove(i);
				enemyW.remove(i);
				enemyH.remove(i);
				enemyHP.remove(i);
				enemyName.remove(i);
				enemyShootingCounter.remove(i);
				enemyDirectionCounter.remove(i);
				storedX.remove(i);
				storedY.remove(i);
				storedX2.remove(i);
				storedY2.remove(i);
				predictiveAimAngle.remove(i);
				enemyAlive.remove(i);
			}
		}
	}

	public void enemyMovement(ArrayList<Double> x, ArrayList<Double> y, ArrayList<Double> w, ArrayList<Double> h,
			int i) {
		if (enemyAlive.size() > enemyDirectionCounter.size()) {
			enemyDirectionCounter.add(0);
		}
		enemyDirectionCounter.set(i, 1 + enemyDirectionCounter.get(i));
		if (enemyDirectionCounter.get(i) == 1) {
			int direction = random.nextInt(359);
			double angle = direction;
			double angle2 = Math.atan2((enemyX.get(i) + 85) - (this.x - 10), (y.get(i) + 70) - this.y);
			if (movementAngleX.size() < enemyAlive.size()) {
				movementAngleX.add(Math.sin(angle));
				movementAngleY.add(Math.cos(angle));
			} else {
//				if (playerAlive && x.get(i) < 1000 && y.get(i) < 650 && 0 < x.get(i) + w.get(i)
//						&& 0 < y.get(i) + h.get(i)) {
					movementAngleX.set(i, (Math.sin(angle) + Math.sin(angle2)));
					movementAngleY.set(i, (Math.cos(angle) + Math.cos(angle2)));
//				} else {
//					movementAngleX.set(i, Math.sin(angle));
//					movementAngleY.set(i, Math.cos(angle));
//				}
			}
		}
		if (enemyDirectionCounter.get(i) < 50) {
			x.set(i, x.get(i) - movementAngleX.get(i) * 0.125);
			y.set(i, y.get(i) - movementAngleY.get(i) * 0.125);
		} else if (enemyDirectionCounter.get(i) >= 50) {
			enemyDirectionCounter.set(i, 0);
		}
	}

	public boolean enemyHit(int i, int i2) {
		if (enemyAlive.size() > 0) {
			if (bulletX.get(i2) < (enemyX.get(i) + 130.0) + 40.0 && enemyX.get(i) + 40.0 < bulletX.get(i2) + 10
					&& bulletY.get(i2) < (enemyY.get(i) + 130.0) + 10 && enemyY.get(i) < bulletY.get(i2) + 10
					&& enemyAlive.get(i)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void bulletCleanup(int i) {
		playerShot.remove(i);
		bulletLife.remove(i);
		bulletX.remove(i);
		bulletY.remove(i);
		bulletXAngle.remove(i);
		bulletYAngle.remove(i);
		bulletAimAngle.remove(i);
	}

	public void enemyBulletLogic() {
		for (int i = 0; i < enemyAlive.size(); i++) {
			double angle = Math.atan2((enemyX.get(i) + 85) - (x - 10), (enemyY.get(i) + 70) - y);
			enemyShootingCounter.set(i, 1 + enemyShootingCounter.get(i));
			if (enemyShootingCounter.get(i) == 400 && enemyName.get(i) == "SpyEye"
					|| enemyShootingCounter.get(i) == 500 && enemyName.get(i) == "MudMuk"
					|| enemyShootingCounter.get(i) == 450 && enemyName.get(i) == "Hugsbert"
					|| enemyName.get(i) == "MadMask") {
				if (enemyName.get(i) == "SpyEye") {
					omnidirectionalPattern(angle, i);
					enemyShootingCounter.set(i, 0);
				} else if (enemyName.get(i) == "MudMuk") {
					shotgunPattern(angle, i);
					enemyShootingCounter.set(i, 0);
				} else if (enemyName.get(i) == "Hugsbert") {
					predictiveAimAngle.set(i, predictiveAim(x, y, enemyX.get(i), enemyY.get(i), angle, i));
					shotgunPattern(predictiveAimAngle.get(i), i);
					enemyShootingCounter.set(i, 0);
				} else if (enemyName.get(i) == "MadMask") {
					if (enemyShootingCounter.get(i) == 50 || enemyShootingCounter.get(i) == 100
							|| enemyShootingCounter.get(i) == 150 || enemyShootingCounter.get(i) == 200
							|| enemyShootingCounter.get(i) == 250)
						genericPattern(angle, i);
					else if (enemyShootingCounter.get(i) == 450)
						enemyShootingCounter.set(i, 0);
				}
			}
		}
		for (int i2 = 0; i2 < enemyShooting.size(); i2++) {
			bulletMovement(i2);
		}
	}

	public void bulletMovement(int i) {
		enemyBulletLife.set(i, -1 + enemyBulletLife.get(i));
		enemyBulletX.set(i, enemyBulletX.get(i) - enemyBulletXAngle.get(i) * enemyBulletSpeed.get(i));
		enemyBulletY.set(i, enemyBulletY.get(i) - enemyBulletYAngle.get(i) * enemyBulletSpeed.get(i));

		if (enemyBulletLife.get(i) <= 0 || enemyShot.get(i) == false) {
			enemyBulletOrigin.remove(i);
			enemyBulletX.remove(i);
			enemyBulletY.remove(i);
			enemyBulletW.remove(i);
			enemyBulletH.remove(i);
			enemyBulletXAngle.remove(i);
			enemyBulletYAngle.remove(i);
			enemyBulletAimAngle.remove(i);
			enemyBulletSpeed.remove(i);
			enemyShooting.remove(i);
			enemyShot.remove(i);
			enemyBulletLife.remove(i);
		}
	}

	public double predictiveAim(int x, int y, double ex, double ey, double angle, int i) {
		double calculationX = ((storedX.get(i) - storedX2.get(i)) * -1) * 50;
		double calculationY = ((storedY.get(i) - storedY2.get(i)) * -1) * 50;
		// System.out.println(i + ": "+ storedX.get(i));
		// System.out.println((i+1) + ": "+ storedX.get(i+1));
		// System.out.println(i + " + " + (i+1) + ": "+ calculationX);
		// System.out.println(i + ": "+ storedY.get(i));
		// System.out.println((i+1) + ": "+ storedY.get(i+1));
		// System.out.println(i + " + " + (i+1) + ": "+ calculationY);
		// System.out.println(calculationX + ", " + calculationY);
		angle = Math.atan2((ex + 85) - ((x - 10) + calculationX), (ey + 70) - ((y - 10) + calculationY));
		// System.out.println(Math.toDegrees(angle));
		return angle;
	}

	public void predictiveAiming(int i) {
		if (storedX.size() < enemyAlive.size()) {
			storedX.add(i, (double) movementTrackerX);
			storedY.add(i, (double) movementTrackerY);
			storedX2.add(i, (double) movementTrackerX);
			storedY2.add(i, (double) movementTrackerY);
		}
		if (enemyShootingCounter.get(i) == 445) {
			storedX.set(i, (double) movementTrackerX);
			storedY.set(i, (double) movementTrackerY);
		}
		if (enemyShootingCounter.get(i) == 449) {
			storedX2.set(i, (double) movementTrackerX);
			storedY2.set(i, (double) movementTrackerY);
		}
	}

	public void genericPattern(double angle, int i) {
		if (enemyName.get(i) == "MadMask") {
			enemyShooting.add(true);
			enemyShot.add(true);
			enemyBulletOrigin.add("MadMask");
			enemyBulletX.add(enemyX.get(i) + 59.5);
			enemyBulletY.add(enemyY.get(i) + 63.5);
			enemyBulletW.add(25.0);
			enemyBulletH.add(25.0);
			enemyBulletXAngle.add(Math.sin(angle));
			enemyBulletYAngle.add(Math.cos(angle));
			enemyBulletAimAngle.add(angle);
			enemyBulletLife.add(500);
			enemyBulletSpeed.add(2.0);
		}
	}

	public void shotgunPattern(double angle, int i) {
		for (int i2 = 0; i2 < 5; i2++) {
			if (enemyName.get(i) == "MudMuk") {
				enemyShooting.add(true);
				enemyShot.add(true);
				enemyBulletOrigin.add("MudMuk");
				enemyBulletX.add(enemyX.get(i) + 80);
				enemyBulletY.add(enemyY.get(i) + 70);
				enemyBulletW.add(20.0);
				enemyBulletH.add(20.0);
				enemyBulletXAngle.add(Math.sin(angle + (Math.toRadians(-25) + (Math.toRadians(12.5 * i2)))));
				enemyBulletYAngle.add(Math.cos(angle + (Math.toRadians(-25) + (Math.toRadians(12.5 * i2)))));
				enemyBulletAimAngle.add(angle + (Math.toRadians(-25) + (Math.toRadians(12.5 * i2))));
				enemyBulletSpeed.add(1.6);
				enemyBulletLife.add(150);
			}
			if (enemyName.get(i) == "Hugsbert") {
				enemyShooting.add(true);
				enemyShot.add(true);
				enemyBulletOrigin.add("Hugsbert");
				enemyBulletX.add(enemyX.get(i) + 80);
				enemyBulletY.add(enemyY.get(i) + 70);
				enemyBulletW.add(20.0);
				enemyBulletH.add(20.0);
				enemyBulletXAngle.add(Math.sin(angle + (Math.toRadians(-15) + (Math.toRadians(7.5 * i2)))));
				enemyBulletYAngle.add(Math.cos(angle + (Math.toRadians(-15) + (Math.toRadians(7.5 * i2)))));
				enemyBulletAimAngle.add(angle + (Math.toRadians(-15) + (Math.toRadians(7.5 * i2))));
				enemyBulletSpeed.add(1.75);
				enemyBulletLife.add(250);
			}
			// TODO - figure out how to do predictive aim
		}
	}

	public void omnidirectionalPattern(double angle, int i) {
		for (int i2 = 0; i2 < 5; i2++) {
			if (enemyName.get(i) == "SpyEye") {
				enemyShooting.add(true);
				enemyShot.add(true);
				enemyBulletOrigin.add("SpyEye");
				enemyBulletX.add(enemyX.get(i) + 90);
				enemyBulletY.add(enemyY.get(i) + 65);
				enemyBulletW.add(30.0);
				enemyBulletH.add(30.0);
				enemyBulletXAngle.add(Math.sin(angle + Math.toRadians(72 * i2)));
				enemyBulletYAngle.add(Math.cos(angle + Math.toRadians(72 * i2)));
				enemyBulletAimAngle.add(angle + Math.toRadians(72 * i2));
				enemyBulletSpeed.add(2.5);
				enemyBulletLife.add(200);
			}
		}
	}

	public void bulletCollision() {
		for (int i = 0; i < enemyAlive.size(); i++) {
			for (int i2 = i * 5; i2 >= i * 5 && i2 < (i * 5) + 5 && i2 < enemyShooting.size(); i2++) {
				if (enemyBulletX.get(i2) < x + 20 && x < enemyBulletX.get(i2) + enemyBulletW.get(i2)
						&& enemyBulletY.get(i2) < y + 20 && y < enemyBulletY.get(i2) + enemyBulletH.get(i2)) {
					if (enemyName.get(i) == "SpyEye") {
						if ((defenseUp / 4) < 5) {
							playerHP -= 5 - (defenseUp / 4);
						} else
							playerHP -= 1;
					} else if (enemyName.get(i) == "MudMuk") {
						if ((defenseUp / 4) < 2) {
							playerHP -= 2 - (defenseUp / 4);
						} else
							playerHP -= 1;
					} else if (enemyName.get(i) == "Hugsbert") {
						if ((defenseUp / 4) < 10) {
							playerHP -= 10 - (defenseUp / 4);
						} else
							playerHP -= 1;
					} else if (enemyName.get(i) == "MadMask") {
						if ((defenseUp / 4) < 5) {
							playerHP -= 5 - (defenseUp / 4);
						} else
							playerHP -= 1;
					}
					enemyShot.set(i2, false);
				}
			}
		}
	}

	public void powerUp(int spawnRate, int i) {

		if (spawnRate == 0 || spawnRate == 1) {
			powerUp.add(true);
			powerUpType.add("Health");
		} else if (spawnRate == 2 || spawnRate == 3) {
			powerUp.add(true);
			powerUpType.add("Mana");
		} else if (spawnRate == 4) {
			powerUp.add(true);
			powerUpType.add("Damage");
		} else if (spawnRate == 5) {
			powerUp.add(true);
			powerUpType.add("Defense");
		}
		for (int i2 = 0; i2 < powerUp.size(); i2++) {
			if (powerUp.get(i2) && powerUp.size() > powerUpX.size() && i2 == powerUpX.size()) {
				int[] enemyX2 = new int[enemyX.size()];
				int[] enemyY2 = new int[enemyY.size()];
				enemyX2[i] += enemyX.get(i) + (enemyW.get(i) / 2) - 12;
				enemyY2[i] += enemyY.get(i) + (enemyH.get(i) / 2) - 12;
				powerUpX.add(enemyX2[i]);
				powerUpY.add(enemyY2[i]);
				// if (powerUp.get(i2) == false) {
				// powerUpX.remove(i2);
				// powerUpY.remove(i2);
				// powerUp.remove(i2);
				// }
			}
		}
	}

	public boolean powerUpCollected(int i) {
		if (x < powerUpX.get(i) + 44 && y < powerUpY.get(i) + 24 && powerUpX.get(i) < x + 24
				&& powerUpY.get(i) < y + 20) {
			return true;
		} else {
			return false;
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		powerUpAnimation++;
		if (powerUpAnimation == 1000)
			powerUpAnimation = 0;
		for (int i = 0; i < powerUpX.size(); i++) {
			if (powerUpType.get(i) == "Health")
				drawHealthUp(powerUpX.get(i), powerUpY.get(i), g);
			else if (powerUpType.get(i) == "Mana")
				drawManaUp(powerUpX.get(i), powerUpY.get(i), g);
			else if (powerUpType.get(i) == "Damage")
				drawDamageUp(powerUpX.get(i), powerUpY.get(i), g);
			else if (powerUpType.get(i) == "Defense")
				drawDefenseUp(powerUpX.get(i), powerUpY.get(i), g);
		}

		drawPreviousDeaths(g);

		g.drawRect(barrierX, barrierY, barrierW, barrierH);

		drawEnemies(g);
		
		for (int i = 0; i < enemyName.size(); i++) {
			if (enemyName.get(i) == "MadMask") {
				drawMadMaskBullets(g, g2d);
			} else if (enemyName.get(i) == "SpyEye") {
				drawSpyEyeBullets(g, g2d);
			} else if (enemyName.get(i) == "Hugsbert") {
				drawHugsbertBullets(g, g2d);
			} else {
				drawMudMukBullets(g, g2d);
			}
		}

		if (playerAlive) {
			drawPlayer(x, y, g);
		} else {
			drawDeadPlayer(x, y, g);
		}

		g.drawString("Personal Best: " + hiScore, 10, 20);
		g.drawString("Kill Count: " + killCount, 10, 40);

		g.drawString("Enemies Alive: " + enemyAlive.size(), 10, 65);
		g.drawString("Regen: " + regenUp, 10, 85);
		g.drawString("Mana Regen: " + manaUp, 10, 105);
		g.drawString("Damage: " + damageUp, 10, 125);
		g.drawString("Defense: " + defenseUp, 10, 145);
		
		drawBullets(g, g2d);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		spawning();
		
//		if (manaUp > 20) manaUp = 20; //mana cap
		
		regen++;
		if (regen > (300 - (regenUp * 5)) && playerHP < 100) {
			playerHP += 2 + regenUp;
			regen = 0;
		}
		if (playerHP > 100) {
			playerHP = 100;
		}
		for (int i = 0; i < powerUp.size(); i++) {
			if (powerUpCollected(i)) {
				if (powerUpType.get(i) == "Health")
					regenUp++;
				else if (powerUpType.get(i) == "Mana")
					manaUp++;
				else if (powerUpType.get(i) == "Damage") 
					damageUp++;
				else if (powerUpType.get(i) == "Defense") 
					defenseUp++;
				
				powerUpX.remove(i);
				powerUpY.remove(i);
				powerUp.remove(i);
				powerUpType.remove(i);
			}
		}

		if (playerHP <= 0) {
			playerAlive = false;
		}

		if (playerAlive) {
			movement();
			shootingMechanic();
			bulletCollision();
		} else {
			for (int i = 0; i < playerShot.size(); i++) {
				bulletLife.set(i, -5 + bulletLife.get(i));
				if (playerShot.get(i)) {
					bulletX.set(i, bulletX.get(i) - bulletXAngle.get(i) * 2.5);
					bulletY.set(i, bulletY.get(i) - bulletYAngle.get(i) * 2.5);
				}
				if (bulletLife.get(i) <= 0) {
					bulletCleanup(i);
				}
			}
			try {
				recordDeath(locationX, locationY);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		enemyLogic();
		enemyBulletLogic();
		
		try {
			highScoreWriter(hiScore);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		repaint();
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W) {
			up = true;
		}
		if (key == 'A') {
			left = true;
		}
		if (key == 'S') {
			down = true;
		}
		if (key == 'D') {
			right = true;
		}
		if (key == KeyEvent.VK_SPACE && mana >= 100) {
			ability = true;
		}
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W) {
			up = false;
		}
		if (key == 'A') {
			left = false;
		}
		if (key == 'S') {
			down = false;
		}
		if (key == 'D') {
			right = false;
		}
		if (key == KeyEvent.VK_SPACE && mana == 100) {
			ability = false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}
	@Override
	public void mousePressed(MouseEvent e) {
		shooting = true;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		shooting = false;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	@Override
	public void mouseExited(MouseEvent e) {

	}

	private void initComponents() {
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));
	}

}