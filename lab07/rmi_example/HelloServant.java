package rmi_example;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;

public class HelloServant extends UnicastRemoteObject implements HelloService {
	ApplicationServer parent;

	public HelloServant(ApplicationServer parent) throws RemoteException{
		super();
		this.parent = parent;
	}

	public String echo(String input) throws RemoteException{
		String ret =  "Super secret " + input + "\n";

		for(Integer number : this.parent.data){
			ret += Integer.toString(number);
			ret += ", ";
		}

		this.parent.callback();
		return ret;
	}

}
