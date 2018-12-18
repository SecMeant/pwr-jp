package lab06;

import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.EOFException;

import java.net.UnknownHostException;
import java.lang.IllegalArgumentException;

import javax.swing.*;

class TaskClient{
	private Socket connection;
	private TaskClientInterface iface;

	private DataOutputStream sockOut;
	private DataInputStream sockIn;

	public static void main(String[] args)throws IOException{
		TaskClient client = new TaskClient();
	}

	TaskClient() throws IOException{
		this.connection = new Socket();

		this.iface = new TaskClientInterface(this);
		this.iface.getWindow().addFormSubmitListener(new ServerFormListener(this));
		this.iface.getWindow().addAddTaskListener(new AddTaskListener(this));
		this.iface.getWindow().addGetTaskListListener(new GetTaskListListener(this));
		this.iface.getWindow().addSolveTaskListener(new SolveTaskListener(this));

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
				this.parent.signalError("Connect to server first.");
			}
			
			this.parent.addTaskToServer(args[0], args[1]);
		}
	}

	private class GetTaskListListener implements FormSubmitListener{
		private TaskClient parent;

		public GetTaskListListener(TaskClient parent){
			this.parent = parent;
		}

		@Override
		public void callback(String[] args) throws IOException{
			if(!this.parent.isConnected()){
				this.parent.signalError("Error! Connect to server first.");
				return;
			}

			this.parent.iface.clearTaskList();
			this.parent.handleGetTaskListRequest();
		}
	}

	private class SolveTaskListener implements FormSubmitListener{
		private TaskClient parent;

		public SolveTaskListener(TaskClient parent){
			this.parent = parent;
		}

		@Override
		public void callback(String[] args) throws IOException{
			Task task = new Task(args[0], args[1], args[2]);

			if(!this.parent.solveTask(task))
				return;

			if(this.parent.sendSolve(task)){
				this.parent.signalSuccess("Successfully sent solved task.");
				return;
			}

			this.parent.signalError("Error occurred when tries to send results to server.");
		}
	}

	private boolean isConnected(){
		
		return this.connection.isConnected() && !this.connection.isClosed();
	}

	private void connect(String host, String port) throws IOException{
		System.out.println("Connecting . . .");

		try{
			this.connection = new Socket(host, Integer.parseInt(port));
			this.sockOut = new 
				DataOutputStream(this.connection.getOutputStream());
			this.sockIn = new
				DataInputStream(this.connection.getInputStream());
			this.iface.setStateConnected(true);
		}catch(NumberFormatException e){
			this.signalError("Error! Port must be a number");
			System.err.println(e.getMessage());
		}catch(UnknownHostException e){
			this.signalError("Error! Server host unknown");
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

	private void disconnect(){
		System.out.println("Disconnecting");
		
		try{
			this.connection.shutdownInput();
			this.connection.shutdownOutput();
			this.connection.close();
			this.iface.setStateConnected(false);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private boolean addTaskToServer(String op, String args){
		if(!this.isConnected()){
			this.signalError("Error! Connect to server first.");
			return false;
		}

		if(!this.checkTask(op,args)){
			this.signalError("Error! Task data incorrect!");
			return false;
		}

		try{
			TaskProtocol.sendAddTaskRequest(this.sockOut, op, args);
		}catch(java.net.SocketException e){
			this.signalError("Server unexpectedly closed connection.");
			this.disconnect();
			return false;
		}catch(EOFException e){
			this.signalError("Server unexpectedly closed connection.");
			this.disconnect();
			return false;
		}catch(IOException e){
			e.printStackTrace();
		}

		if( !this.getServerResponse() ){
			this.signalError("Sending task to server failed.");
			return false;
		}

		this.signalSuccess("Task set successfully.");
		return true;
	}

	private boolean checkTask(String op, String args){
		return true;
	}

	private boolean getServerResponse(){
		try{
			if(this.sockIn.readInt() == TaskProtocol.RES_OK)
				return true;
		}catch(IOException e){
			this.disconnect();
			e.printStackTrace();
		}

		return false;
	}

	private void handleGetTaskListRequest() throws IOException{
		// Send request
		this.sockOut.writeInt(TaskProtocol.REQ_GETTASKLIST);

		// Datasize for this request should be 0
		this.sockOut.writeInt(0);

		// Fetch request type
		int anstype = this.sockIn.readInt();

		// Check answer for this packet
		// If ans type is not of expected type destroy current session
		// something went out of sync
		if(anstype != TaskProtocol.ANS_TASKLIST){
			System.out.println("Got unexpected request from server. Client disconnecting!");
			System.out.println(anstype);
			this.signalError("Got unexpected request from server. Disconnecting!");
			this.disconnect();
			return;
		}

		// Get datasize
		int dataSize = this.sockIn.readInt();

		// Read data
		byte[] buff = new byte[dataSize];
		this.sockIn.readFully(buff, 0, buff.length);

		if(this.sockIn.readInt() != TaskProtocol.RES_OK){
			System.out.println("Got some data from server but it returned NON RES_OK afterward. Perhaps stolen other packet?");
		}
		
		String data = new String(buff);

		if(!data.contains("\0\0"))
			return;

		String[] tasks = data.split("\0\0");

		for(String task_ds : tasks){
			String[] tmp = task_ds.split("\0");

			String op = tmp[0];
			String args = tmp[1];
			String result = tmp[2];

			this.iface.addTaskToList(op, args, result);
		}
	}

	private boolean solveTask(Task task){
		String[] args = Task.unpackArgs(task);

		if(args.length == 0){
			return false;
		}

		int result = Integer.parseInt(args[0]);

		try{
		switch(task.operation){
			case "add":
				for(int i = 1; i < args.length; ++i){
					try{
						result += Integer.parseInt(args[i]);
					}catch(NumberFormatException e){/*ignore*/}
				}
				break;
			case "sub":
				for(int i = 1; i < args.length; ++i){
					try{
						result -= Integer.parseInt(args[i]);
					}catch(NumberFormatException e){/*ignore*/}
				}
				break;
			case "mod":
				for(int i = 1; i < args.length; ++i){
					try{
						result %= Integer.parseInt(args[i]);
					}catch(NumberFormatException e){/*ignore*/}
				}
				break;
			case "mul":
				for(int i = 1; i < args.length; ++i){
					try{
						result *= Integer.parseInt(args[i]);
					}catch(NumberFormatException e){/*ignore*/}
				}
				break;
			case "div":
				for(int i = 1; i < args.length; ++i){
					try{
						result /= Integer.parseInt(args[i]);
					}catch(NumberFormatException e){/*ignore*/}
				}
				break;
			default:
				throw new Error("Unknown operation");
		}
		}catch(ArithmeticException e){
			this.signalError("Arithmetic exception! Task is unsolvable.");
			throw e;
		}

		task.result = String.valueOf(result);
		return true;
	}

	// Returns whether server got task successfully
	private boolean sendSolve(Task task)throws IOException{

		byte[] data = task.serialize();

		// Send header
		// send request type
		this.sockOut.writeInt(TaskProtocol.REQ_RESULT);

		// send length of data
		this.sockOut.writeInt(data.length);

		// send data
		this.sockOut.write(data, 0, data.length);

		// Wait for response from server
		if(this.sockIn.readInt() == TaskProtocol.RES_OK){
			return true;
		}

		return false;
	}
}
