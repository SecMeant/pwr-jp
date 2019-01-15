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
	private MonitorGui gui = new MonitorGui(this);

	public static void main(final String... args){
		Monitor self = new Monitor();
	}

	public Monitor(){
		try{
			this.registerRMIInterface();
			this.loadCentralInterface();
			this.registerToCentral();
		}catch(Exception e){
			System.err.println("Failed to create monitor.");
			System.err.println(e.getMessage());
		}
	}

	private void registerRMIInterface() throws RemoteException{
			this.selfIFace = new IMonitorImpl(this);

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

	public void updateCategoryTickets(String category, int[] tickets){
		this.gui.updateCategoryTickets(category, tickets);
	}
}
