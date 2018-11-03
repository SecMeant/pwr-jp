package lab03;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Database{
	public ArrayList<Pacient> pacients = new ArrayList<>();
	public ArrayList<Doctor> doctors = new ArrayList<>();
	public ArrayList<Room> rooms = new ArrayList<>();

	public void loadPacients(String filepath) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(filepath)).useDelimiter("\n");
		Integer line = Integer.valueOf(1); // For debug

		while(sc.hasNext()){
			try{
				String[] pacientFields = sc.next().split(";");
				Pacient pacient = new Pacient(pacientFields[0],pacientFields[1],Integer.valueOf(pacientFields[2]));
				pacients.add(pacient);
			}catch(IndexOutOfBoundsException e){
				System.err.println("Parsing error occured when parsing Pacients from file on line " + line);
			}
			line++;
		}
	}
}

