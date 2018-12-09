package lab06;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.io.IOException;

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
			try{
				if(this.parent.formSubmitListener != null)
					this.parent.formSubmitListener.callback(this.parent.serverInfoForm.getInput());
			}catch(IOException excp){
				excp.printStackTrace();
			}
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

	public void setStateConnected(boolean state){
		if(state){
			this.window.serverInfoForm.disableInput();
			this.window.serverInfoForm.button.setText("Disconnect");
		}else{
			this.window.serverInfoForm.enableInput();
			this.window.serverInfoForm.button.setText("Connect");
		}
	}
}
