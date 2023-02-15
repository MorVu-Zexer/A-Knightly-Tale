import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.Timer;

import objects.Background;
import objects.BossBullet;
import objects.Odin;
import objects.Player;
import objects.PlayerBomb;
import objects.PlayerBullet;

public class GUI extends JPanel implements ActionListener{

	Background background = new Background();
	Player player = new Player(340, 550, 40, 50, 3);
	
	Odin odin = new Odin(330, 100, 18*3, 25*3);
	
	ArrayList<PlayerBullet> playerBullets = new ArrayList<PlayerBullet>();
	ArrayList<PlayerBomb> playerBomb = new ArrayList<PlayerBomb>();
	ArrayList<BossBullet> bossBullets = new ArrayList<BossBullet>();
	
	Timer t;
	
	GUI(){
		t = new Timer(10, this);
		t.start();
	}
	
	public void highScoreWriter(long highScore) throws IOException {
		String path = System.getProperty("user.dir");
		File file = new File(path + "/src/hiScore.txt");
		Scanner s = new Scanner(file);
		String line;
		line = s.nextLine();
		highScore = Integer.parseInt(line);
		if (player.getScore() > highScore) {
			highScore = player.getScore();
			FileWriter output = new FileWriter(file);
			output.write(new Long(highScore).toString());
			output.close();
		}
		player.setHiScore(highScore);
	}
	
	public void paintComponent(Graphics g){
		
		Graphics2D g2d = (Graphics2D)g;
		
		super.paintComponent(g);
		background.draw(g);
		
		player.drawBullets(playerBullets, g);
		player.drawBomb(playerBomb, g, g2d);
		player.draw(g);
		
		odin.draw(g, g2d);
		odin.drawBullets(bossBullets, g, g2d);
		if (odin.getInvincible()) {
			odin.setDisplayPoints(true);
		}
		if (odin.getDisplayPointsTimer() <= 0) {
			odin.setDisplayPoints(false);
			odin.setDisplayPointsTimer(100);
			odin.removeBulletPoints();
		} else if (odin.getDisplayPoints()) {
			odin.drawBulletPoints(bossBullets, g);
			odin.removeAllBullets(bossBullets);
			odin.setDisplayPointsTimer(odin.getDisplayPointsTimer()-1);
		}
		drawUI(g);
		                   
		//TODO Print "Enemy" on the bottom of the screen to indicate where Odin is
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (player.getAlive()) {
			background.loopMusic();
			
			player.movement();
			player.collision(bossBullets);
			player.shoot(playerBullets);
			player.removeBullets(playerBullets);
			
			if (player.getBomb() && player.getBombTimer() > 0) {
				player.bomb(playerBomb);
				player.setBombTimer(player.getBombTimer() - 1);
			} else if (player.getBombTimer() <= 0) {
				player.setBombTimer(300);
				player.setBomb(false);
				player.setBombAngle(0);
			}
			for (int i = 0; i < playerBomb.size(); i++) {
				playerBomb.get(i).move();
			}
			player.removeBomb(playerBomb);
			player.bombCollision(playerBomb, bossBullets);
			
			if (player.getInvincible()) {
				player.setInvincibleTimer(player.getInvincibleTimer() - 1);
			}
			if (player.getInvincibleTimer() <= 0) {
				player.setInvincible(false);
				player.setInvincibleTimer(300);
			}
			if (player.getLives() <= 0)
				player.setAlive(false);
			
			if (odin.getInvincible()) {
				odin.setInvincibleTimer(odin.getInvincibleTimer()-1);
			} else {
				odin.collision(playerBullets, player);
				odin.setInvincibleTimer(100);
			}
			if (odin.getInvincibleTimer() <= 0) {
				odin.setInvincible(false);
			}
			if (odin.getHealth() == 1380 && odin.getBonus() == false || odin.getHealth() <= 0) {
				for (int i = 0; i < bossBullets.size(); i++) {
					player.setScore(player.getScore() + 1000);
				}
				odin.setInvincible(true);
			}
			odin.attack(bossBullets, player);
			
			odin.removeBullets(bossBullets);
		} else {
			background.stopMusic();
			pause();
		}
		
		
//		odin.setX(odin.getX() + 1);
		
		/* TODO 
		 * Music: Magus Night ~ Oriental Sacred Place ZUN remix
		 */
		
		/* 
		 * If his health goes down to the orange bar, you get a bonus and his health fills back up.
		 * He then starts a new attack. If his health is not depleted in time, he starts a new
		 * attack and does not give a bonus.
		 * 
		 * Timer is 30 seconds.
		 * 
		 * Surviving a full HP bar (red AND orange) without any deaths during that period grants a large bonus.
		 */

		try {
			highScoreWriter(player.getHiScore());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		repaint();
	}
	
	public void pause() {
		t.stop();
	}
	
	public void resume() {
		t = new Timer(10, this);
		t.start();
	}
	
	public void drawUI(Graphics g) {
//		Play Area
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 15, 800); // Top
		g.fillRect(0, 0, 715, 15); // Left
		g.fillRect(0, 746, 715, 50); // Bottom
		g.fillRect(715, 0, 600, 800); // Right
		odin.drawIndicator(g);
		g.setColor(Color.WHITE);
		g.drawRect(15, 15, 700, 731);
		
//		Player UI
		player.drawScore(740, 90, g);
		player.drawLives(740, 200, g);
		player.drawBombs(740, 230, g);
		player.drawGraze(740, 300, g);
		
		odin.drawHealth(20, 20, 690, 10, g);
		odin.drawTimer(650, 62, g);
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			player.setUp(true);
		}
		if (key == KeyEvent.VK_LEFT) {
			player.setLeft(true);
		}
		if (key == KeyEvent.VK_DOWN) {
			player.setDown(true);
		}	
		if (key == KeyEvent.VK_RIGHT) {
			player.setRight(true);
		}
		if (key == KeyEvent.VK_SHIFT) {
			player.setSlow(true);
		}
		if (key == KeyEvent.VK_Z) {
			player.setShooting(true);
		}
		if (key == KeyEvent.VK_X) {
			if (!player.getBomb() && player.getBombs() > 0) {
				player.setBombOriginX(player.getX());
				player.setBombOriginY(player.getY());
				player.setBomb(true);
				player.setBombs(player.getBombs()-1);
			}
		}
		if (key == KeyEvent.VK_SPACE) {
			pause();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			player.setUp(false);
		}
		if (key == KeyEvent.VK_LEFT) {
			player.setLeft(false);
		}
		if (key == KeyEvent.VK_DOWN) {
			player.setDown(false);
		}	
		if (key == KeyEvent.VK_RIGHT) {
			player.setRight(false);
		}
		if (key == KeyEvent.VK_SHIFT) {
			player.setSlow(false);
		}
		if (key == KeyEvent.VK_Z) {
			player.setShooting(false);
		}
		if (key == KeyEvent.VK_SPACE) {
			resume();
		}
	}

}
