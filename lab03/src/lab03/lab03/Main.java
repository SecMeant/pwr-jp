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

		Pacient p = db.getPacientByPesel(605037909);
		if(p != null)
			db.removePacient(p);

		Doctor d = db.getDoctorById(1);
		if(d != null)
			db.removeDoctor(d);

		for(Room r : db.rooms)
			System.out.println(r);

		for(Pacient p2 : db.pacients)
			System.out.println(p2);

		for(Doctor d2 : db.doctors)
			System.out.println(d2);
	}

	public static void main(String[] args)
	throws FileNotFoundException, Exception
	{
		test();
	}
}
