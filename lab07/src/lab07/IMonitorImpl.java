package lab07;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IMonitorImpl extends UnicastRemoteObject implements IMonitor{

	public IMonitorImpl() throws RemoteException{

	}

	public void update(Info[] i) throws RemoteException{
		System.out.println("Update monitor called");
	}
}
