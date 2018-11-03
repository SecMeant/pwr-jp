package lab03;

import java.io.FileNotFoundException;

class Main{
	private final static String pacientsPath = 
		"/home/holz/etc/github/pwr-jp/lab03/src/lab03/data/pacients.txt";

	private final static String doctorsPath = 
		"/home/holz/etc/github/pwr-jp/lab03/src/lab03/data/doctors.txt";

	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("Hello world");

		Database db = new Database();

		db.loadPacients(pacientsPath);
		db.loadDoctors(doctorsPath);

		for(Pacient p : db.pacients)
			System.out.println(p);

		for(Doctor d : db.doctors)
			System.out.println(d);
	}
}
