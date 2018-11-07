package lab03;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

class Main
{
	private final static String pacientsPath = 
		Paths.get(System.getProperty("user.dir"), "data", "pacients.txt").toString();

	private final static String doctorsPath = 
		Paths.get(System.getProperty("user.dir"), "data", "doctors.txt").toString();

	private final static String visitsPath = 
		Paths.get(System.getProperty("user.dir"), "data", "visitsDone.txt").toString();

	private static void test()
	throws FileNotFoundException, Exception
	{
		Database db = new Database();

		db.open(pacientsPath, doctorsPath, visitsPath);

		ConsoleInterface ci = new ConsoleInterface(db);
		ci.run();
	}

	public static void main(String[] args)
	throws FileNotFoundException, Exception
	{
		test();
	}
}
