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

	public boolean signDoctor(Doctor doctor, int timeOffset)
	{
		if(this.duties[timeOffset] != null)
			return false;

		this.duties[timeOffset] = doctor;
		return true;
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

