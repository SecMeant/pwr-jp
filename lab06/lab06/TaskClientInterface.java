package lab06;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.io.IOException;

class MainWindow extends JFrame{
	public static final int WINDOW_WIDTH = 400;
	public static final int WINDOW_HEIGHT = 400;

	private JPanel mainPanel = new JPanel();
	public ServerInfoForm serverInfoForm =
		new ServerInfoForm(new ServerFormSubmitListener(this));
	private MessageManager messageManager = new MessageManager();
	private TaskList taskList = new TaskList();

	FormSubmitListener connectFormSubmitListener = null;
	
	MainWindow(){
		this.initWindow();
	}

	MainWindow(FormSubmitListener listener){
		this.connectFormSubmitListener = listener;
		this.initWindow();
	}

	public void addFormSubmitListener(FormSubmitListener listener){
		this.connectFormSubmitListener = listener;
	}

	public MessageManager getMessageManager(){
		return this.messageManager;
	}

	private void initWindow(){
		this.setResizable(false);
		this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
		this.mainPanel.add(this.serverInfoForm);
		this.mainPanel.add(this.taskList);
		this.mainPanel.add(this.messageManager);

		
		this.taskList.addElement("asdf");

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
				if(this.parent.connectFormSubmitListener != null)
					this.parent.connectFormSubmitListener.callback(this.parent.serverInfoForm.getInput());
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
		this.window.setSize(MainWindow.WINDOW_WIDTH,MainWindow.WINDOW_HEIGHT);
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
			this.window.getMessageManager().addMessageSuccess("Connected to the server");
		}else{
			this.window.serverInfoForm.enableInput();
			this.window.serverInfoForm.button.setText("Connect");
			this.window.getMessageManager().addMessageForced("Disconnected from the server");
		}
	}
}
