package lab02;

import java.util.Comparator;

public class PersonIDComparator implements Comparator<Person>
{
	@Override
	public int compare(Person p1, Person p2)
	{
		return p1.number - p2.number;
	}
}
