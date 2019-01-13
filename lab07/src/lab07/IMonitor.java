package lab07;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMonitor extends Remote{
	public void update(Info[] i) throws RemoteException;
}
