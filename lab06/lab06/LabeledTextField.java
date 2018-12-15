package lab06;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Dimension;

class LabeledTextField extends JPanel{
	private JLabel label = new JLabel();
	private JTextField textField = new JTextField();

	public LabeledTextField(String label){
		this.label.setText(label);
		this.label.setFont(this.label.getFont().deriveFont(10f));

		this.textField.setPreferredSize(new Dimension(100,20));

		this.setLayout(new GridLayout(2,1));

		this.add(this.label);
		this.add(this.textField);
		this.setVisible(true);
	}
}
