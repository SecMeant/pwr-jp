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

	private List<Task> tasks = new ArrayList<Task>();

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
		DataOutputStream sockOut = new DataOutputStream(sock.getOutputStream());
		DataInputStream sockIn = new DataInputStream(sock.getInputStream());

		while(true){
			if(!this.handleRequest(sockIn, sockOut)){
				this.sendERR(sockOut);
				this.terminateConnection(sock);
				break;
			}
			this.sendOK(sockOut);
		}
	}

	private static boolean checkHeader(int reqType, int dataSize){
		if(dataSize > 512){
			return false;
		}

		switch(reqType){
			case TaskProtocol.REQ_ADDTASK:
				return true;

			case TaskProtocol.REQ_GETTASKLIST:
				if(dataSize != 0)
					return false;
				return true;

			default:
				return false;
		}
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
	private boolean handleRequest(DataInputStream in, DataOutputStream out) throws IOException{
		int reqType = 0;
		int dataSize = 0;
		String[] data;
		try{
			reqType = in.readInt();
			dataSize = in.readInt();

			if(!checkHeader(reqType, dataSize)){
				return false;
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
					if(data.length != 2)
						return false;
					
					String op = data[0];
					String args = data[1];

					this.addTask(op, args);
					break;

				case TaskProtocol.REQ_GETTASKLIST:
					break;

				default:
					System.out.println("Got unknown req");
					return false;
			}

			// Request was successfully handled
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

	private void addTask(String op, String args){
		Task task = new Task(op, args);
	
		if(this.tasks.contains(task))
			return;

		this.tasks.add(task);
	}
}

