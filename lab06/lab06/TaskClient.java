package lab06;

import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.*;

class TaskClient{
	private Socket connection;
	private TaskClientInterface iface;

	public static void main(String[] args)throws IOException{
		TaskClient client = new TaskClient();
	}

	TaskClient() throws IOException{
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
		public void callback(String[] args){
			System.out.println(args[0]);
			System.out.println(args[1]);
			this.parent.iface.getServerInfoForm().disable();
		}
	}
}
