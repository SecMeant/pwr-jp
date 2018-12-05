package lab03;

class Event
{
	Integer roomNumber;
	Integer timeOffset;

	Event()
	{}

	Event(Integer roomNumber, Integer timeOffset)
	{
		this.roomNumber = roomNumber;
		this.timeOffset = timeOffset;
	}

	boolean compare(Event e)
	{
		return (e.roomNumber == this.roomNumber && e.timeOffset == this.timeOffset);
	}

	public String toString()
	{
		return "Room: " + this.roomNumber + ", " + (this.timeOffset+7) + ":00";
	}
}
