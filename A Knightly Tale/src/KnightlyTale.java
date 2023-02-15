import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import assets.Asset;
import assets.*;
import characters.Character;
import maps.*;
import menu.Interaction_Menu;
import tiles.*;

/**
 * Program Name: KnightlyTale.java
 * Purpose: The meat and bones of A Knightly Tale
 * Coder: Jaden Duong
 * Date: Aug. 21, 2021
 */

/*
 * TODO (in descending order of difficulty):
 * 1. Add breakable objects
 * 2. Tile manipulation
 * 3. Inventory management
 * 4. Enemy movement
 */

public class KnightlyTale extends JFrame implements ActionListener {
	
	JPanel allyPanel, enemyPanel;
	
	Interaction_Menu menu = new Interaction_Menu();
	
	private Timer t;
	
	private int mouseX, mouseY;
	private ArrayList<Character> allies = new ArrayList<Character>();
	private ArrayList<Character> foes = new ArrayList<Character>();
	private ArrayList<Character> deathMarked = new ArrayList<Character>();
	
	private Asset arrow;
	
	private Map[] mapArray = {new TestMap(), new Map1(), new Map2()};;
	private int currMap;
	
	private class MouseAction implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			for (Character c : allies) {
				// Pull up interaction menu on right click
				if (c.getSelected() && !c.getMoved() && mapArray[currMap].getTile(c.getCoordsX(), c.getCoordsY()).getValid() && e.getButton() == 3) {
					menu.setExists(true);
					menu.setX(mouseX);
					menu.setY(mouseY);
				}
				// Should only be accessed after "Attacking" has been selected
				if (c.getAttacking()) {
					for (Character c2 : foes) {
						if (mouseX < c2.getX() + c2.getSize() && mouseY < c2.getY() + c2.getSize()
							&& c2.getX() < mouseX && c2.getY() < mouseY && !c2.getUnfocused()) {
							// IT'S TIME TO FIGHT
							if (c.getWeapon().getPhysical()) {
								// If the weapon is physical
								c2.setHealth(c2.getHealth() - ((c.getWeapon().getMight() + c.getStrength()) - c2.getDefense()));
								// Counter attack
								if (c2.getWeapon().getPhysical()) {
									// If the enemy's weapon is physical
									c.setHealth(c.getHealth() - ((c2.getWeapon().getMight() + c2.getStrength()) - c.getDefense()));
								} else {
									// If the enemy's weapon is magical
									c.setHealth(c.getHealth() - ((c2.getWeapon().getMight() + c2.getMagic()) - c.getResistance()));
								}
							} else {
								// If the weapon is magical
								c2.setHealth(c2.getHealth() - ((c.getWeapon().getMight() + c.getMagic()) - c2.getResistance()));
								// Counter attack
								if (c2.getWeapon().getPhysical()) {
									// If the enemy's weapon is physical
									c.setHealth(c.getHealth() - ((c2.getWeapon().getMight() + c2.getStrength()) - c.getDefense()));
								} else {
									// If the enemy's weapon is magical
									c.setHealth(c.getHealth() - ((c2.getWeapon().getMight() + c2.getMagic()) - c.getResistance()));
								}
							}
							// End character's move
							c.setCoordsXOrigin(c.getCoordsX());
							c.setCoordsYOrigin(c.getCoordsY());
							c.setMoved(true);
							mapArray[currMap].deselectTiles();
						}
						c2.setUnfocused(false); // Refocus all enemies
					}
					c.setAttacking(false);
				}
			}
			// Allows for the interaction menu to be clicked on and interacted with
			if (menu.getExists() && mouseX > menu.getX() && mouseY > menu.getY()
				&& mouseX < menu.getX() + menu.getSectionArray().get(menu.getSectionArray().size()-1).getWidth()
				&& mouseY < menu.getY() + menu.getSectionArray().get(menu.getSectionArray().size()-1).getHeight() * (menu.getSectionArray().size())) {
				// Find which section got clicked
				int sectionNo = (mouseY - menu.getY()) / menu.getSectionArray().get(menu.getSectionArray().size()-1).getHeight();
				// Activate the clicked section
				menu.getSectionArray().get(sectionNo).activate(allies, foes, mapArray[currMap]);
				menu.setExists(false);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			for (Character c : foes) {
				if (mouseX < c.getX() + c.getSize() && mouseY < c.getY() + c.getSize()
					&& c.getX() < mouseX && c.getY() < mouseY) {
					for (Character c2 : foes) {
						c2.setSelected(false);
					}
					c.setSelected(true);
				}
			}
			
			for (Character c : allies) {
				c.setUnfocused(false); // Regain focus on everyone when something gets touched. TODO: Will probably cause bugs later
				if (!menu.getExists()) {
					if (mouseX < c.getX() + c.getSize() && mouseY < c.getY() + c.getSize()
						&& c.getX() < mouseX && c.getY() < mouseY) {
						// If an ally character is clicked...
						for (Character c2 : allies) {
							// Reset the state of all other non-clicked allies
							if (mouseX < c2.getX() || mouseY < c2.getY() 
								|| mouseX > c2.getX() + c2.getSize() || mouseY > c2.getY() + c2.getSize()) {
								c2.setSelected(false);
								c2.setWalking(false);
								c2.setCoordsX(c2.getCoordsXOrigin());
								c2.setCoordsY(c2.getCoordsYOrigin());
							}
						}
						c.setSelected(true); // Select clicked ally
						c.setWalking(true);
						mapArray[currMap].deselectTiles();
					} else {
						// Clears movement forecast when an invalid tile is clicked
						for (int i = 0; i < mapArray[currMap].getWidth(); i++) {
							for (int j = 0; j < mapArray[currMap].getHeight(); j++) {
								// If an invalid tile is clicked while the ally is NOT attacking...
								if (mouseX < (mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * (i + 1))) + i && mouseY < (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * (j + 1))) + j
									&& (mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * i)) + i < mouseX && (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * j)) + j < mouseY
									&& !mapArray[currMap].getTile(i, j).getValid() && !c.getAttacking()) {
									// ...return them to their original spot and clear movement forecast
									c.setWalking(false);
									c.setCoordsX(c.getCoordsXOrigin());
									c.setCoordsY(c.getCoordsYOrigin());
									mapArray[currMap].deselectTiles();
								}
							}
						}
					}
				} else if (mouseX < menu.getX() || mouseY < menu.getY()
					|| mouseX > menu.getX() + menu.getSectionArray().get(menu.getSectionArray().size()-1).getWidth()
					|| mouseY > menu.getY() + menu.getSectionArray().get(menu.getSectionArray().size()-1).getHeight() * (menu.getSectionArray().size())) {
					menu.setExists(false);
				}
				
				// Sets up character's turn
				if (c.getSelected() && c.getWalking() && !c.getMoved() && !menu.getExists()) {
					mapArray[currMap].deselectTiles();
					mapArray[currMap].getTile(c.getCoordsXOrigin(), c.getCoordsYOrigin()).setValid(true);
					mapArray[currMap].getTile(c.getCoordsXOrigin(), c.getCoordsYOrigin()).setRemainingMov(c.getMovement() + 1);
					
					// Movement
					for (int movCountdown = c.getMovement(); movCountdown >= 0; movCountdown--) {
						for (int i = 0; i < mapArray[currMap].getWidth(); i++) {
							for (int j = 0; j < mapArray[currMap].getHeight(); j++) {							
								// Flood Fill								
								// If any tiles are valid...
								if (mapArray[currMap].getTile(i, j).getValid()) {
									// Check if adjacent tiles are accessible
									// Up
									if (j > 0) {
										// Counts for flood fill
										if (!mapArray[currMap].getTile(i, j-1).getValid() &&
												movCountdown - mapArray[currMap].getTile(i, j-1).getMovementValue() >= 0) {
											// Prevents movement from getting re-applied
											if (mapArray[currMap].getTile(i, j-1).getRemainingMov() < mapArray[currMap].getTile(i, j).getRemainingMov() - mapArray[currMap].getTile(i, j-1).getMovementValue()) {
												mapArray[currMap].getTile(i, j-1).setRemainingMov(mapArray[currMap].getTile(i, j).getRemainingMov() - mapArray[currMap].getTile(i, j-1).getMovementValue());
												mapArray[currMap].getTile(i, j-1).setValidPending(true);
											}
										}
									}
									// Down
									if (j < mapArray[currMap].getHeight() - 1) {
										// Counts for flood fill
										if (!mapArray[currMap].getTile(i, j+1).getValid() &&
												movCountdown - mapArray[currMap].getTile(i, j+1).getMovementValue() >= 0) {
											// Prevents movement from getting re-applied
											if (mapArray[currMap].getTile(i, j+1).getRemainingMov() < mapArray[currMap].getTile(i, j).getRemainingMov() - mapArray[currMap].getTile(i, j+1).getMovementValue()) {
												mapArray[currMap].getTile(i, j+1).setRemainingMov(mapArray[currMap].getTile(i, j).getRemainingMov() - mapArray[currMap].getTile(i, j+1).getMovementValue());	
												mapArray[currMap].getTile(i, j+1).setValidPending(true);	
											}
										}
									}
									// Left
									if (i > 0) {
										// Counts for flood fill
										if (!mapArray[currMap].getTile(i-1, j).getValid() &&
												movCountdown - mapArray[currMap].getTile(i-1, j).getMovementValue() >= 0) {
											// Prevents movement from getting re-applied
											if (mapArray[currMap].getTile(i-1, j).getRemainingMov() < mapArray[currMap].getTile(i, j).getRemainingMov() - mapArray[currMap].getTile(i-1, j).getMovementValue()) {
												mapArray[currMap].getTile(i-1, j).setRemainingMov(mapArray[currMap].getTile(i, j).getRemainingMov() - mapArray[currMap].getTile(i-1, j).getMovementValue());
												mapArray[currMap].getTile(i-1, j).setValidPending(true);
											}
										}
									}
									// Right
									if (i < mapArray[currMap].getWidth() - 1) {
										// Counts for flood fill
										if (!mapArray[currMap].getTile(i+1, j).getValid() &&
												movCountdown - mapArray[currMap].getTile(i+1, j).getMovementValue() >= 0) {
											// Prevents movement from getting re-applied
											if (mapArray[currMap].getTile(i+1, j).getRemainingMov() < mapArray[currMap].getTile(i, j).getRemainingMov() - mapArray[currMap].getTile(i+1, j).getMovementValue()) {
												mapArray[currMap].getTile(i+1, j).setRemainingMov(mapArray[currMap].getTile(i, j).getRemainingMov() - mapArray[currMap].getTile(i+1, j).getMovementValue());
												mapArray[currMap].getTile(i+1, j).setValidPending(true);
											}
										}
									}
								}
								mapArray[currMap].getTile(i, j).setAttackable(false);
							}					
						}
						
						// Make spaces occupied by enemies impassable
						for (Character c2 : foes) {
							// If a space that a non-ally is occupying is valid, invalidate it
							if (mapArray[currMap].getTile(c2.getCoordsX(), c2.getCoordsY()).getValidPending()) {
								mapArray[currMap].getTile(c2.getCoordsX(), c2.getCoordsY()).setValidPending(false);
							}
						}
						
						// Validate all pending tiles
						mapArray[currMap].validateTiles();
					}						
					// Account for spaces occupied by allies
					for (Character c2 : allies) {
						// If a space that a non-ally is occupying is valid, invalidate it
						if (mapArray[currMap].getTile(c2.getCoordsX(), c2.getCoordsY()).getValid() && c != c2) {
							mapArray[currMap].getTile(c2.getCoordsX(), c2.getCoordsY()).setValid(false);
						}
					}		
					
					// Attack range
					for (int attCountdown = c.getWeapon().getRange() - 1; attCountdown >= 0; attCountdown--) {
						for (int i = 0; i < mapArray[currMap].getWidth(); i++) {
							for (int j = 0; j < mapArray[currMap].getHeight(); j++) {
								// Flood Fill
								
								// If any tiles are valid...
								if (mapArray[currMap].getTile(i, j).getValid() || mapArray[currMap].getTile(i, j).getAttackable()) {
									// Up
									if (j > 0) {
										if (!mapArray[currMap].getTile(i, j-1).getValid() && !mapArray[currMap].getTile(i, j-1).getAttackable()) {
											mapArray[currMap].getTile(i, j-1).setAttackablePending(true);
										}
									}
									// Down
									if (j < mapArray[currMap].getHeight() - 1) {
										if (!mapArray[currMap].getTile(i, j+1).getValid() && !mapArray[currMap].getTile(i, j+1).getAttackable()) {
											mapArray[currMap].getTile(i, j+1).setAttackablePending(true);	
										}
									}
									// Left
									if (i > 0) {
										if (!mapArray[currMap].getTile(i-1, j).getValid() && !mapArray[currMap].getTile(i-1, j).getAttackable()) {
											mapArray[currMap].getTile(i-1, j).setAttackablePending(true);
										}
									}
									// Right
									if (i < mapArray[currMap].getWidth() - 1) {
										if (!mapArray[currMap].getTile(i+1, j).getValid() && !mapArray[currMap].getTile(i+1, j).getAttackable()) {
											mapArray[currMap].getTile(i+1, j).setAttackablePending(true);
										}
									}
								}
							}
						}
						
						// Validate all pending tiles
						mapArray[currMap].validateAttackTiles();
					}
					// Invalidate walls from being attackable
					for (int i = 0; i < mapArray[currMap].getWidth(); i++) {
						for (int j = 0; j < mapArray[currMap].getHeight(); j++) {
							if (mapArray[currMap].getTile(i, j) instanceof Wall) {
								mapArray[currMap].getTile(i, j).setAttackable(false);
							}
						}
					}						
					// Invalidate allies from being attackable
					for (Character c2 : allies) {
						// If a space that a non-ally is occupying is valid, invalidate it
						if (mapArray[currMap].getTile(c2.getCoordsX(), c2.getCoordsY()).getAttackable()) {
							mapArray[currMap].getTile(c2.getCoordsX(), c2.getCoordsY()).setAttackable(false);
						}
					}
				}			
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			for (Character c : allies) {
				for (int i = 0; i < mapArray[currMap].getWidth(); i++) {
					for (int j = 0; j < mapArray[currMap].getHeight(); j++) {
						if (mouseX < (mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * (i + 1))) + i && mouseY < (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * (j + 1))) + j
						&& (mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * i)) + i < mouseX && (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * j)) + j < mouseY && c.getSelected()
						&& mapArray[currMap].getTile(i, j).getValid() && !menu.getExists()) {
							c.setX(mapArray[currMap].getX() + (i * mapArray[currMap].getTileSize()) + 2 + i);
							c.setY(mapArray[currMap].getY() + (j * mapArray[currMap].getTileSize()) + 2 + j);
							c.setCoordsX(i);
							c.setCoordsY(j);
							
							// Reset Menu options
							menu.removeAttack();
							
							// Check if enemies are close enough to attack after moving
							mapArray[currMap].getTile(c.getCoordsX(), c.getCoordsY()).setSearched(true);
							for (int searchCountdown = c.getWeapon().getRange() - 1; searchCountdown >= 0; searchCountdown--) {
								for (int k = 0; k < mapArray[currMap].getWidth(); k++) {
									for (int l = 0; l < mapArray[currMap].getHeight(); l++) {
										// Flood Fill								
										// If any tiles are valid...
										if (mapArray[currMap].getTile(k, l).getSearched()) {
											// Up
											if (l > 0) {
												if (!mapArray[currMap].getTile(k, l-1).getSearched()) {
													mapArray[currMap].getTile(k, l-1).setSearchedPending(true);
												}
											}
											// Down
											if (l < mapArray[currMap].getHeight() - 1) {
												if (!mapArray[currMap].getTile(k, l+1).getSearched()) {
													mapArray[currMap].getTile(k, l+1).setSearchedPending(true);
												}
											}
											// Left
											if (k > 0) {
												if (!mapArray[currMap].getTile(k-1, l).getSearched()) {
													mapArray[currMap].getTile(k-1, l).setSearchedPending(true);
												}
											}
											// Right
											if (k < mapArray[currMap].getWidth() - 1) {
												if (!mapArray[currMap].getTile(k+1, l).getSearched()) {
													mapArray[currMap].getTile(k+1, l).setSearchedPending(true);
												}
											}
										}
									}
								}
								// ...validate the tiles
								mapArray[currMap].validateSearchTiles();
							}
							
							// Check if any enemies were in the searched area
							for (Character c2 : foes) {
								// If an enemy was standing on a searched tile...
								if (mapArray[currMap].getTile(c2.getCoordsX(), c2.getCoordsY()).getSearched()) {
									// ...add the attack option
									menu.addAttack();
								}
							}
						} else {
							c.setX(mapArray[currMap].getX() + (c.getCoordsX() * mapArray[currMap].getTileSize()) + 2 + c.getCoordsX());
							c.setY(mapArray[currMap].getY() + (c.getCoordsY() * mapArray[currMap].getTileSize()) + 2 + c.getCoordsY());
						}
					}
				}
			}
		}
	}
	
	private class MouseMotionAction implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			
			// Used to drag the character around
			for (Character c : allies) {
				if (c.getSelected() && !c.getMoved() && !menu.getExists()) {
					c.setX(mouseX - (c.getSize() / 2));
					c.setY(mouseY - (c.getSize() / 2));
				}
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}
		
	}
	
	public class AllyPanel extends JPanel {
		
		public AllyPanel() {
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			for (Character c : allies) {
				if (c.getSelected() && c.getAllied()) {
					g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
					g.drawString(c.getName(), 20, 60);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
					g.drawString("Equipped Weapon: " + c.getWeapon().getName(), 20, 120);
					// Summary stats
					g.drawString("HP: " + c.getHealth() + " / " + c.getMaxHealth(), 20, 170);
					g.drawString("MOV: " + c.getMovement(), 270, 170);
					if (c.getWeapon().getPhysical())
						g.drawString("ATT: " + (c.getStrength() + c.getWeapon().getMight()), 20, 220);
					else
						g.drawString("ATT: " + (c.getMagic() + c.getWeapon().getMight()), 20, 220);
					g.drawString("POWER: " + c.getPower(), 270, 220);
					// Character stats
					g.drawString("Str: " + c.getStrength(), 20, 270);
					g.drawString("Mag: " + c.getMagic(), 20, 320);
					g.drawString("Def: " + c.getDefense(), 270, 270);
					g.drawString("Res: " + c.getResistance(), 270, 320);
				}
			}
		}
	}
	
	public class Display extends JPanel {
		
		BufferedImage tileMovable = null;
		BufferedImage tileSelected = null;
		BufferedImage tileAttackable = null;
		
		final char FRAME = 50;
		char animationTimer = 0;
		
		public Display() {
			
			setMinimumSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));	

			currMap = 1; // 0 is the test map
			
			// Load allies in the given map
			for (Character c : mapArray[currMap].getAllyArray()) {
				allies.add(c);
				allies.get(allies.size()-1).setX(mapArray[currMap].getX() + (mapArray[currMap].getAllySpawnX(allies.size()-1) * mapArray[currMap].getTileSize()) + 2 + mapArray[currMap].getAllySpawnX(allies.size()-1));
				allies.get(allies.size()-1).setY(mapArray[currMap].getY() + (mapArray[currMap].getAllySpawnY(allies.size()-1) * mapArray[currMap].getTileSize()) + 2 + mapArray[currMap].getAllySpawnY(allies.size()-1));
				allies.get(allies.size()-1).setCoordsX(mapArray[currMap].getAllySpawnX(allies.size()-1));
				allies.get(allies.size()-1).setCoordsY(mapArray[currMap].getAllySpawnY(allies.size()-1));
				allies.get(allies.size()-1).setCoordsXOrigin(allies.get(allies.size()-1).getCoordsX());
				allies.get(allies.size()-1).setCoordsYOrigin(allies.get(allies.size()-1).getCoordsY());
			}

			// Load enemies in the given map
			for (Character c : mapArray[currMap].getEnemyArray()) {
				foes.add(c);
				foes.get(foes.size()-1).setX(mapArray[currMap].getX() + (mapArray[currMap].getEnemySpawnX(foes.size()-1) * mapArray[currMap].getTileSize()) + 2 + mapArray[currMap].getEnemySpawnX(foes.size()-1));
				foes.get(foes.size()-1).setY(mapArray[currMap].getY() + (mapArray[currMap].getEnemySpawnY(foes.size()-1) * mapArray[currMap].getTileSize()) + 2 + mapArray[currMap].getEnemySpawnY(foes.size()-1));
				foes.get(foes.size()-1).setCoordsX(mapArray[currMap].getEnemySpawnX(foes.size()-1));
				foes.get(foes.size()-1).setCoordsY(mapArray[currMap].getEnemySpawnY(foes.size()-1));
				foes.get(foes.size()-1).setCoordsXOrigin(foes.get(foes.size()-1).getCoordsX());
				foes.get(foes.size()-1).setCoordsYOrigin(foes.get(foes.size()-1).getCoordsY());
			}

			// Resize the sprites so they fit in the tiles
			for (Character c : allies) {
				c.setSize(mapArray[currMap].getTileSize() - 4);
			}
			for (Character c : foes) {
				c.setSize(mapArray[currMap].getTileSize() - 4);
			}
		}
		
		public void bufferImages() {
			try {
				tileSelected = ImageIO.read(new File("src/images/tiles/selected.png"));
			} catch(IOException e) {
				System.out.println("selected.png not found.");
			}
			try {
				tileMovable = ImageIO.read(new File("src/images/tiles/movable.png"));
			} catch(IOException e) {
				System.out.println("movable.png not found.");
			}
			try {
				tileAttackable = ImageIO.read(new File("src/images/tiles/attackable.png"));
			} catch(IOException e) {
				System.out.println("attackable.png not found.");
			}
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			bufferImages();
			
			for (int i = 0; i < mapArray[currMap].getWidth(); i++) {
				for (int j = 0; j < mapArray[currMap].getHeight(); j++) {						
					// Terrain
					if (mapArray[currMap].getTile(i, j) instanceof Forest) {
						g.setColor(Color.GREEN);
					} else if (mapArray[currMap].getTile(i, j) instanceof Water) {
						g.setColor(Color.CYAN);
					} else if (mapArray[currMap].getTile(i, j) instanceof Cliff) {
						g.setColor(Color.GRAY);
					} else if (mapArray[currMap].getTile(i, j) instanceof Wall) {
						g.setColor(Color.LIGHT_GRAY);
					} else {
						g.setColor(Color.BLACK);
					}
					
					// Valid tiles
					
					// Draw the tile being hovered over
					if (mouseX < (mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * (i + 1))) + i && mouseY < (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * (j + 1))) + j
						&& (mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * i)) + i < mouseX && (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * j)) + j < mouseY) {
						// Draw image
						g.drawImage(tileSelected, (mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * i)) + i, (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * j)) + j, mapArray[currMap].getTileSize(), mapArray[currMap].getTileSize(), null);
					} else if (mapArray[currMap].getTile(i, j).getValid()) {
						// Draw movable tiles
						g.drawImage(tileMovable, (mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * i)) + i, (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * j)) + j, mapArray[currMap].getTileSize(), mapArray[currMap].getTileSize(), null);
					} else if (mapArray[currMap].getTile(i, j).getAttackable()) {
						// Draw attackable tiles. Movable has priority over attackable
						g.drawImage(tileAttackable, (mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * i)) + i, (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * j)) + j, mapArray[currMap].getTileSize(), mapArray[currMap].getTileSize(), null);
					}
					
					// Tile outlines
					g.drawRect((mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * i)) + i, (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * j)) + j, mapArray[currMap].getTileSize(), mapArray[currMap].getTileSize());
					
					// Displays movement value for all tiles.
					// NOTE: For testing purposes only.
//					g.setColor(Color.BLACK);
//					g.drawString("" + mapArray[currMap].getTile(i, j).getMovementValue(), (mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * i)) + i, (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * j)) + j + 10);
//					
//					g.setColor(Color.RED);
//					g.drawString("" + mapArray[currMap].getTile(i, j).getRemainingMov(), (mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * i)) + i, (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * j)) + j + 20);
//					if (mapArray[currMap].getTile(i, j).getSearched()) {
//						g.drawRect((mapArray[currMap].getX() + (mapArray[currMap].getTileSize() * i)) + i, (mapArray[currMap].getY() + (mapArray[currMap].getTileSize() * j)) + j, 50, 50);
//					}
					
				}
			}
			
			for (Character c : allies) {
				// Animations!
				if (c.getMoved() && c.getUnfocused()) {
					// Already moved and is unfocused
					c.drawMovedUnfocused();
				} else if (c.getMoved()) {
					// Already moved
					c.drawMoved();
				} else if (c.getUnfocused()) {
					// Unfocused
					c.animateUnfocused(animationTimer, FRAME);
				} else if (c.getWalking()) {
					// Animate if a character is selected
					c.animateWalking(animationTimer, FRAME);
				} else {
					// Idle
					c.animateIdle(animationTimer, FRAME);
					// c.animateIdle(animationTimer, FRAME);
				}
				
				// Draw the actual character
				if (c.getSprite() != null) {
					// If a character sprite exists
					g.drawImage(c.getSprite(), c.getX(), c.getY(), c.getSize(), c.getSize(), null);
				} else {
					// Draw a blue rectangle otherwise
					g.setColor(Color.BLUE);
					g.drawRect(c.getX(), c.getY(), c.getSize(), c.getSize());					
				}
			}

			for (Character c : foes) {
				// Animations!
				if (c.getMoved()) {
					// Already moved
					c.drawMoved();
				} else if (c.getUnfocused()) {
					// Unfocused
					c.animateUnfocused(animationTimer, FRAME);
				} else {
					// Idle
					c.animateIdle(animationTimer, FRAME);
				}
				
				// Draw the actual character
				if (c.getSprite() != null) {
					// If a character sprite exists
					g.drawImage(c.getSprite(), c.getX(), c.getY(), c.getSize(), c.getSize(), null);
				} else {
					// Draw a red rectangle otherwise
					g.setColor(Color.RED);
					g.drawRect(c.getX(), c.getY(), c.getSize(), c.getSize());
				}
			}
			
			for (Character c : allies) {				
				// Draw an arrow to signal the selected character
				if (c.getSelected()) {
					arrow = new Arrow(mapArray[currMap].getTileSize() + 1, mapArray[currMap].getTileSize() + 1);                                          
					g.drawImage(arrow.getSprite(),
						(c.getCoordsX() + 1) * arrow.getX() + (c.getSize() / 5),
						(c.getCoordsY() + 1) * arrow.getY() - ((c.getSize() / 3) * 3),
						(c.getSize() / 5) * 4, (c.getSize() / 3) * 2, null);
				}
			}

			// Reset the animation timer
			if (animationTimer >= FRAME * 2) {
				animationTimer = 0;
			}
			animationTimer++; // Increment animation
			
			if (menu.getExists()) {
				menu.drawInteractionMenu(menu.getX(), menu.getY(), g);
			}
		}
	}
	 
	public class EnemyPanel extends JPanel {
		
		public EnemyPanel() {
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
			
			for (Character c : foes) {
				if (c.getSelected() && !c.getAllied()) {
					g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
					g.drawString(c.getName(), 20, 60);
					g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
					g.drawString("Equipped Weapon: " + c.getWeapon().getName(), 20, 120);
					// Summary stats
					g.drawString("HP: " + c.getHealth() + " / " + c.getMaxHealth(), 20, 170);
					g.drawString("MOV: " + c.getMovement(), 270, 170);
					if (c.getWeapon().getPhysical())
						g.drawString("ATT: " + (c.getStrength() + c.getWeapon().getMight()), 20, 220);
					else
						g.drawString("ATT: " + (c.getMagic() + c.getWeapon().getMight()), 20, 220);
					g.drawString("POWER: " + c.getPower(), 270, 220);
					// Character stats
					g.drawString("Str: " + c.getStrength(), 20, 270);
					g.drawString("Mag: " + c.getMagic(), 20, 320);
					g.drawString("Def: " + c.getDefense(), 270, 270);
					g.drawString("Res: " + c.getResistance(), 270, 320);
				}
			}
		}
	}
	
	public KnightlyTale() {
		super("A Knightly Tale");
		
		t = new Timer(1, this);
		t.start();
		
		this.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		Display display = new Display();
		
		c.fill = GridBagConstraints.BOTH;
		
		c.insets = new Insets(6, 6, 6, 3);
		c.weightx = 0.5;
		c.weighty = 1;
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(new AllyPanel(), c);		
		
		display.addMouseListener(new MouseAction());
		display.addMouseMotionListener(new MouseMotionAction());
		
		c.insets = new Insets(6, 3, 6, 3);
		c.gridx = 1;
		c.weightx = 1;
		c.gridwidth = 2;
		this.add(display, c);
		
		c.gridx = 2;
		c.weightx = 0.5;
		c.gridwidth = 1;
		this.add(new JPanel(), c);

		c.insets = new Insets(6, 3, 6, 6);
		c.gridx = 3;
		this.add(new EnemyPanel(), c);
		
		
		t = new Timer(10, this);
		t.start();
		
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new KnightlyTale();
	}
	// end main

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		// Death
		// Allies
		for (Character c : allies) {
			if (c.getHealth() <= 0) {
				deathMarked.add(c);
			}
		}
		allies.removeAll(deathMarked);
		deathMarked.clear();
		
		// Enemies
		for (Character c : foes) {
			if (c.getHealth() <= 0) {
				deathMarked.add(c);
			}
		}
		foes.removeAll(deathMarked);
		deathMarked.clear();
		
		// Automatically end turn if all allies have moved
		boolean endTurn = true;
		for (Character c : allies) {
			if (!c.getMoved())
				endTurn = false;
		}
		// Next turn
		if (endTurn) {
			for (Character c : allies) {
				c.setMoved(false);
				c.setCoordsXOrigin(c.getCoordsX());
				c.setCoordsYOrigin(c.getCoordsY());
//				System.out.println(allies.size() + ", " + c.getMoved());
			}
		}
		
		repaint();
	}
}
 // end class