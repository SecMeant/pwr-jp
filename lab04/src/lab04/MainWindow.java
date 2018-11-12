package lab04;

import javax.swing.*;

public class MainWindow extends JFrame
{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;

	JLabel label;
	
	public MainWindow(String string) {
		// delegate construction
		super(string);
		
		// setup window
		this.setSize(MainWindow.WIDTH, MainWindow.HEIGHT);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		
		this.label = new JLabel("LABEL");
		
		this.getContentPane().add(this.label);
		
		this.setLocationRelativeTo(null); // center
		this.setVisible(true);
	}

	private static final long serialVersionUID = 1L;
	
}
