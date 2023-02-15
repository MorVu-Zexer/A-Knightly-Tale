import javax.swing.JFrame;

public class Requiem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame w = new JFrame();
        w.setSize(1200,825);
        w.setResizable(false);
        w.setLocationRelativeTo(null);
        w.setTitle("Project Requiem");
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUI m = new GUI();
        w.add(m);
		w.addKeyListener(new KeyInput(m));
        w.setVisible(true);
	}

}
