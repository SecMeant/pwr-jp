package lab06;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;

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
		while(true){
			this.getMessage(sock);
		}
	}

	private static boolean checkHeader(int reqType, int dataSize){
		if(reqType != 1 && reqType != 2){
			return false;
		}

		if(dataSize > 512){
			return false;
		}

		return true;
	}

	private static void terminateConnection(Socket sock){
		try{
			sock.shutdownInput();
			sock.shutdownOutput();
			sock.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void getMessage(Socket sock) throws IOException{
		int reqType = 0;
		int dataSize = 0;
		DataInputStream in = new DataInputStream(sock.getInputStream());
		String[] data;
		try{
			reqType = in.readInt();
			dataSize = in.readInt();

			if(!checkHeader(reqType, dataSize)){
				this.terminateConnection(sock);
				System.exit(0);
			}

			byte[] buff = new byte[dataSize];
			in.readFully(buff, 0, dataSize);
			data = (new String(buff)).split("\0");

			System.out.printf("Op: %s\n",data[0]);
			System.out.printf("Args: %s\n",data[1]);
			
		}catch(NullPointerException e){
			System.out.println("Connection closed");
		}
	}
}

