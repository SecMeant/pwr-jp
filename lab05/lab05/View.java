package lab05;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;

class View{
	public View(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
			createAndShowGui();
			}
		});
	}

	public static void createAndShowGui(){
		JFrame f = new JFrame("Swing pain demo");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new MyPanel());
		f.pack();
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}

class MyPanel extends JPanel{
	
	private int squareX = 50;
	private int squareY = 50;
	private final int squareW = 50;
	private final int squareH = 50;

	public MyPanel(){
		setBorder(BorderFactory.createLineBorder(Color.black));

		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e){
				moveSquare(e.getX(), e.getY());
			}
		});

		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				moveSquare(e.getX(), e.getY());
			}
		});
	}

	public Dimension getPreferredSize(){
		return new Dimension(800, 600);
	}

	public void moveSquare(int x, int y){
		squareX = x;
		squareY = y;
	}
	
	public void paintComponent(Graphics g){
		repaint();
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(squareX-squareW/2,squareY-squareH/2,squareW,squareH);
		g.setColor(Color.BLACK);
		g.drawRect(squareX-squareW/2,squareY-squareH/2,squareW,squareH);
	}
}

