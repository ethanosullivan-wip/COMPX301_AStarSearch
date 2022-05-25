import java.awt.*;
import javax.swing.JPanel;
import java.util.*;
import java.awt.geom.*;

public class GraphPanel extends JPanel {
	
	//What sort of variables/lists do we need?
	//	- Stars/points
	ArrayList<Point> dots;
	double largestX, largestY;
	double scaleFactorX, scaleFactorY;
	
	ArrayList<Line2D> lines;
	
	Graphics2D guhuh;
	
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
		
		scaleFactorX =  GRAPH_WIDTH / (largestX + 10);
		scaleFactorY =  GRAPH_HEIGHT / (largestY + 10);
		
		lines = new ArrayList<Line2D>();
		
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
	
	public void paintPath(ArrayList<Integer> pathPoints) {
		
		Line2D newLine;
		
		for ( int i = 1; i < pathPoints.size(); i++ ) {
			double lineX1 = dots.get( pathPoints.get(i) ).x  * scaleFactorX;
			double lineY1 = dots.get( pathPoints.get(i) ).y * scaleFactorY;
			double lineX2 = dots.get( pathPoints.get(i-1) ).x * scaleFactorX;
			double lineY2 = dots.get( pathPoints.get(i-1) ).y * scaleFactorY;
			
			newLine = new Line2D.Double( lineX1, lineY1, lineX2, lineY2 );
			lines.add(newLine);
		}
		
		this.repaint();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {	//pretty sure the system that calls this gives it the graphics object
		super.paintComponent(g);
		
		Graphics2D guhuh = (Graphics2D) g;
		
		guhuh.setColor(Color.BLACK);
		
		
		//guhuh.fillOval(x1, y1+200, width, height);
		
		
		
		for (int i=0; i < dots.size(); i++) {
			guhuh.fillOval( (int)(dots.get(i).x * scaleFactorX + 10) , GRAPH_HEIGHT - (int)(dots.get(i).y * scaleFactorY + 10) , STAR_SIZE, STAR_SIZE );
			
		}
		
		guhuh.setColor(Color.GREEN);
		
		for (int i=0; i < lines.size(); i++) {
			guhuh.draw( lines.get(i) );
		}
	}
	
	
}