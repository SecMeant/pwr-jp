package lab06;

import javax.swing.*;

import java.awt.event.ActionListener;

class ServerInfoForm extends JPanel{
	JTextField hostNameField = new JTextField(10);
	JTextField hostPort = new JTextField(10);
	JButton button = new JButton("Connect");

	ServerInfoForm(ActionListener buttonPressedListener){
		this.add(hostNameField);
		this.add(hostPort);
		this.add(button);

		this.button.addActionListener(buttonPressedListener);

		this.setVisible(true);
	}

	public String[] getInput(){
		String[] ret = new String[2];

		ret[0] = this.hostNameField.getText();
		ret[1] = this.hostPort.getText();

		return ret;
	}

	public void clearInput(){
		this.hostNameField.setText("");
		this.hostNameField.setText("");
	}

	public void disableInput(){
		this.hostNameField.setEnabled(false);
		this.hostPort.setEnabled(false);
	}

	public void enableInput(){
		this.hostNameField.setEnabled(true);
		this.hostPort.setEnabled(true);
	}
}

