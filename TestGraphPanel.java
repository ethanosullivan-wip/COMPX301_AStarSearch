import java.awt.*;
import javax.swing.JPanel;
import java.util.*;

public class TestGraphPanel extends JPanel {
	
	//What sort of variables/lists do we need?
	//	- Stars/points
	ArrayList<Point> dots;
	
	ArrayList<Point> dots2;
	public static final int STAR_SIZE = 2;
	//	- lines
	//	- final path lines (should they even be separate? yes, but only if we do the during-runtime-display idea)
	
	//When created we could pass it some parameters, maybe star locations
	public TestGraphPanel(ArrayList<Point> points) {
		setUpPanel();
		
		dots = points;
		
		dots2 = new ArrayList<Point>();
		
		dots2.add(new Point(50, 50));
		dots2.add(new Point(150, 150));
		dots2.add(new Point(150, 300));
	}
	
	public void setUpPanel() {
		this.setBackground(Color.CYAN);
		this.setVisible(true);
		//Maybe preset the size of the window/panel? That'd be the frame I want to set
		//this.setMinimumSize(new Dimension(200, 200));
		
		this.setSize(new Dimension(500, 400));
		this.setLocation(new Dimension(100, 100));
		//Fill in star locations now?
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {	//pretty sure the system that calls this gives it the graphics object
		super.paintComponent(g);
		
		//Apparently the Graphics2D class is a good move?
		Graphics2D guhuh = (Graphics2D) g;
		
		guhuh.setColor(Color.GREEN);
		
		int x1 = 50;
		int y1 = 50;
		int x2 = 550;
		int y2 = 150;
		
		int width = 100;
		int height = 100;
		
		guhuh.drawLine(x1, y1, x2, y2);
		
		guhuh.fillRect(x1+width, y1, width, height);
		
		guhuh.setColor(Color.BLACK);
		
		
		//guhuh.fillOval(x1, y1+200, width, height);
		
		for (int i=0; i < dots.size(); i++) {
			guhuh.fillOval( dots.get(i).x, dots.get(i).y, STAR_SIZE, STAR_SIZE );
			
		}
		/*
		for (int i=0; i < dots2.size(); i++) {
			guhuh.fillOval( dots2.get(i).x, dots2.get(i).y, STAR_SIZE, STAR_SIZE );
			
		}
		*/
		
	}
	
	
}