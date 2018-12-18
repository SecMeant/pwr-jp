package lab06;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

class TaskServer extends Thread{
	public static final int LISTEN_PORT = 1337;
	public static final String LISTEN_IP = "0.0.0.0";
	public static final String TASK_FILE_NAME = "tasks.dat";

	public static final String[] operations =
		new String[]{"add","sub","mod","mul","div"};

	private List<String> tasks = new ArrayList<String>();

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
			if(!this.handleRequest(sock)){
				this.terminateConnection(sock);
				break;
			}
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

	// Returns false if request was invalid
	// true otherwise
	private boolean handleRequest(Socket sock) throws IOException{
		int reqType = 0;
		int dataSize = 0;
		DataInputStream in = new DataInputStream(sock.getInputStream());
		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		String[] data;
		try{
			reqType = in.readInt();
			dataSize = in.readInt();

			if(!checkHeader(reqType, dataSize)){
				this.terminateConnection(sock);
				return false; // request incorrect
			}

			byte[] buff = new byte[dataSize];
			in.readFully(buff, 0, dataSize);
			data = (new String(buff)).split("\0");

			if(!this.isOperationOk(data[0])){
				System.out.println("Got incorrect operation from client");

				return false;
			}

			switch(reqType){
				case TaskProtocol.REQ_ADDTASK:
					System.out.println("Got req addtask");
					break;
				case TaskProtocol.REQ_GETTASKLIST:
					System.out.println("Got req get tasklist");
					break;
				default:
					System.out.println("Got unknown req");
					return false;
			}

			// Request was successfully handled
			this.sendOK(out);
			return true;
			
		}catch(NullPointerException e){
			System.out.println("Connection closed");
		}

		return false;
	}

	// Returns whether operation given in string is known to server
	private boolean isOperationOk(String operation){
		for(String op : this.operations){
			if( op.equals(operation) ){
				return true;
			}
		}

		return false;
	}

	private void sendOK(DataOutputStream out){
		try{
		out.writeInt(TaskProtocol.RES_OK);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void sendERR(DataOutputStream out){
		try{
		out.writeInt(TaskProtocol.RES_ERR);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}

