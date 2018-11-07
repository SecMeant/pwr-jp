package lab03;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.IndexOutOfBoundsException;

class ConsoleInterface
{
	Scanner scanner = new Scanner(System.in).useDelimiter("\n");
	Database db;

	ConsoleInterface(Database db)
	{this.db = db;}
	
	public void printMenu()
	{
		System.out.println("=== Menu ===");
		System.out.println("\t1. Get doctor list by speciality");
		System.out.println("\t2. Get available visits by given doctor");
		System.out.println("\t3. Get pacients list");
		System.out.println("\t4. Get doctors list");
		System.out.println("\t5. Remove pacient by pesel");
		System.out.println("\t6. Remove doctor by id");
		System.out.println("\t7. Get visits by pesel");
		System.out.println("\t8. Add new pacient");
		System.out.println("\t9. Add new doctor");
		System.out.println("\t10. Sign pacient");
		System.out.println("\t11. Unsign pacient");
		System.out.println("\t12. Sign doctor");
		System.out.println("\t13. Unsign doctor");
		System.out.println("\t14. Finish visit");
		System.out.println("\t15. Save");
	}

	public boolean handleUserInput()
	{
		System.out.print("> ");
		String[] args = scanner.next().trim().split(" ");
		
		if(args[0].equals("quit") || args[0].equals("q"))
		{
			return false;
		}
		else if(args[0].equals("0"))
		{
			db.rooms.forEach(System.out::println);
		}
		else if(args[0].equals("1"))
		{
			if(args.length != 2)
			{
				System.out.println("Error! Expected 1 argument.");
				return true;
			}

			db.getDoctorsBySpeciality(args[1]).forEach(doc->{
				System.out.println(doc);
			});
		}
		else if(args[0].equals("2"))
		{
			if(args.length != 1)
			{
				System.out.println("Error! Unexpected arguments.");
				return true;
			}
			System.out.print("First name: ");
			String firstname = this.scanner.next().trim();
			System.out.print("Last name: ");
			String lastname = this.scanner.next().trim();

			Doctor doc = db.getDoctorByName(firstname, lastname);
			if(doc == null)
			{
				System.out.println("That doctor doesnt exists.");
				return true;
			}

			ArrayList<Event> evs = db.getAvailableVisitsByDoctor(doc);
			if(evs.size() == 0)
				System.out.println("No free visits available for this doctor");
			else
			{
				evs.forEach(ev->{
					System.out.println(ev);
				});
			}
		}
		else if(args[0].equals("3"))
		{
			db.pacients.forEach(System.out::println);
		}
		else if(args[0].equals("4"))
		{
			db.doctors.forEach(System.out::println);
		}
		else if(args[0].equals("5"))
		{
			if(args.length != 2)
			{
				System.out.println("Error! Expected 1 argument.");
				return true;
			}

			db.removePacient(Integer.valueOf(args[1]));
		}
		else if(args[0].equals("6"))
		{
			if(args.length != 2)
			{
				System.out.println("Error! Expected 1 argument.");
				return true;
			}

			db.removeDoctor(Integer.valueOf(args[1]));
		}
		else if(args[0].equals("7"))
		{
			if(args.length != 2)
			{
				System.out.println("Error! Expected 1 argument.");
				return true;
			}

			Pacient pacient = db.getPacientByPesel(Integer.valueOf(args[1]));
			if(pacient == null)
			{
				System.out.println("Couldnt find pacient with given pesel");
				return true;
			}

			if(pacient.visits.size() == 0)
			{
				System.out.println("No visits");
				return true;
			}

			pacient.visits.forEach(visit->{
				System.out.println(visit);
			});
		}
		else if(args[0].equals("8"))
		{
			if(args.length != 1)
			{
				System.out.println("Error! Unexpected arguments.");
				return true;
			}
			System.out.print("First name: ");
			String firstName = this.scanner.next().trim();

			System.out.print("Second name: ");
			String lastName = this.scanner.next().trim();

			System.out.print("pesel: ");
			int pesel = Integer.valueOf(this.scanner.next().trim());
			
			// TODO make better exception
			try
			{
				db.addPacient(firstName, lastName, pesel);
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				return true;
			}

			System.out.println("Successfully added new pacient");
		}
		else if(args[0].equals("9"))
	{
			if(args.length != 1)
			{
				System.out.println("Error! Unexpected arguments.");
				return true;
			}
			System.out.print("First name: ");
			String firstName = this.scanner.next().trim();

			System.out.print("Second name: ");
			String lastName = this.scanner.next().trim();

			System.out.print("speciality: ");
			String speciality = this.scanner.next().trim();
			
			db.addDoctor(firstName, lastName, speciality);

			System.out.println("Successfully added new doctor");
		}
		else if(args[0].equals("10"))
		{
			System.out.print("Pesel: ");
			Integer pesel = Integer.valueOf(this.scanner.next().trim());

			System.out.print("Room number: ");
			Integer roomNumber  = Integer.valueOf(this.scanner.next().trim());

			System.out.print("Time offset: ");
			Integer timeOffset = Integer.valueOf(this.scanner.next().trim());
			
			try { db.signPacient(pesel, roomNumber, timeOffset); }
			catch(Exception e){System.out.println(e.getMessage());return true;}
		}
		else if(args[0].equals("11"))
		{
			System.out.print("Pesel: ");
			Integer pesel = Integer.valueOf(this.scanner.next().trim());

			System.out.print("Room number: ");
			Integer roomNumber  = Integer.valueOf(this.scanner.next().trim());

			System.out.print("Time offset: ");
			Integer timeOffset = Integer.valueOf(this.scanner.next().trim());
			
			try { db.unsignPacient(pesel, roomNumber, timeOffset); }
			catch(Exception e){System.out.println(e.getMessage());return true;}
		}
		else if(args[0].equals("12"))
		{
			System.out.print("ID: ");
			Integer id = Integer.valueOf(this.scanner.next().trim());

			System.out.print("Room number: ");
			Integer roomNumber  = Integer.valueOf(this.scanner.next().trim());

			System.out.print("Time offset: ");
			Integer timeOffset = Integer.valueOf(this.scanner.next().trim());
			
			try { db.signDoctor(db.getDoctorById(id), roomNumber, timeOffset); }
			catch(Exception e){System.out.println(e.getMessage());return true;}
		}
		else if(args[0].equals("13"))
		{
			System.out.print("ID: ");
			Integer id = Integer.valueOf(this.scanner.next().trim());

			System.out.print("Room number: ");
			Integer roomNumber  = Integer.valueOf(this.scanner.next().trim());

			System.out.print("Time offset: ");
			Integer timeOffset = Integer.valueOf(this.scanner.next().trim());
			
			try { db.unsignDoctor(db.getDoctorById(id), roomNumber, timeOffset); }
			catch(Exception e){System.out.println(e.getMessage());return true;}
		}
		else if(args[0].equals("14"))
		{
			System.out.print("Room number: ");
			Integer roomNumber  = Integer.valueOf(this.scanner.next().trim());

			System.out.print("Time offset: ");
			Integer timeOffset = Integer.valueOf(this.scanner.next().trim());

			Room r = db.getRoomByNumber(roomNumber);
			int pacientID;
			
			try
			{
				if(r.visits[timeOffset] == null)
				{
					System.out.println("There is no visit at that time");
					return true;
				}
				pacientID = r.visits[timeOffset].getPesel();
				r.visits[timeOffset] = null;

				System.out.print("Notes: ");
				String notes = this.scanner.next().trim();

				db.visitsDone.add(new VisitDone(roomNumber, timeOffset, pacientID, notes));

				db.visitsDone.forEach(vd->{System.out.println(vd.dump());});
			}
			catch(IndexOutOfBoundsException e)
			{
				System.out.println("Bad time offset");
				return true;
			}

			return true;
		}
		else if(args[0].equals("15"))
		{
			try{db.save();}
			catch(IOException e)
			{
				System.out.println("Error occured when writing to file");
				System.out.println(e.getMessage());
			}
			return true;
		}
		else
		{
			this.printMenu();
		}

		return true;
	}

	public void run()
	{
		this.printMenu();
		while(this.handleUserInput()) {}
	}

}
