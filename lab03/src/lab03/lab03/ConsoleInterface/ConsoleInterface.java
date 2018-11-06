package lab03;

import java.util.Scanner;
import java.util.ArrayList;

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
	}

	public boolean handleUserInput()
	{
		System.out.print("> ");
		String[] args = scanner.next().trim().split(" ");
		
		if(args[0].equals("quit") || args[0].equals("q"))
		{
			return false;
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
			if(args.length != 3)
			{
				System.out.println("Error! Expected 2 arguments. Firstname and lastname.");
				return true;
			}

			Doctor doc = db.getDoctorByName(args[1],args[2]);
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
		else
		{
			this.printMenu();
		}

		return true;
	}

	public void run()
	{
		while(this.handleUserInput()) {}
	}

}
