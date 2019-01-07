package rmi_example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {
	
	public static void main(final String... args) throws RemoteException{
		Registry registry = LocateRegistry.createRegistry(5099);
		registry.rebind("secretMethod", new HelloServant());
	}

}
