import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	GUI d;
	
	public KeyInput(GUI di) {
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
