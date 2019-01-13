package lab07;

import java.io.Serializable;

public class Ticket implements Serializable{
	public String category;
	public int number;
	// 'i' - issued
	// 'c' - called
	// 's' - served
	public char status;
}
