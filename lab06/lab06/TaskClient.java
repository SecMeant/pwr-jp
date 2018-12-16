package lab06;

import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;

import java.net.UnknownHostException;
import java.lang.IllegalArgumentException;

import javax.swing.*;

class TaskClient{
	private Socket connection;
	private TaskClientInterface iface;

	public static void main(String[] args)throws IOException{
		TaskClient client = new TaskClient();
	}

	TaskClient() throws IOException{
		this.connection = new Socket();

		this.iface = new TaskClientInterface(this);
		this.iface.getWindow().addFormSubmitListener(new ServerFormListener(this));
	}

	private void test()throws IOException{
		OutputStream out = this.connection.getOutputStream();
		String msg = new String("Hello world\n");
		out.write(msg.getBytes());
	}

	private class ServerFormListener implements FormSubmitListener{
		private TaskClient parent;

		ServerFormListener(TaskClient parent){
			this.parent = parent;
		}

		@Override
		public void callback(String[] args) throws IOException{
			if(!this.parent.connection.isConnected() || this.parent.connection.isClosed())
				this.parent.connect(args[0], args[1]);
			else
				this.parent.disconnect();
		}
	}

	private class AddTaskListener implements FormSubmitListener{
		private TaskClient parent;

		public AddTaskListener(TaskClient parent){
			this.parent = parent;
		}	

		@Override
		public void callback(String[] args) throws IOException{
			if(!this.parent.isConnected()){
				this.parent.signalError("Error! In order to add task, you need to be connected to the server.");
			}
		}
	}

	private boolean isConnected(){
		return this.connection.isConnected() && !this.connection.isClosed();
	}

	private void connect(String host, String port) throws IOException{
		System.out.println("Connecting . . .");

		try{
			this.connection = new Socket(host, Integer.parseInt(port));
			this.iface.setStateConnected(true);
		}catch(NumberFormatException e){
			this.signalError("Error! Port must be a number");
			System.err.println(e.getMessage());
		}catch(UnknownHostException e){
			this.signalError("Error! server host unknown");
			System.err.println(e.getMessage());
		}catch(java.net.ConnectException e){
			this.signalError("Connection refused by server");
			System.err.println(e.getMessage());
		}catch(java.lang.IllegalArgumentException e){
			this.signalError("Error! Port of out range");
			System.err.println(e.getMessage());
		}
	}

	private void signalError(String message){
		this.iface.getWindow().getMessageManager().addMessageError(message);
	}

	private void signalSuccess(String message){
		this.iface.getWindow().getMessageManager().addMessageSuccess(message);
	}

	private void disconnect() throws IOException{
		System.out.println("Disconnecting");
		
		this.connection.shutdownInput();
		this.connection.shutdownOutput();
		this.connection.close();
		this.iface.setStateConnected(false);
	}
}
