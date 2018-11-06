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

	public String toString()
	{
		return "Room: " + this.roomNumber + ", " + this.timeOffset;
	}
}
