import java.awt.*;
import javax.swing.JPanel;
import java.util.*;

public class GraphPanel extends JPanel {
	
	//What sort of variables/lists do we need?
	//	- Stars/points
	ArrayList<Point> dots;
	double largestX, largestY;
	
	public static final int STAR_SIZE = 5;
	public static final int GRAPH_WIDTH = 500;
	public static final int GRAPH_HEIGHT = 400;
	//	- lines
	//	- final path lines (should they even be separate? yes, but only if we do the during-runtime-display idea)
	
	//When created we could pass it some parameters, maybe star locations
	public GraphPanel(ArrayList<Point> points, double _largestX, double _largestY) {
		setUpPanel();
		
		dots = points;
		
		largestX = _largestX;
		largestY = _largestY;
		
	}
	
	public void setUpPanel() {
		this.setBackground(Color.CYAN);
		this.setVisible(true);
		//Maybe preset the size of the window/panel? That'd be the frame I want to set
		//this.setMinimumSize(new Dimension(200, 200));
		
		//largestX*1.25, largestY*1.25
		
		this.setSize(new Dimension(GRAPH_WIDTH, GRAPH_HEIGHT));
		this.setLocation(100, 150);
		//Fill in star locations now?
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {	//pretty sure the system that calls this gives it the graphics object
		super.paintComponent(g);
		
		Graphics2D guhuh = (Graphics2D) g;
		
		guhuh.setColor(Color.BLACK);
		
		
		//guhuh.fillOval(x1, y1+200, width, height);
		
		double scaleFactorX =  GRAPH_WIDTH / (largestX + 10);
		double scaleFactorY =  GRAPH_HEIGHT / (largestY + 10);
		
		for (int i=0; i < dots.size(); i++) {
			guhuh.fillOval( (int)(dots.get(i).x * scaleFactorX + 10) , GRAPH_HEIGHT - (int)(dots.get(i).y * scaleFactorY + 10) , STAR_SIZE, STAR_SIZE );
			
		}
		/*
		for (int i=0; i < dots2.size(); i++) {
			guhuh.fillOval( dots2.get(i).x, dots2.get(i).y, STAR_SIZE, STAR_SIZE );
			
		}
		*/
		
	}
	
	
}