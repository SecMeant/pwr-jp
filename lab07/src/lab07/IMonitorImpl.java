package lab07;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class IMonitorImpl extends UnicastRemoteObject implements IMonitor{

	Monitor parent;

	public IMonitorImpl(Monitor parent) throws RemoteException{
		this.parent = parent;
	}

	public void update(Info[] infos) throws RemoteException{
		System.out.println("Update monitor called");
		for(Info i : infos){
			this.parent.updateCategoryTickets(i.categoryName, i.queue);		
			System.out.println(i);
		}


	}
}
