package lab02;

import java.util.ArrayList;

public class Person {
	public ArrayList<Person> friends;
	public String name;
	public int number;

	Osoba(String nm, ArrayList<Osoba> fs){
		name = nm;
		friends = fs;
	}
}
