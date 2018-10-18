package lab02;

import java.util.ArrayList;

class SolvePair
{
	Table table;
	ArrayList<Person> people;

	SolvePair(ArrayList<Person> people, Table table)
	{
		this.table = table;
		this.people = people;
	}

	@Override
	public String toString()
	{
		return "{" + this.table + ": " + this.people + "}";
	}
}
