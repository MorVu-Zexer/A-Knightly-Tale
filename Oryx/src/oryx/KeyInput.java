package oryx;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class KeyInput extends KeyAdapter{
	Display d;
	
	public KeyInput(Display di) {
		// TODO Auto-generated constructor stub
		d = di;
	}
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		d.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		d.keyReleased(e);
	}

}
