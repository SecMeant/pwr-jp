package lab02;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Integer;

class Parser
{
	public static ParsingResult parse
	(String peopleFilePath, String tablesFilePath)
	throws FileNotFoundException
	{
		ParsingResult pr = new ParsingResult();

		pr.people = loadPeople(peopleFilePath);
		pr.tables = loadTables(tablesFilePath);

		return pr;
	}

	// Parses file with tables, returns table of tables
	private static ArrayList<Table> loadTables
	(String filePath) throws FileNotFoundException
	{
		Scanner sc = new Scanner(new File(filePath));

		ArrayList<Table> ar = new ArrayList<Table>();

		while(sc.hasNextInt())
		{
			int f = sc.nextInt();
			int s = sc.nextInt();
			ar.add(new Table(f,s));
		}

		sc.close();
		return ar;
	}

	// Parses file with people, returns table of people
	private static ArrayList<Person> loadPeople
	(String filePath) throws FileNotFoundException
	{
		Scanner sc = new Scanner(new File(filePath));

		ArrayList<Person> ar = new ArrayList<>();

		while(sc.hasNextLine())
		{
			int nm = sc.nextInt();
			String friends = sc.next();

			// Check if new person is already present in array
			// If not create new person, otherwise make changes on existing
			Person newp = ParserUtils.getPersonByNumber(nm, ar);
			if(newp == null)
			{
				newp = new Person(nm, new ArrayList<Person>());
				// Adding new person to array, will modify it later
				ar.add(newp);
			}

			ArrayList<Person> friendsArray = newp.friends;

			for(String s : friends.split(","))
			{
				int personNumber = Integer.parseInt(s);
				Person p = ParserUtils.getPersonByNumber(personNumber, ar);
				
				if(p == null)
				{
					p = new Person(personNumber, new ArrayList<Person>());
					ar.add(p);
				}
				friendsArray.add(p);
			}
		}

		sc.close();
		return ar;
	}
}
