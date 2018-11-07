package lab03;

import java.util.ArrayList;

class Room
{
	public static final int meetingCountPerRoom = 10;

	// Room number
	private Integer number;

	public Doctor[] duties = new Doctor[meetingCountPerRoom];
	public Pacient[] visits = new Pacient[meetingCountPerRoom];

	Room(Integer roomNumber)
	{
		this.number = roomNumber;
	}

	public Integer getNumber()
	{return this.number;}

	public void signPacient(Pacient pacient, int timeOffset)
	throws Exception
	{	
		if(this.visits[timeOffset] != null)
			throw new Exception("Given visit is already taken by someone else");

		if(this.duties[timeOffset] == null)
			throw new Exception("No doctor is in this room at that moment");

		this.visits[timeOffset] = pacient;
	}

	public void unsignPacient(Pacient pacient, int timeOffset)
	throws Exception
	{	
		if(this.visits[timeOffset] != pacient)
			throw new Exception("Given visit is taken by someone else");

		this.visits[timeOffset] = null;
	}

	public void signDoctor(Doctor doctor, int timeOffset)
	throws Exception
	{
		if(this.duties[timeOffset] != null)
			throw new Exception("Another doctor has already taken that room and hour");

		this.duties[timeOffset] = doctor;
	}

	public void unsignDoctor(Doctor doctor, int timeOffset)
	throws Exception
	{
		if(this.duties[timeOffset] != doctor)
			throw new Exception("Another doctor has taken that room and hour");

		this.duties[timeOffset] = null;
	}

	public String toString()
	{
		String ret = "Room number: " + this.number + "\n";
		
		ret += "\tDuties: \n\t\t";
		for(int i=0; i < this.meetingCountPerRoom; i++)
			ret += this.duties[i] + " | ";

		ret += "\n\tVisits: \n\t\t";
		for(int i=0; i < this.meetingCountPerRoom; i++)
			ret += this.visits[i] + " | ";

		return ret;
	}
}

