package lab06;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class TaskServer extends Thread{
	public static final int LISTEN_PORT = 1337;
	public static final String LISTEN_IP = "0.0.0.0";
	public static final String TASK_FILE_NAME = "tasks.dat";

	ServerSocket listenSocket;

	TaskServer()throws IOException{
		this.listenSocket = new ServerSocket(LISTEN_PORT);
		this.setDaemon(true);
	}

	public void run(){
		this.listenLoop();
	}

	private void listenLoop(){
		for(;;){
			try{
			Socket sock = this.listenSocket.accept();
			System.out.println("Accepted");
			this.handleConnection(sock);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	private void handleConnection(Socket sock) throws IOException{
		BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		String msg = in.readLine();
		if(msg.equals("Hello world")){
			System.out.println(msg);
			System.exit(0);
		}
	}
}

