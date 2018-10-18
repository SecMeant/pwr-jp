package lab02;

import java.util.ArrayList;
import java.lang.Integer;
import java.lang.StringBuilder;

class Person
{
	public ArrayList<Person> friends;
	public int number;

	Person(int number, ArrayList<Person> friends)
	{
		this.number = number;
		this.friends = friends;
	}

	Person(int number)
	{
		this.number = number;
		this.friends = new ArrayList<Person>();
	}

	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		ret.append(Integer.toString(this.number));
		ret.append(": {");
		for( Person f : this.friends)
		{
			ret.append(Integer.toString(f.number));
			ret.append(",");
		}
		ret.append("}");
		return ret.toString();
	}
}
