package lab02;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {

	private static String fakeArgs = "1 2 3\nKoniec\n";
	
	private void rozwiazanie() {
		List<Osoba> osoby = new ArrayList<Osoba>();
		
		Osoba jan = new Osoba("Jan");
		osoby.add(jan);
		
		Osoba ewa = new Osoba("Ewa");
		jan.przyjaciele.add(ewa);
		
		Osoba adam = new Osoba("Adam");
		jan.przyjaciele.add(adam);
		
	}
	
	public static void main(String[] args) {
		
	}
}
