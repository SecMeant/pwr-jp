package lab07;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import java.util.concurrent.TimeUnit;

public class TicketHandler{
	private ICentral centralInterface;

	public static void main(final String... args){
		TicketHandler self = new TicketHandler();
	
		self.handleTickets();
	}

	public TicketHandler(){
		try{
			this.loadCentralInterface();
			System.out.println("Ticket handler connected to central.");
		}catch(Exception e){
			System.err.println("Ticket handler failed to connect to central.");
		}
	}

	private void handleTickets(){
		for(;;){
			try{
				Ticket t = this.getTicket("other");
				TimeUnit.SECONDS.sleep(3); // hard work
				this.centralInterface.reportTicketHandled(t);
			}catch(Exception e){
				System.err.println(e.getMessage());
			}
		}
	}

	private void loadCentralInterface()
	throws NotBoundException, MalformedURLException, RemoteException {
		this.centralInterface = (ICentral) Naming.lookup(Central.INTERFACE_URL);
	}

	public Ticket getTicket(String category) throws RemoteException, InterruptedException{
		Ticket ticket = this.centralInterface.waitForTicket(category);

		System.out.println("Got ticket: " + ticket.toString());
		return ticket;
	}
}
