package lab02;

public class Main
{
	private static void usage(String[] args)
	{
		System.out.println
			("Usage: java main <people_file_path> <tables_file_path>\n");
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
		solver.setup(pr.people, pr.tables);
		Solution s = solver.solve();
		System.out.println(s.seatInfo);
		System.out.println(s.points);
	}
}
