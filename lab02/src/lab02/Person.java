package lab02;

import java.util.ArrayList;
import java.lang.Integer;
import java.lang.StringBuffer;

public class Person
{
	public ArrayList<Person> friends;
	public int number;

	Person(int nm, ArrayList<Person> fs)
	{
		number = nm;
		friends = fs;
	}

	@Override
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
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
