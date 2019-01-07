package rmi_example;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client{
	
	public static void main(final String... args)
	throws NotBoundException, MalformedURLException, RemoteException
	{
		HelloService service = (HelloService) Naming.lookup("rmi://localhost:5099/secretMethod");
		System.out.println(service.echo("asdf") + " " + service.getClass());
	}
}

