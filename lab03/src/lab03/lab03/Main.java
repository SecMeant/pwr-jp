package lab03;

import java.io.FileNotFoundException;

class Main
{
	private final static String pacientsPath = 
		System.getProperty("user.dir") + "\\data\\pacients.txt";

	private final static String doctorsPath = 
		System.getProperty("user.dir") + "\\data\\doctors.txt";

	private static void test()
	throws FileNotFoundException, Exception
	{
		Database db = new Database();

		db.loadPacients(pacientsPath);
		db.loadDoctors(doctorsPath);

		db.addDoctor("Patryk","Wlazlyn","Programista");
		db.addPacient("Adam","Waleczny", 997998991);

		for(Pacient p : db.pacients)
			System.out.println(p);

		for(Doctor d : db.doctors)
			System.out.println(d);
	}

	public static void main(String[] args)
	throws FileNotFoundException, Exception
	{
		System.out.println(pacientsPath);
		test();
	}
}
