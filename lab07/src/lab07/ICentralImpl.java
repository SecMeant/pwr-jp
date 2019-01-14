package lab07;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ICentralImpl extends UnicastRemoteObject implements ICentral {

	private Central parent;
	private Map<String, BlockingQueue<Ticket>> tickets = new HashMap<String, BlockingQueue<Ticket>>();
	private int ticketNumber;

	public ICentralImpl(Central parent) throws RemoteException{
		this.parent = parent;
		this.ticketNumber = 1;

		this.tickets.put("other", new LinkedBlockingQueue<Ticket>());
		this.tickets.put("high priority", new LinkedBlockingQueue<Ticket>());
		this.tickets.put("different", new LinkedBlockingQueue<Ticket>());
	}

	public boolean register(IMonitor m) throws RemoteException{
		System.out.println("Registering new Monitor " + m.toString());
		this.parent.monitors.add(m);
		return true;
	}

	public Ticket getTicket(String category) throws RemoteException{
		Ticket t = new Ticket();
		t.category = category;
		t.number = this.ticketNumber;
		t.status = 'i';

		this.ticketNumber++;

		BlockingQueue<Ticket> ticketQ = this.tickets.get(category);

		try{
			ticketQ.put(t);
		}catch(Exception e){
			System.err.println("Putting ticket into queue failed!");
		}

		this.updateMonitors();

		return t;
	}

	public Ticket waitForTicket(String category) throws RemoteException{
		Ticket t;

		BlockingQueue<Ticket> ticketQ = this.tickets.get(category);

		// Try to take until success
		for(;;){
			try{
				t = ticketQ.take();
				break;
			}catch(Exception e){
				System.err.println("Getting ticket from q failed.");
			}
		}

		return t;
	}

	public void reportTicketHandled(Ticket t) throws RemoteException{
		this.updateMonitors();
	}

	public void updateMonitors(){
		Queue<Ticket> ticketQ = this.tickets.get("other");
		Object[] ticketArray = ticketQ.toArray();

		int[] tickets = new int[ticketArray.length];
		
		for(int i=0; i < ticketArray.length; i++){
			tickets[i] = ((Ticket) ticketArray[i]).number;
		}

		Info info = new Info();
		info.categoryName = "other";
		info.queue = tickets;

		Info[] infos = new Info[1];
		infos[0] = info;

		for(IMonitor monitor : this.parent.monitors){
			try{
					monitor.update(infos);
			}catch(Exception e){
				System.err.println("Some monitor update failed.");
				System.err.println(e.getMessage());
			}
		}
	}
}