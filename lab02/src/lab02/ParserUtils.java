package lab02;

import java.util.ArrayList;

class ParserUtils
{
	// Used to find person in ArrayList of people by its number
	// Returns Person object if found, null otherwise
	public static Person getPersonByNumber(int pn, ArrayList<Person> ppl)
	{
		for(Person p : ppl)
		{
			if(p.number == pn)
				return p;	
		}
		return null;
	}
}
