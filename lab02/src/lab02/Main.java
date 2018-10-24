package lab02;

import java.util.ArrayList;

public class Main
{
	private static void usage(String[] args)
	{
		System.out.println
			("Usage: java main <people_file_path> <tables_file_path>\n");
	}
	
	private static void printSolution(Solution s, ArrayList<Person> people)
	{
		for(int i=0; i<people.size(); i++)
		{
			System.out.print("{");
			System.out.print(people.get(i).number);
			System.out.print(":");
			System.out.print(s.seatInfo.get(i));
			System.out.print("}");
		}
		System.out.print("\nScore: ");
		System.out.print(s.points);
	}

	public static void main(String[] args)
	throws Exception
	{
		if(args.length != 2)
		{
			usage(args);
			return;
		}

		ParsingResult pr = Parser.parse(args[0],args[1]);
		Solver solver = new Solver();
		solver.setup(pr.people, pr.tables, Solver::evaluatorMostHappy);
		Solution s = solver.solve();
		printSolution(s, pr.people);
	}
}
