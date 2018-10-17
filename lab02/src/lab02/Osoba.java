package lab02;

import java.util.Vector;

public class Osoba {
	public Vector<Osoba> przyjaciele;
	public String imie;
	public int numer;

	Osoba(String im, ){
		imie = im;
		przyjaciele = new Vector<Osoba>();
	}
}
