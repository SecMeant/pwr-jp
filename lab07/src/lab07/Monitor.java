package lab07;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;

public class Monitor{
	public static int REGISTRY_PORT = 5061;
	
	private ICentral centralIFace;
	private IMonitor selfIFace; // Rmi interface to this monitor

	public Monitor(){
		try{
			this.registerRMIInterface();
			this.loadCentralInterface();
			this.registerToCentral();
		}catch(Exception e){
			System.err.println("Failed to create monitor.");
		}
	}

	private void registerRMIInterface() throws RemoteException{
		this.selfIFace = new IMonitorImpl();

		Registry registry = LocateRegistry.createRegistry(Monitor.REGISTRY_PORT);
		registry.rebind("IMonitor", this.selfIFace); 

		System.out.println("Successfully registered monitor interface.");
	}

	private void loadCentralInterface()
	throws RemoteException, NotBoundException, MalformedURLException{
		this.centralIFace = (ICentral) Naming.lookup(Central.INTERFACE_URL);
	}

	private void registerToCentral() throws RemoteException{
		this.centralIFace.register(this.selfIFace);
	}
}
