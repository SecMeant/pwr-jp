package lab02;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileNotFoundException;

public class Main
{
	private static void usage(String[] args)
	{
		System.out.println
			("Usage: java main <people_file_path> <tables_file_path>\n");
	}	

	public static void main(String[] args)
	throws FileNotFoundException
	{
		if(args.length != 2)
		{
			usage(args);
			return;
		}

		ParsingResult pr = Parser.parse(args[0],args[1]);

		Person p2 = ParserUtils.getPersonByNumber(3, pr.people);

		if(p2 != null)
		{
			p2.friends.add(new Person(13, new ArrayList<Person>()));
		}

		System.out.println(pr.people);
		System.out.println(pr.tables);

		System.out.println(Solver.solve(pr.people, pr.tables));
		System.out.print("\n\n\n");
		
		SequenceGenerator s = new SequenceGenerator();
		int[] tab = {1,4,7,5,11};
		ArrayList<int[]> ans = s.getCombinations(tab, 10);

		for (int[] a : ans)
		{
			System.out.print(Arrays.toString(a));
			int[] tmp = Utils.getArraysComplement(tab, a);
			System.out.println(Arrays.toString(tmp));
		}
			
		System.out.print("\n");

	}
}
