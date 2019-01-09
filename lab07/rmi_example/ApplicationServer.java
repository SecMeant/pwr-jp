package rmi_example;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.ArrayList;

public class ApplicationServer {
	
	ArrayList<Integer> data = new ArrayList<Integer>();

	public static void main(final String... args) throws RemoteException{
		ApplicationServer server = new ApplicationServer();

		server.callback();
		server.callback();
		server.callback();

		Registry registry = LocateRegistry.createRegistry(5099);
		registry.rebind("secretMethod", new HelloServant(server));

	}

	public void callback(){
		System.out.println("Someone called remote method.");
		this.data.add(this.data.size());
	}

}
