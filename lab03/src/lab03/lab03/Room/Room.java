package lab03;

import java.util.ArrayList;

class Room
{
	public static final int meetingCountPerRoom = 10;

	// Room number
	Integer number;

	Event[] duties = new Event[meetingCountPerRoom];
	Event[] visits = new Event[meetingCountPerRoom];

	Room(Integer roomNumber)
	{
		this.number = roomNumber;
	}

	public boolean addVisit(Event visit)
	{	
		System.out.println("Adding visit " + visit.toString());
		// TODO catch boundary exception
		if(this.visits[visit.timeOffset] != null)
			return false;

		// No doctor here
		// TODO UNCOMMENT
		//if(this.duties[visit.timeOffset] == null)
		//	return false;
		
		this.visits[visit.timeOffset] = visit;
		return true;
	}

	public boolean addDuty(Event duty)
	{
		// Some doctor have already this duty?
		if(this.duties[duty.timeOffset] != null)
			return false;

		this.duties[duty.timeOffset] = duty;
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

