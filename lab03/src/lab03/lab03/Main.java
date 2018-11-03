package lab03;

import java.io.FileNotFoundException;

class Main{
	public static void main(String[] args) throws FileNotFoundException{
		System.out.println("Hello world");

		Database db = new Database();

		db.loadPacients("/home/holz/etc/github/pwr-jp/lab03/src/lab03/data/pacients.txt");

		for(Pacient p : db.pacients)
			System.out.println(p);
	}
}
