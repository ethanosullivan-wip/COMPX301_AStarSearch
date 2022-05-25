
import java.awt.*;
import javax.swing.JPanel;

public class TestGraphPanel extends JPanel {
	
	//What sort of variables/lists do we need?
	//	- Stars/points
	//	- lines
	//	- final path lines (should they even be separate? yes, but only if we do the during-runtime-display idea)
	
	//When created we could pass it some parameters, maybe star locations
	public TestGraphPanel() {
		setUpPanel();
	}
	
	public void setUpPanel() {
		this.setBackground(Color.DarkGrey);
		//Maybe preset the size of the window/panel?
		//Fill in star locations now?
	}
	
	@Override
	public void paintComponent(Graphics g) {	//pretty sure the system that calls this gives it the graphics object
		super.paintComponent();
		
		//Apparently the Graphics2D class is a good move?
		Graphics2D guhuh = (Graphics2D) g;
	}
	
	
}