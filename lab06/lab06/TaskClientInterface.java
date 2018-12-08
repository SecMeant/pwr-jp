package lab06;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

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

	public void disable(){
		this.hostNameField.setEnabled(false);
		this.hostPort.setEnabled(false);
	}

	public void enable(){
		this.hostNameField.setEnabled(true);
		this.hostPort.setEnabled(true);
	}
}

interface FormSubmitListener{
	public void callback(String[] args);
}

class MainWindow extends JFrame{
	JPanel mainPanel = new JPanel();
	ServerInfoForm serverInfoForm = new ServerInfoForm(new ServerFormSubmitListener(this));

	FormSubmitListener formSubmitListener = null;
	
	MainWindow(){
		this.initWindow();
	}

	MainWindow(FormSubmitListener listener){
		this.formSubmitListener = listener;
		this.initWindow();
	}

	public void addFormSubmitListener(FormSubmitListener listener){
		this.formSubmitListener = listener;
	}

	private void initWindow(){
		this.mainPanel.add(this.serverInfoForm);

		this.add(this.mainPanel);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private class ServerFormSubmitListener implements ActionListener{
		MainWindow parent;

		ServerFormSubmitListener(MainWindow parent){
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent e){
			if(this.parent.formSubmitListener != null)
				this.parent.formSubmitListener.callback(this.parent.serverInfoForm.getInput());
		}
	}
}

class TaskClientInterface{
	private TaskClient parent;

	private MainWindow window = new MainWindow();

	TaskClientInterface(TaskClient parent){
		System.out.println("Init gui");
		this.parent = parent;

		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowGui();
			}
		});
	}

	public void createAndShowGui(){
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setSize(400,300);
	}
	
	public ServerInfoForm getServerInfoForm(){
		return this.window.serverInfoForm;
	}

	public MainWindow getWindow(){
		return this.window;
	}
}
