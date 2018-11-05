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
					visit.timeRange =
						new TimeRange(new Time(Integer.valueOf(visitInfo[1]),
						                       Integer.valueOf(visitInfo[2])),
					                new Time(Integer.valueOf(visitInfo[3]),
													         Integer.valueOf(visitInfo[4])));
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
					duty.timeRange =
						new TimeRange(new Time(Integer.valueOf(dutyInfo[1]),
						                       Integer.valueOf(dutyInfo[2])),
					                new Time(Integer.valueOf(dutyInfo[3]),
													         Integer.valueOf(dutyInfo[4])));
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
		this.pacients.forEach(p->{
			p.visits.forEach(v->{
				
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
			if(p.getPesel() == pesel)
				this.pacients.remove(p); // This might be slow, maybe i should delete by index

		// If not in db just ignore
	}

	public void removePacient
	(Pacient pacient)
	{
		// TODO remove from visits

		this.pacients.remove(pacient); // This might be slow, maybe i should delete by index

		// If not in db just ignore
	}

	public void removeDoctor
	(Integer id)
	{
		for(Doctor d : this.doctors)
			if(d.getId() == id)
				this.doctors.remove(d); // This might be slow, maybe i should delete by index

		// If not in db just ignore
	}

	public void removeDoctor
	(Doctor doctor)
	{
		// TODO Remove from visits

		this.doctors.remove(doctor); // This might be slow, maybe i should delete by index

		// If not in db just ignore
	}
}

