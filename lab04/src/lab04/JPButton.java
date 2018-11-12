package lab04;

import javax.swing.*;

// Wrapper class to make button behave like panel in grid layout
// This allows the button to have smaller size
// Layout manager keep resizing element added to gridlayout
public class JPButton extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JButton button = new JButton();
	
	{
		this.add(this.button);
	}

	JButton get() {return this.button;}
}
