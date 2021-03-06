package lab07;

import java.net.MalformedURLException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public class TicketHandler{
	private ICentral centralInterface;
	private String ticketCategory; // ticket category to handle

	public static void main(String[] args){
		TicketHandler self;

		if(args.length == 0){
			self = new TicketHandler(Central.ticketCategories.get(0));
		}else{
			self = new TicketHandler(args[0]);
		}

		if(!Central.ticketCategories.contains(self.ticketCategory)){
			System.err.println("Warrning! Ticket handler initialized with category not known to Central.");
		}

		self.handleTickets();
	}

	public TicketHandler(String category){
		this.ticketCategory = category;

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
				Ticket t = this.getTicket(ticketCategory);
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
