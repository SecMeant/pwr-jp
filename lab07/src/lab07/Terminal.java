package lab07;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class Terminal{
	ICentral centralInterface;

	public static void main(final String... args){
		Terminal self = new Terminal();

		try{
			self.getTicket("other");
			self.getTicket("priority");
			self.getTicket("different");
			self.getTicket("i dont even know what im doing");
		}catch(Exception e){
			System.err.println("Error occured when tried to call Central remote interface.");
		}
	}

	Terminal(){
		try{
			this.loadCentralInterface();
			System.out.println("Creating new Terminal");	
		}catch(Exception e){
			System.err.println("Terminal failed to connect to central.");
		}
	}

	private void loadCentralInterface()
	throws NotBoundException, MalformedURLException, RemoteException {
		this.centralInterface = (ICentral) Naming.lookup(Central.INTERFACE_URL);
	}

	public void getTicket(String category)
	throws NotBoundException, MalformedURLException, RemoteException {
		Ticket ticket = this.centralInterface.getTicket(category);
		
		System.out.println("Got ticket: " + ticket.toString());

	}

}



