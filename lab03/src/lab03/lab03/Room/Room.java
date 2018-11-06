package lab03;

import java.util.ArrayList;

class Room
{
	// Room number
	Integer number;

	ArrayList<Event> duties = new ArrayList<>();
	ArrayList<Event> visits = new ArrayList<>();

	Room(Integer roomNumber)
	{
		this.number = roomNumber;
	}

	public boolean addVisit(Event visit)
	{
		// Check if visit doesnt intersects with
		// other visit at the time
		for(Event e : this.visits)
		{
			if(e.intersects(visit))
				return false;
		}
	
		// Check if in requested visits time any
		// doctor is on duty in this room
		for(Event e : this.duties)
		{
			if(!visit.within(e))
				return false;
		}

		this.visits.add(visit);

		return true;
	}

	public boolean addDuty(Event duty)
	{
		// Check given duty intersects with already
		// declated duty
		for(Event e : this.duties)
		{
			if(duty.intersects(e))
				return false;
		}

		this.duties.add(duty);

		return true;
	}

	public String toString()
	{
		String ret = "{Room number: " + this.number + ";" + "Duties: " + 
		             this.duties.toString() + " Visits: " + this.visits.toString() + "}";

		return ret;
	}
}

