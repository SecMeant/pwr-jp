package lab02;

import java.io.FileNotFoundException;

public class Main
{

	static Parser parser = new Parser();

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

		ParsingResult pr = parser.parse(args[0],args[1]);

		System.out.println(pr.people);
		System.out.println(pr.tables);
	}
}
