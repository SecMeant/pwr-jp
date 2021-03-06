package lab03;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.Exception;
import java.io.FileWriter;
import java.io.IOException;

class Database
{
	public ArrayList<Pacient> pacients = new ArrayList<>();
	public ArrayList<Doctor> doctors = new ArrayList<>();
	public ArrayList<Room> rooms = new ArrayList<>();
	public ArrayList<VisitDone> visitsDone = new ArrayList<>();

	private Integer currentID = Integer.valueOf(0);

	// Files which were used to load doctors and pacients
	private String doctorsSourceFilePath;
	private String pacientsSourceFilePath;
	private String doneVisitsSourceFilePath;

	public void open(String pacientFilePath, String doctorsFilePath,
	                 String doneVisitsFilePath)
	throws FileNotFoundException
	{	
		// Save file names
		this.doctorsSourceFilePath = doctorsFilePath;
		this.pacientsSourceFilePath = pacientFilePath;
		this.doneVisitsSourceFilePath = doneVisitsFilePath;

		this.loadPacients(pacientFilePath);
		this.loadDoctors(doctorsFilePath);
		this.loadDoneVisits(doneVisitsFilePath);
		this.updateRoomVisits();
	}

	private void loadPacients(String filepath) 
	throws FileNotFoundException, NumberFormatException
	{
		Scanner sc = new Scanner(new File(filepath)).useDelimiter("\n");
		Integer line = Integer.valueOf(1); // For debug

		while(sc.hasNext())
		{
			try
			{
				String[] pacientFields = sc.next().trim().split(";");
				Pacient pacient = new Pacient(pacientFields[0],pacientFields[1],
				                              Integer.valueOf(pacientFields[2]));

				for(int i = 3; i < pacientFields.length; i++)
				{
					String[] visitInfo = pacientFields[i].split(",");
					Event visit = new Event();
					visit.roomNumber = Integer.valueOf(visitInfo[0]);
					visit.timeOffset = Integer.valueOf(visitInfo[1]);

					try
					{
						pacient.addVisit(visit);
					}
					catch(Exception e)
					{
						System.err.println("Visits intersects!");
					}
				}

				pacients.add(pacient);
			}
			catch(IndexOutOfBoundsException e)
			{
				System.err.println("Parsing error occured when parsing Pacients from file on line " + line);
			}

			catch(NumberFormatException e)
			{
				System.err.println("Parsing error occured when parsing Pacients from file on line " + line);
			}

			line++;
		}
	}

	private void loadDoctors
	(String filepath)
	throws FileNotFoundException
	{
		Scanner sc = new Scanner(new File(filepath)).useDelimiter("\n");
		Integer line = Integer.valueOf(1); // for debug

		// First value in file is currentID, restore it
		this.currentID = Integer.valueOf(sc.next().trim());

		while(sc.hasNext())
		{
			try
			{
				String[] doctorFields = sc.next().trim().split(";");
				Doctor doctor = new Doctor(Integer.valueOf(doctorFields[0]),doctorFields[1],
				                           doctorFields[2], doctorFields[3]);

				for(int i = 4; i < doctorFields.length; i++)
				{
					String[] dutyInfo = doctorFields[i].split(",");
					Event duty = new Event();
					duty.roomNumber = Integer.valueOf(dutyInfo[0]);
					duty.timeOffset = Integer.valueOf(dutyInfo[1]);

					try
					{
						doctor.addDuty(duty);
					}
					catch(Exception e)
					{
						System.err.println("Duties intersects!");
					}
				}

				this.doctors.add(doctor);
			}

			catch(IndexOutOfBoundsException e)
			{
				System.err.println("Parsing error occured when parsing Doctors from file on line " + line);
			}
		}
	}

	private void loadDoneVisits
	(String filepath)
	throws FileNotFoundException
	{
		Scanner sc = new Scanner(new File(filepath)).useDelimiter("\n");
		Integer line = Integer.valueOf(1); // for debug

		while(sc.hasNext())
		{
			String[] visitFields = sc.next().trim().split(";");
			try
			{
				VisitDone vd = new VisitDone(Integer.valueOf(visitFields[0]),
				                             Integer.valueOf(visitFields[1]),
				                             Integer.valueOf(visitFields[2]),
				                             visitFields[3]);
				this.visitsDone.add(vd);
			}
			catch(IndexOutOfBoundsException e)
			{
				System.err.println("Parsing error occured when parsing visits done from file on line " + line);
			}
		}
	}

	private void updateRoomVisits()
	{
		this.rooms = new ArrayList<>();
		
		this.doctors.forEach(doc->
		{
			doc.duties.forEach(duty->
			{
				Room r = this.getRoomByNumber(duty.roomNumber);
				if(r != null)
				{
					try{r.signDoctor(doc, duty.timeOffset.intValue());}
					catch(Exception e){System.out.println(e.getMessage());}
				}
				else
				{
					System.err.println("Just tried to add duty to room that doesnt exists");
				}
			});
		});
		
		this.pacients.forEach(pacient->
		{
			pacient.visits.forEach(visit->
			{
				Room r = this.getRoomByNumber(visit.roomNumber);
				if(r != null)
				{
					try{r.signPacient(pacient, visit.timeOffset);}
					catch(Exception e){System.err.println(e.toString());} // just log and go forward
				}
				else
				{
					System.err.println("Just tried to add visit to room that doesnt exists");
				}
			});
		});
	}

	public void addPacient
	(String firstName, String lastName, Integer pesel)
	throws Exception
	{
		for(Pacient p : this.pacients)
			if(p.getPesel().intValue() == pesel.intValue())
				throw new Exception("Another pacient with this pesel already exists " + pesel);

		this.pacients.add(new Pacient(firstName, lastName, pesel));
	}

	public void addDoctor
	(String firstName, String lastName, String speciality)
	{	
		this.doctors.add(new Doctor(this.currentID, firstName, lastName, speciality));
		this.currentID++;
	}

	public void signDoctor(Doctor doctor, int roomNumber, int timeOffset)
	{
		Room room = this.getRoomByNumber(roomNumber);
		
		try{room.signDoctor(doctor, timeOffset);}
		catch(Exception e){System.out.println(e.getMessage());}
	}

	public void signPacient(Integer pesel, int roomNumber, int timeOffset)
	throws Exception
	{
		this.signPacient(this.getPacientByPesel(pesel), roomNumber, timeOffset);
	}

	public void signPacient(Pacient pacient, int roomNumber, int timeOffset)
	throws Exception
	{
		Room r = getRoomByNumber(roomNumber);
		
		try
		{
			r.signPacient(pacient,timeOffset);
		}
		catch(Exception e)
		{
			throw e;
		}

		pacient.visits.add(new Event(roomNumber, timeOffset));
	}

	public void unsignPacient(Integer pesel, int roomNumber, int timeOffset)
	throws Exception
	{
		this.unsignPacient(this.getPacientByPesel(pesel), roomNumber, timeOffset);
	}

	public void unsignPacient(Pacient pacient, int roomNumber, int timeOffset)
	throws Exception
	{
		Room r = getRoomByNumber(roomNumber);

		try{r.unsignPacient(pacient, timeOffset);}
		catch(Exception e){System.out.println(e.getMessage());return;}
	
		// Find visit in visits list and drop it	
		for(int i=0; i<pacient.visits.size(); i++)
		{
			if(pacient.visits.get(i).roomNumber == roomNumber && 
			   pacient.visits.get(i).timeOffset == timeOffset)
			{
				pacient.visits.remove(i);
			}
		}
	}

	public void unsignDoctor(Integer id, int roomNumber, int timeOffset)
	throws Exception
	{
		this.unsignDoctor(this.getDoctorById(id), roomNumber, timeOffset);
	}

	public void unsignDoctor(Doctor doctor, int roomNumber, int timeOffset)
	throws Exception
	{
		Room r = getRoomByNumber(roomNumber);

		try{r.unsignDoctor(doctor, timeOffset);}
		catch(Exception e){System.out.println(e.getMessage());return;}
	
		// Find duty in futies list and drop it	
		for(int i=0; i<doctor.duties.size(); i++)
		{
			if(doctor.duties.get(i).roomNumber == roomNumber && 
			   doctor.duties.get(i).timeOffset == timeOffset)
			{
				doctor.duties.remove(i);
			}
		}
	}

	public void removePacient
	(Integer pesel)
	{
		for(Pacient p : this.pacients)
		{
			if(p.getPesel().intValue() == pesel.intValue())
			{
				// This might be slow, maybe i should delete by index
				this.removePacient(p); 
				break;
			}
		}
		// If not in db just ignore
	}

	public void removePacient
	(Pacient pacient)
	{
		// Remove all visits from rooms for given pacient
		for(Room r : this.rooms)
		{
			for(int i=0; i < r.visits.length ; i++)
			{
				if(pacient == r.visits[i])
					r.visits[i] = null;
			}
		}

		this.pacients.remove(pacient);
	}

	public void removeDoctor
	(Integer id)
	{
		for(Doctor d : this.doctors)
			if(d.getId().intValue() == id.intValue())
			{
				this.removeDoctor(d); // This might be slow, maybe i should delete by index
				break;
			}

		// If not in db just ignore
	}

	public void removeDoctor
	(Doctor doctor)
	{
		// Remove all visits from rooms for given pacient
		for(Room r : this.rooms)
		{
			for(int i=0; i < r.duties.length ; i++)
			{
				if(doctor == r.duties[i])
				{
					// If doctor being dropped has duty, drop it
					r.duties[i] = null;

					// If anyone has visit at this time, drop it also
					r.visits[i] = null;
				}
			}
		}

		this.doctors.remove(doctor);
	}

	public Room getRoomByNumber(Integer number)
	{
		for(Room r : this.rooms)
			if(r.getNumber().intValue() == number.intValue())
				return r;

		Room room = new Room(number);
		this.rooms.add(room);
		return room;
	}

	public Pacient getPacientByPesel(Integer pesel)
	{
		for(Pacient p : this.pacients)
		{
			if(p.getPesel().intValue() == pesel.intValue())
				return p;
		}

		return null;
	}

	public Doctor getDoctorById(Integer id)
	{
		for(Doctor d : this.doctors)
		{
			if(d.getId().intValue() == id.intValue())
				return d;
		}

		return null;
	}

	public ArrayList<Doctor> getDoctorsBySpeciality(String speciality)
	{
		ArrayList<Doctor> ret = new ArrayList<>();

		this.doctors.forEach(d->{
			if(d.getSpeciality().equals(speciality))
				ret.add(d);
		});

		return ret;
	}

	public Doctor getDoctorByName(String firstName, String lastName)
	{
		for(Doctor doc : this.doctors)
			if(doc.getFirstName().equals(firstName) && doc.getLastName().equals(lastName))
				return doc;
		return null;
	}

	public ArrayList<Event> getAvailableVisitsByDoctor(Doctor doctor)
	{
		ArrayList<Event> visits = new ArrayList<>();
		
		for(Room r : this.rooms)
		{
			for(int i=0; i < r.duties.length; i++)
			{
				if(r.duties[i] == doctor && r.visits[i] == null)
					visits.add(new Event(r.getNumber(), i));
			}
		}

		return visits;
	}

	private void flushDoctorsToFile()
	throws IOException
	{
		FileWriter writer = new FileWriter(this.doctorsSourceFilePath);
		writer.write(String.valueOf(this.currentID) + "\n");

		for(Doctor doc : this.doctors)
		{
			writer.write(doc.getId() + ";" + doc.getFirstName() + ";" + doc.getLastName() +
			             ";" + doc.getSpeciality());

			// Write all duties
			for(Event duty : doc.duties)
			{
				writer.write(";" + duty.roomNumber + "," + duty.timeOffset);
			}
			writer.write("\n");
		}

		writer.close();
	}

	private void flushPacientToFile()
	throws IOException
	{
		FileWriter writer = new FileWriter(this.pacientsSourceFilePath);

		for(Pacient pac : this.pacients)
		{
			writer.write(pac.getFirstName() + ";" + pac.getLastName() +
			             ";" + pac.getPesel());

			// Write all visits
			for(Event visit : pac.visits)
			{
				writer.write(";" + visit.roomNumber + "," + visit.timeOffset);
			}
			writer.write("\n");
		}

		writer.close();
	}

	private void flushVisitsDoneToFile()
	throws IOException
	{
		FileWriter writer = new FileWriter(this.doneVisitsSourceFilePath);

		for(VisitDone vd : this.visitsDone)
		{
			writer.write(vd.dump());
		}

		writer.close();
	}

	public ArrayList<VisitDone> getVisitsByPacientId(Integer id)
	{
		ArrayList<VisitDone> vdarr = new ArrayList<>();

		this.visitsDone.forEach(vd->{
			if(vd.pacientID.intValue() == id.intValue())
				vdarr.add(vd);
		});

		return vdarr;
	}

	public void save()
	throws IOException
	{
		this.flushDoctorsToFile();
		this.flushPacientToFile();
		this.flushVisitsDoneToFile();
	}
}

