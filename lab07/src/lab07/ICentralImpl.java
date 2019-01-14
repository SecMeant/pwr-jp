package lab07;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ICentralImpl extends UnicastRemoteObject implements ICentral {

	private Central parent;
	int ticketNumber;

	public ICentralImpl(Central parent) throws RemoteException{
		this.parent = parent;
		this.ticketNumber = 0;
	}

	public boolean register(IMonitor m) throws RemoteException{
		System.out.println("Registering new Monitor " + m.toString());
		return true;
	}

	public Ticket getTicket() throws RemoteException{
		Ticket t = new Ticket();
		t.category = "other";
		t.number = this.ticketNumber;
		t.status = 'i';

		this.ticketNumber++;
		return t;
	}
}
