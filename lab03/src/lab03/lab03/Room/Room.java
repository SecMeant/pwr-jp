package lab03;

import java.util.ArrayList;

class Room
{
	public static final int meetingCountPerRoom = 10;

	// Room number
	Integer number;

	Doctor[] duties = new Doctor[meetingCountPerRoom];
	Pacient[] visits = new Pacient[meetingCountPerRoom];

	Room(Integer roomNumber)
	{
		this.number = roomNumber;
	}

	public boolean signPacient(Pacient pacient, int timeOffset)
	{	
		if(this.visits[timeOffset] != null)
			return false;

		// TODO uncomment
		//if(this.duties[timeOffset] == null)
		//	return false;

		this.visits[timeOffset] = pacient;
		return true;
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

