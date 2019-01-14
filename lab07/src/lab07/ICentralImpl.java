package lab07;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ICentralImpl extends UnicastRemoteObject implements ICentral {

	private Central parent;
	int ticketNumber;

	public ICentralImpl(Central parent) throws RemoteException{
		this.parent = parent;
		this.ticketNumber = 1;
	}

	public boolean register(IMonitor m) throws RemoteException{
		System.out.println("Registering new Monitor " + m.toString());
		this.parent.monitors.add(m);
		return true;
	}

	public Ticket getTicket(String category) throws RemoteException{
		Ticket t = new Ticket();
		t.category = category;
		t.number = this.ticketNumber;
		t.status = 'i';

		this.ticketNumber++;

		this.parent.updateMonitors();

		return t;
	}

	private void createAndShowGui(){

	}
}
