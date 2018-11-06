package lab03;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.Exception;

class Database
{
	public ArrayList<Pacient> pacients = new ArrayList<>();
	public ArrayList<Doctor> doctors = new ArrayList<>();
	public ArrayList<Room> rooms = new ArrayList<>();

	private Integer currentID = Integer.valueOf(0);

	public void open(String pacientFilePath, String doctorsFilePath)
	throws FileNotFoundException
	{
		this.loadPacients(pacientFilePath);
		this.loadDoctors(doctorsFilePath);
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
		System.out.println(Integer.valueOf(sc.next().trim()));

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

	private void updateRoomVisits()
	{
		this.rooms = new ArrayList<>();
		
		this.doctors.forEach(doc->{
			doc.duties.forEach(duty->{
				Room r = this.getRoomByNumber(duty.roomNumber);
				if(r != null)
					r.addDuty(duty);
				else
					System.err.println("Just tried to add duty to room that doesnt exists");
			});
		});

		this.pacients.forEach(p->{
			p.visits.forEach(v->{
				Room r = this.getRoomByNumber(v.roomNumber);
				if(r != null)
					r.addVisit(v);
				else
					System.err.println("Just tried to add visit to room that doesnt exists");
			});
		});
	}

	public void addPacient
	(String firstName, String lastName, Integer pesel)
	throws Exception
	{
		for(Pacient p : this.pacients)
			if(p.getPesel() == pesel)
				throw new Exception("Another pacient with this pesel already exists");

		this.pacients.add(new Pacient(firstName, lastName, pesel));
	}

	public void addDoctor
	(String firstName, String lastName, String speciality)
	{	
		this.doctors.add(new Doctor(this.currentID, firstName, lastName, speciality));
		this.currentID++;
	}

	public void removePacient
	(Integer pesel)
	{
		for(Pacient p : this.pacients)
		{
			if(p.getPesel().intValue() == pesel.intValue())
			{
				this.removePacient(p); // This might be slow, maybe i should delete by index
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
				for(Event pacientVisit : pacient.visits)
				{
					if(pacientVisit == r.visits[i])
						r.visits[i] = null;
				}
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
				for(Event doctorsDuty : doctor.duties)
				{
					if(doctorsDuty == r.duties[i])
					{
						// If doctor being dropped has duty, drop it
						r.duties[i] = null;

						// If anyone has visit at this time, drop it also
						r.visits[i] = null;
					}
				}
			}
		}

		this.doctors.remove(doctor);
	}

	public Room getRoomByNumber(Integer number)
	{
		for(Room r : this.rooms)
			if(r.number == number)
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

		System.out.println(ret.size());
		return ret;
	}
}

