import javax.swing.JOptionPane;
import java.awt.*;
import javax.swing.*;
import java.util.*;

//Remember we've also got to make a pop-up window or something when no path is found

public class GUI {
	
	public static void main(String[] args) {
		
		ArrayList<Point> emptyPoints = new ArrayList<Point>();
		
		GUI gui = new GUI(emptyPoints);
		
	}

	public void popup(){
		JOptionPane.showMessageDialog(null, "There is no path found");
	}
	
	public GUI(ArrayList<Point> points) {
		JFrame frame = new JFrame("This is my name :)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		
		//frame.add(new Canvas());
		
		JPanel everything = new JPanel();
		everything.setBackground(Color.LIGHT_GRAY);
		everything.setLayout(null);
		frame.setContentPane(everything);
		//frame.getContentPane().add()...
		
		TestGraphPanel tgp = new TestGraphPanel(points);
		everything.add(tgp);
		
		JPanel emptyPanel = new JPanel();
		emptyPanel.setBackground(Color.BLUE);
		emptyPanel.setSize(new Dimension(100, 100));
		everything.add(emptyPanel);
		
		//frame.pack();
		
		frame.setVisible(true);
	}
	
	
	
}