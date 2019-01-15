package lab07;

import java.rmi.NotBoundException;

public class Main{

	public static void main(final String ... args)
	{
		Central central = new Central();
		Terminal terminal = new Terminal();
		Monitor monitor = new Monitor();
	}
}
