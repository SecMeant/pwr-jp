package lab07;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.ArrayList;
import java.util.Arrays;

public class Central{
	public static final ArrayList<String> ticketCategories = 
		new ArrayList<>(Arrays.asList("other","high priority"));
	public static int REGISTRY_PORT = 5099;
	public static String INTERFACE_URL = "rmi://localhost:5099/ICentral";

	ArrayList<IMonitor> monitors = new ArrayList<IMonitor>();

	public static void main(final String... args){
		Central self = new Central();
	}

	public Central(){
		this.registerInterface();
	}

	private void registerInterface(){
		try{
			Registry registry = LocateRegistry.createRegistry(Central.REGISTRY_PORT);
			registry.rebind("ICentral", new ICentralImpl(this));
			System.out.println("Successfully registered central interface.");
		}catch(RemoteException e){
			System.err.println("Interface registering failed!");
		}
	}
}
