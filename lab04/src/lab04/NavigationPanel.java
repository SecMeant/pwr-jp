package lab04;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class NavigationPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JButton forwardButton;
	private JButton backwardButton;
	
	{
		this.forwardButton = new JButton(">");
		this.backwardButton = new JButton("<");

		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		this.add(this.forwardButton, c);
		
		c.gridx = 0;
		this.add(this.backwardButton, c);
	}
}
