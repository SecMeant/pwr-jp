package lab06;

import java.io.IOException;

class Main{
	
	public static void main(String[] args)throws IOException, InterruptedException{
		TaskServer server = new TaskServer();
		
		server.start();
		TaskClient client = new TaskClient();

		server.join();
	}
}
