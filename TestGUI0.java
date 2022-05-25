import javax.swing.JOtionPane;
import java.awt.*;
import javax.swing.*;
import java.util.*;

//Remember we've also got to make a pop-up window or something when no path is found

public class TestGUI0 {
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("This is my name :)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		
		//frame.add(new Canvas());
		
		ArrayList<Point> emptyPoints = new ArrayList<Point>();
		
		TestGraphPanel tgp = new TestGraphPanel(emptyPoints);
		frame.getContentPane().add(tgp);
		
		//frame.pack();
		
		frame.setVisible(true);
		
	}

	public void popup(){
		JOtionPane.showMessageDialog(null, "There is no path found");
	}
	
	public TestGUI0(ArrayList<Point> points) {
		JFrame frame = new JFrame("This is my name :)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		
		//frame.add(new Canvas());
		
		
		
		TestGraphPanel tgp = new TestGraphPanel(points);
		frame.getContentPane().add(tgp);
		
		//frame.pack();
		
		frame.setVisible(true);
	}
	
	
	
}