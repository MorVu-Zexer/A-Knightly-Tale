package oryx;
import java.io.IOException;

import javax.swing.JFrame;

public class Oryx {
		public static JFrame frame = new JFrame();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		frame.setSize(1000, 650);
		frame.setTitle("Oryx");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		Display display = new Display();
		frame.add(display);
		frame.addKeyListener(new KeyInput(display));
		frame.addMouseListener(display);
		frame.addMouseMotionListener(display);
		frame.setVisible(true);

	}

}
