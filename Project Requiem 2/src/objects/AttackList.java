package objects;

import java.util.ArrayList;
import java.util.Random;

public class AttackList {

//	Spell-Specific Variables
	
	private double angle = 0;
	
	private boolean clockwise = true, cClockwise;
	
	private int duration = 3000;
	
	private ArrayList<Integer> targetX = new ArrayList<Integer>();
	private ArrayList<Integer> targetY = new ArrayList<Integer>();
	
	Random r = new Random();
	
//	Constructor
	
	AttackList(){
		
	}
	
//	Methods
	
	public void rest() {
		angle = 0;
		duration = 3000;
	}
	
	public void genericSprial(ArrayList<BossBullet> bullet, int x, int y, double a, boolean clockwise) {
//		Clockwise

		if (duration % 3 == 0) {
			bullet.add(new BossBullet(x-5, y-10, 10, 20, 2.5, "Directed"));
			bullet.get(bullet.size()-1).setX(bullet.get(bullet.size()-1).getX()-(bullet.get(bullet.size()-1).getW()/2));
			
			while (bullet.get(bullet.size()-1).getAngle() > 360) {
				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
			}
			if (clockwise) {
				bullet.get(bullet.size()-1).setAngle(angle);
				bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle+90)));     
				bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle+90)));
			}
			else {
				bullet.get(bullet.size()-1).setAngle(-angle);
				bullet.get(bullet.size()-1).setAngleX(Math.cos(-Math.toRadians(angle-90)));     
				bullet.get(bullet.size()-1).setAngleY(Math.sin(-Math.toRadians(angle-90)));
			}
		}
			
			angle += a;
			if (angle > 360)
				angle -= 360;
	}
	
	public void flower(ArrayList<BossBullet> bullet, int x, int y) {
		
//	Clockwise
		
		bullet.add(new BossBullet(x-5, y-10, 10, 20, 2.5, "Directed"));
		bullet.get(bullet.size()-1).setX(bullet.get(bullet.size()-1).getX()-(bullet.get(bullet.size()-1).getW()/2));
		
		bullet.get(bullet.size()-1).setAngle(angle+90);
		while (bullet.get(bullet.size()-1).getAngle() > 360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
		}
		
		bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle)));     
		bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle)));

//	Counter clockwise
		
		bullet.add(new BossBullet(x-5, y-10, 10, 20, 2.5, "Directed"));
		
		bullet.get(bullet.size()-1).setAngle(-angle);
		while (bullet.get(bullet.size()-1).getAngle() < -360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() + 360);
		}
		
		bullet.get(bullet.size()-1).setAngleX(Math.sin(Math.toRadians(angle)));     
		bullet.get(bullet.size()-1).setAngleY(Math.cos(Math.toRadians(angle)));
				
		angle += 60.75;
		if (angle > 360)
			angle -= 360;
	}
	
	public void vortex(ArrayList<BossBullet> bullet, int x, int y) {
		
//	Slowest
		
		bullet.add(new BossBullet(x-5, y-10, 10, 20, 1.2, "Directed"));
		
		bullet.get(bullet.size()-1).setAngle(angle+90);
		while (bullet.get(bullet.size()-1).getAngle() > 360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
		}
		
		bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle)));     
		bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle)));

//	Moderate
		
		bullet.add(new BossBullet(x-5, y-10, 10, 20, 1.9, "Directed"));
		
		bullet.get(bullet.size()-1).setAngle(angle+90);
		while (bullet.get(bullet.size()-1).getAngle() > 360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
		}
		
		bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle)));     
		bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle)));
		
//	Fastest
		
		bullet.add(new BossBullet(x-5, y-10, 10, 20, 2.6, "Directed"));
		
		bullet.get(bullet.size()-1).setAngle(angle+90);
		while (bullet.get(bullet.size()-1).getAngle() > 360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
		}
		
		bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle)));     
		bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle)));

		angle += 90.75;
		if (angle > 360)
			angle -= 360;
	}
	
	public void fractal(ArrayList<BossBullet> bullet, int x, int y) {
		
		for (int i = 0; i < 3; i++) {
			bullet.add(new BossBullet(x-5, y-10, 10, 20, 1.2+(i*0.7), "Inverse"));
			
			bullet.get(bullet.size()-1).setAngle(angle+90);
			while (bullet.get(bullet.size()-1).getAngle() > 360) {
				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
			}
			
			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle)));     
			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle)));
			
			angle += 90.75;
			if (angle > 360)
				angle -= 360;
		}
		
		for (int i = 0; i < 3; i++) {
			bullet.add(new BossBullet(x-5, y-10, 10, 20, 1.2+(i*0.7), "Inverse"));
			
			bullet.get(bullet.size()-1).setAngle(-angle+90);
			while (bullet.get(bullet.size()-1).getAngle() > 360) {
				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
			}
			
			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(-angle)));     
			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(-angle)));

			angle += 90.75;
			if (angle > 360)
				angle -= 360;
		}
		
////		Slowest
//			
//			bullet.add(new BossBullet(x-5, y-10, 10, 20, 1.2, "Inverse"));
//			
//			bullet.get(bullet.size()-1).setAngle(angle+90);
//			while (bullet.get(bullet.size()-1).getAngle() > 360) {
//				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
//			}
//			
//			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle)));     
//			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle)));
//
////		Moderate
//			
//			bullet.add(new BossBullet(x-5, y-10, 10, 20, 1.9, "Inverse"));
//			
//			bullet.get(bullet.size()-1).setAngle(angle+90);
//			while (bullet.get(bullet.size()-1).getAngle() > 360) {
//				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
//			}
//			
//			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle)));     
//			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle)));
//			
////		Fastest
//			
//			bullet.add(new BossBullet(x-5, y-10, 10, 20, 2.6, "Directed"));
//			
//			bullet.get(bullet.size()-1).setAngle(angle+90);
//			while (bullet.get(bullet.size()-1).getAngle() > 360) {
//				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
//			}
//			
//			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle)));     
//			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle)));
			
////			Slowest
//				
//			bullet.add(new BossBullet(x-5, y-10, 10, 20, 1.2, "Inverse"));
//			
//			bullet.get(bullet.size()-1).setAngle(-angle+90);
//			while (bullet.get(bullet.size()-1).getAngle() > 360) {
//				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
//			}
//			
//			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(-angle)));     
//			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(-angle)));
//			
////			Moderate
//			
//			bullet.add(new BossBullet(x-5, y-10, 10, 20, 1.9, "Inverse"));
//			
//			bullet.get(bullet.size()-1).setAngle(-angle+90);
//			while (bullet.get(bullet.size()-1).getAngle() > 360) {
//				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
//			}
//			
//			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(-angle)));     
//			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(-angle)));
//				
////		Fastest
//				
//			bullet.add(new BossBullet(x-5, y-10, 10, 20, 2.6, "Directed"));
//			
//			bullet.get(bullet.size()-1).setAngle(-angle+90);
//			while (bullet.get(bullet.size()-1).getAngle() > 360) {
//				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
//			}
//				
//			bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(-angle)));     
//			bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(-angle)));
			
			angle += 90.75;
			if (angle > 360)
				angle -= 360;
	}

	public void zigzag(ArrayList<BossBullet> bullet, int x, int y) {
//	0 Degrees
		
		bullet.add(new BossBullet(x-5, y-10, 10, 20, 2.5, "Directed"));
		
		bullet.get(bullet.size()-1).setAngle(angle+90);
		while (bullet.get(bullet.size()-1).getAngle() > 360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
		}
			
		bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle)));     
		bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle)));
		
//	22.5 Degrees
		
		bullet.add(new BossBullet(x-5, y-10, 10, 20, 2.5, "Directed"));
		
		bullet.get(bullet.size()-1).setAngle(angle+110.5);
		while (bullet.get(bullet.size()-1).getAngle() > 360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
		}
			
		bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle+22.5)));     
		bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle+22.5)));

//	45 Degrees
			
		bullet.add(new BossBullet(x-5, y-10, 10, 20, 2.5, "Directed"));
			
		bullet.get(bullet.size()-1).setAngle(angle+135);
		while (bullet.get(bullet.size()-1).getAngle() > 360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
		}
			
		bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle+45)));     
		bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle+45)));

//	67.5 Degrees
				
		bullet.add(new BossBullet(x-5, y-10, 10, 20, 2.5, "Directed"));
					
		bullet.get(bullet.size()-1).setAngle(angle+157.5);
		while (bullet.get(bullet.size()-1).getAngle() > 360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
		}
						
		bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle+67.5)));     
		bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle+67.5)));
			
		if (angle > 4320) {
			clockwise = false;
			cClockwise = true;
		} else if (angle < -4320) {
			clockwise = true;
			cClockwise = false;
		}
		
		if (clockwise) {
			angle += 90.3;
		} else if (cClockwise) {
			angle -= 90.3;
		}
		
	}

	public void doubleSpiral(ArrayList<BossBullet> bullet, int x, int y, int x2, int y2) {
//		Left
		
		bullet.add(new BossBullet(x-5, y-10, 10, 20, 1.9, "Inverse"));
		
		bullet.get(bullet.size()-1).setAngle(angle+90);
		while (bullet.get(bullet.size()-1).getAngle() > 360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
		}
		
		bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle)));     
		bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle)));
		
//		Right

		bullet.add(new BossBullet(x2-5, y2-10, 10, 20, 1.9, "Inverse"));
		
		bullet.get(bullet.size()-1).setAngle(-angle);
		while (bullet.get(bullet.size()-1).getAngle() > 360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
		}
		
		bullet.get(bullet.size()-1).setAngleX(Math.sin(Math.toRadians(angle)));     
		bullet.get(bullet.size()-1).setAngleY(Math.cos(Math.toRadians(angle)));

		angle += 60.35;
		if (angle > 360)
			angle -= 360;
	}

	public void circle(ArrayList<BossBullet> bullet, int x, int y, int rate) {
		if (duration % rate == 0) {
			for (int i = 0; i < 50; i++) {
				bullet.add(new BossBullet(x - 15, y - 15, 30, 30, 1, "Orb"));
				
				bullet.get(bullet.size()-1).setAngle(-angle+90);
				while (bullet.get(bullet.size()-1).getAngle() > 360) {
					bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
				}
				
				bullet.get(bullet.size()-1).setAngleX(Math.sin(Math.toRadians(angle)));     
				bullet.get(bullet.size()-1).setAngleY(Math.cos(Math.toRadians(angle)));
				
				angle += 7.2;
			}
		}
		
		duration--;
	}

	public void chaser(ArrayList<BossBullet> bullet, int startX, int startY, int targetX, int targetY) {
//		Fires the parent bullet
		if (duration % 100 == 0) {
			bullet.add(new BossBullet(startX-15, startY-15, 30, 30, 5.4, "Orb"));
			
			bullet.get(bullet.size()-1).setAngle(Math.atan2(startX-targetX, startY-targetY)); 
			while (bullet.get(bullet.size()-1).getAngle() > 360) {
				bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
			}
			
			bullet.get(bullet.size()-1).setAngleX(Math.sin(bullet.get(bullet.size()-1).getAngle()));     
			bullet.get(bullet.size()-1).setAngleY(Math.cos(bullet.get(bullet.size()-1).getAngle()));
			
			this.targetX.add(targetX);
			this.targetY.add(targetY);
		}
		
//		Fires the child bullets
		for (int i = 0; i < bullet.size(); i++) {
			for (int j = 0; j < this.targetX.size(); j++) {
				if (bullet.get(i).getType() == "Orb") {
					if (bullet.get(i).getX() >= this.targetX.get(j) - 30 && bullet.get(i).getY() >= this.targetY.get(j) - 30 && 
							bullet.get(i).getX() <= this.targetX.get(j) + 30 && bullet.get(i).getY() <= this.targetY.get(j) + 30) {
						for (int b = 0; b < 50; b++) {
							bullet.add(new BossBullet(bullet.get(i).getX() + 5, bullet.get(i).getY() + 10, 10, 20, 0.5, "Directed"));
							
							bullet.get(bullet.size()-1).setAngle(-angle);
							while (bullet.get(bullet.size()-1).getAngle() > 360) {
								bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
							}
							
							bullet.get(bullet.size()-1).setAngleX(Math.sin(Math.toRadians(angle)));     
							bullet.get(bullet.size()-1).setAngleY(Math.cos(Math.toRadians(angle)));
							
							angle += 7.2;
						}
						bullet.remove(i);
						this.targetX.remove(j);
						this.targetY.remove(j);
					}
				}
			}
		}
		
		duration--;
	}
	
//	Spell Circles
	
	public void imperishableLotus(ArrayList<BossBullet> bullet, int x, int y) {
//		6 spell circles revolve around Odin, all shooting their own stream of bullets.
//		6 more spell circles also revolve around Odin, shooting streams of bullets in the other direction of the first 6.
//		Odin rapidly uses the "Circle" spell.
		if (duration % 50 == 0) {
			for (int i = 0; i < 50; i++) {
				bullet.add(new BossBullet(x - 10, y - 20, 20, 40, 4.4, "Fireball"));
				
				bullet.get(bullet.size()-1).setAngle(-angle);
				while (bullet.get(bullet.size()-1).getAngle() > 360) {
					bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
				}
				
				bullet.get(bullet.size()-1).setAngleX(Math.sin(Math.toRadians(angle)));     
				bullet.get(bullet.size()-1).setAngleY(Math.cos(Math.toRadians(angle)));
				
				angle += 7.2;
			}
			angle += 0.4;
		}
		
//		angleX = getX() + (Math.sin(angle)*h);
//		angleY = getY() + (Math.cos(angle)*h);
		
		angle += 1;
		
		duration--;
		
	}
	
	public void absoluteChaos(ArrayList<BossBullet> bullet, int x, int y) {
		int type = r.nextInt(7);
		double speed = r.nextInt(10)+1;
		
		switch (type) {
		
		case 0:
			bullet.add(new BossBullet(x-5, y-10, 10, 20, speed, "Directed"));
			break;
		case 1:
			bullet.add(new BossBullet(x-5, y-10, 10, 20, speed, "Inverse"));
			break;
		case 2:
			bullet.add(new BossBullet(x-15, y-15, 30, 30, speed, "Orb"));
			break;
		case 3:
			bullet.add(new BossBullet(x-10, y-20, 20, 40, speed, "Fireball"));
			break;
		case 4:
			bullet.add(new BossBullet(x-10, y-15, 20, 30, speed, "Missile"));
			break;
		case 5:
			bullet.add(new BossBullet(x-10, y-10, 20, 20, speed, "Ringed"));
			break;
		case 6:
			bullet.add(new BossBullet(x-9, y-9, 18, 18, speed, "Star"));
			break;
		default:
			System.out.println("Bullet not found.");
			break;
		
		}
		bullet.get(bullet.size()-1).setX(bullet.get(bullet.size()-1).getX()-(bullet.get(bullet.size()-1).getW()/2));
			
		while (bullet.get(bullet.size()-1).getAngle() > 360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
		}
		bullet.get(bullet.size()-1).setAngle(angle);
		bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle+90)));     
		bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle+90)));
			
		angle += r.nextInt(360)+(r.nextInt(999)/1000);
		if (angle > 360)
			angle -= 360;

		type = r.nextInt(7);
		speed = r.nextInt(10)+1;

		switch (type) {
		
		case 0:
			bullet.add(new BossBullet(x-5, y-10, 10, 20, speed, "Directed"));
			break;
		case 1:
			bullet.add(new BossBullet(x-5, y-10, 10, 20, speed, "Inverse"));
			break;
		case 2:
			bullet.add(new BossBullet(x-15, y-15, 30, 30, speed, "Orb"));
			break;
		case 3:
			bullet.add(new BossBullet(x-10, y-20, 20, 40, speed, "Fireball"));
			break;
		case 4:
			bullet.add(new BossBullet(x-10, y-15, 20, 30, speed, "Missile"));
			break;
		case 5:
			bullet.add(new BossBullet(x-10, y-10, 20, 20, speed, "Ringed"));
			break;
		case 6:
			bullet.add(new BossBullet(x-9, y-9, 18, 18, speed, "Star"));
			break;
		default:
			System.out.println("Bullet not found.");
			break;
		
		}
		bullet.get(bullet.size()-1).setX(bullet.get(bullet.size()-1).getX()-(bullet.get(bullet.size()-1).getW()/2));
			
		while (bullet.get(bullet.size()-1).getAngle() > 360) {
			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
		}
		bullet.get(bullet.size()-1).setAngle(angle);
		bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle+90)));     
		bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle+90)));
			
		angle += r.nextInt(360)+(r.nextInt(999)/1000);
		if (angle > 360)
			angle -= 360;
	}
	
//	public void accelerate(ArrayList<BossBullet> bullet, int x, int y) {
//		
//		bullet.add(new BossBullet(x, y, 10, 20, 1.5, "Directed"));
//		
//		bullet.get(bullet.size()-1).setAngle(angle+90);
//		while (bullet.get(bullet.size()-1).getAngle() > 360) {
//			bullet.get(bullet.size()-1).setAngle(bullet.get(bullet.size()-1).getAngle() - 360);
//		}
//		
//		bullet.get(bullet.size()-1).setAngleX(Math.cos(Math.toRadians(angle)));     
//		bullet.get(bullet.size()-1).setAngleY(Math.sin(Math.toRadians(angle)));
//		
//		angle += 30.25;
//		duration--;
//		
//		if (duration % 100 == 0) {
//			angle *= 1.75;
//		}
//		
//		if (angle > 360)
//			angle -= 360;
//	}
}
