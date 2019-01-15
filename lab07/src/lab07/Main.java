package lab07;

import java.rmi.NotBoundException;

public class Main{

	public static void main(final String ... args)
	{
		Central central = new Central();
		Terminal terminal = new Terminal();
		Monitor monitor = new Monitor();
		TicketHandler ticketHandler = new TicketHandler("other");

		try{
			terminal.getTicket("other");
			terminal.getTicket("priority");
			ticketHandler.getTicket("other");
			terminal.getTicket("different");
			terminal.getTicket("i dont even know what im doing");
		}catch(Exception e){
			System.err.println("Error occured when tried to call Central remote interface.");
		}
	}
}
