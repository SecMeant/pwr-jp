package lab03;

import java.io.FileNotFoundException;
import java.nio.file.Paths;

class Main
{
	private final static String pacientsPath = 
		Paths.get(System.getProperty("user.dir"), "data", "pacients.txt").toString();

	private final static String doctorsPath = 
		Paths.get(System.getProperty("user.dir"), "data", "doctors.txt").toString();

	private static void test()
	throws FileNotFoundException, Exception
	{
		Database db = new Database();

		db.open(pacientsPath, doctorsPath);

		db.addDoctor("Patryk","Wlazlyn","Programista");
		db.addPacient("Adam","Waleczny", 997998991);

		for(Pacient p : db.pacients)
			System.out.println(p);

		for(Doctor d : db.doctors)
			System.out.println(d);

		System.out.println(db.rooms.size());
		for(Room r : db.rooms)
			System.out.println(r);
	}

	public static void main(String[] args)
	throws FileNotFoundException, Exception
	{
		test();
	}
}
