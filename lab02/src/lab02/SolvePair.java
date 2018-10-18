package lab02;

import java.util.ArrayList;

class SolvePair
{
	Table table;
	ArrayList<Person> people;

	SolvePair(ArrayList<Person> ppl, Table tab)
	{
		this.table = tab;
		this.people = ppl;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append(this.table.toString());
		sb.append(": ");
		sb.append(this.people.toString());
		sb.append("}");
		
		return sb.toString();
	}
}
