package lab07;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICentral extends Remote{
	public boolean register(IMonitor m) throws RemoteException;
	public Ticket getTicket(String category) throws RemoteException;
	public Ticket waitForTicket(String category) throws RemoteException;
	public void reportTicketHandled(Ticket t) throws RemoteException;
}
