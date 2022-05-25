import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI2 extends JFrame{

    public GUI2() {
        super("Algorithm");
        buildGUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(500,200);
        setSize(400,400);

        setVisible(true);
    }

    public void buildGUI(){
        setLayout(new BorderLayout());
        Container contentpane = getContentPane();
        ResultPanel resultPanel = new ResultPanel();
        //Panel used to draw the graph

        contentpane.add(resultPanel, BorderLayout.CENTER);
        
        JPanel p = new JPanel();
        JTextField e1 = new JTextField(3);
        JTextField e2 = new JTextField(3);
        JTextField e3 = new JTextField(3);
        
        JButton button = new JButton("Open sesame");

        p.add(new JLabel("X position"));
        p.add(e1);
        p.add(new JLabel("Y Position"));
        p.add(e2);
        p.add(new JLabel("D"));
        p.add(e3);
        p.add(button);

        contentpane.add(p, BorderLayout.SOUTH);
        button.addActionListener(new DrawingActionListener(e1, e2, e3, resultPanel));
        //working listener for when button is pressed.
    }

    public static void main(String[] args) {
        new GUI2();
    }

    //Panel that will have Graph as the result 
    class ResultPanel extends JPanel{
        int x,y,d;

        public void paint(Graphics g) {
            g.clearRect(0,0,getWidth(),getHeight());
            g.drawLine(50, 250, 350, 250);

            for (int i=1; i<11; i++){
                g.drawString(i*10+ "", 25, 255-(20*i));
                g.drawLine(50,250-(20*i), 350, 250-(20*i));
            }

            g.drawLine(50,20,50,250);
            g.drawString("x",100,270);
            g.drawString("y",200, 270);
            g.drawString("d",300,270);
            g.setColor(Color.BLUE);

            if(x > 0)
            g.fillRect(110,250-x*2,10,x*2);
            if(y > 0)
            g.fillRect(210,250 - y*2,10,y*2);
            if(d>0)
            g.fillRect(310,250 - d*2,10,d*2);

        }
        void setValue(int x, int y, int d){
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }

    class DrawingActionListener implements ActionListener{
        JTextField e1,e2,e3;
        ResultPanel resultPanel;

        DrawingActionListener(JTextField e1, JTextField e2, JTextField e3,ResultPanel resultPanel){
            this.e1 = e1;
            this.e2 = e2;
            this.e3 = e3;
            this.resultPanel = resultPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int x = Integer.parseInt(e1.getText());
                int y = Integer.parseInt(e2.getText());
                int d = Integer.parseInt(e3.getText());
                resultPanel.setValue(x,y,d);
                resultPanel.repaint();
            }
            catch(NumberFormatException nfe){
                JOptionPane.showMessageDialog(resultPanel, "Input, again!", "error message", JOptionPane.ERROR_MESSAGE);

            }
        }

        
    }
    
}
