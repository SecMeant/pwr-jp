package lab04;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SummaryPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private JLabel personalAverage = new JLabel();
	private JLabel classAverage = new JLabel();
	
	{
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.add(this.personalAverage,c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(this.classAverage,c);
		
		this.personalAverage.setVisible(true);
		this.classAverage.setVisible(true);
	}
	
	public void setPersonalAverage(String newValue)
	{
		this.personalAverage.setText("Personal average: " + newValue);
	}
	
	public void setClassAverage(String newValue)
	{
		this.classAverage.setText("Class average: " + newValue);
	}
}
