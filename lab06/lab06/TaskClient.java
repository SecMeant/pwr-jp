package lab06;

import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;

class TaskClient{
	private Socket connection;
	private TaskClientInterface iface;

	public static void main(String[] args)throws IOException{
		TaskClient client = new TaskClient();
		client.iface = new TaskClientInterface(client);
	}

	TaskClient() throws IOException{
		this.connection = new Socket(TaskServer.LISTEN_IP, TaskServer.LISTEN_PORT);

		if(this.connection.isConnected()){
			System.out.println("Connected");
		}

		OutputStream out = this.connection.getOutputStream();
		String msg = new String("Hello world\n");
		out.write(msg.getBytes());
	}
}
