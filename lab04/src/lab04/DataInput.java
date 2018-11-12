package lab04;

import javax.swing.*;

public class DataInput extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public JLabel label = new JLabel();
	public JTextField textfield = new JTextField();
	
	{
		this.add(label);
		this.add(textfield);
	}
	
	DataInput(String label)
	{
		this(label, 10);
	}
	
	DataInput(String label, int textFieldWidth)
	{
		this.label.setText(label);
		this.textfield.setColumns(textFieldWidth);
		this.setVisible(true);
	}
}
