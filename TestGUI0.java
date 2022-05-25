import java.awt.*;
import javax.swing.*;

//Remember we've also got to make a pop-up window or something when no path is found

public class TestGUI0 {
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("This is my name :)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		
		//frame.add(new Canvas());
		
		frame.setVisible(true);
		
		TestGraphPanel tgp = new TestGraphPanel();
		frame.add(tgp);
		
	}
	
}