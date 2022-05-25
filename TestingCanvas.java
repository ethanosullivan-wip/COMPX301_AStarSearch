//import javax.swing.*;
import java.awt.Graphics;
import java.awt.Canvas;
import javax.swing.JFrame;

//JPanel for drawing in??

public class TestingCanvas extends Canvas {
	//uh IDK restarting
	public static void main(String[] args) {
		
		Canvas c = new Canvas();
		JFrame f = new JFrame();
		Graphics g = new Graphics();	//I think we'll need to make an instance class that implements this
		
		f.add(c);
		f.setSize(400, 400);
		
		f.setVisible(true);
		
		g.drawRect(200, 200, 300, 300);
		
	}
	
}