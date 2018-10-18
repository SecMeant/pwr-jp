package lab02;

import java.util.ArrayList;

class Solver
{
	static ArrayList<SolvePair> solve
	(ArrayList<Person> ppl, ArrayList<Table> tables)
	{
		// TODO solving the problem
		// Bellow is hard coded bad result in terms of logic
		// but good result in terms of return type of result
		// which is array of SolvePairs
		ArrayList<SolvePair> result = new ArrayList<>();

		result.add(new SolvePair(ppl, tables.get(0)));

		for(int i = 1; i < tables.size(); i++)
		{
			result.add(new SolvePair(new ArrayList<Person>(), tables.get(i)));
		}
		
		return result;
	}
}
