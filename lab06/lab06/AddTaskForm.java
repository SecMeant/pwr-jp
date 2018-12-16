package lab06;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.Dimension;

import java.awt.event.ActionListener;

class AddTaskForm extends JPanel{
	private TitledBorder border = new TitledBorder("Add task form");

	private LabeledTextField operationField = new LabeledTextField("op");
	private LabeledTextField argsField = new LabeledTextField("args");

	private JButton button = new JButton("Send");

	public AddTaskForm(){
		this.border.setTitleJustification(TitledBorder.CENTER);
		this.border.setTitlePosition(TitledBorder.TOP);

		this.setBorder(this.border);

		this.add(this.operationField);
		this.add(this.argsField);

		this.button.setPreferredSize(new Dimension(70,35));
		this.add(this.button);

		this.setVisible(true);
	}

	public void addButtonPressedListener(ActionListener listener){
		this.button.addActionListener(listener);
	}

	public String[] getInput(){
		String[] ret = new String[2];

		ret[0] = this.operationField.getText();
		ret[1] = this.argsField.getText();

		return ret;
	}
}
