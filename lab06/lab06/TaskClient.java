package lab06;

import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;

import java.net.UnknownHostException;

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

	private void connect(String host, String port) throws IOException{
		System.out.println("Connecting . . .");

		try{
			this.connection = new Socket(host, Integer.parseInt(port));
			this.iface.setStateConnected(true);
		}catch(NumberFormatException e){
			this.signalError("Error! Port must be a number");
		}catch(UnknownHostException e){
			this.signalError("Error! server host unknown");
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
