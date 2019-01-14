package lab07;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class Terminal{
	Terminal(){
		System.out.println("Creating new Terminal");	
	}

	public void getTicket(String category)
	throws NotBoundException, MalformedURLException, RemoteException {
		ICentral iface = (ICentral) Naming.lookup(Central.INTERFACE_URL);
		Ticket ticket = iface.getTicket(category);
		
		System.out.println("Got ticket: " + ticket.toString());

	}

}



