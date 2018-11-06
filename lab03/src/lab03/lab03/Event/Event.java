package lab03;

class Event
{
	Integer roomNumber;
	TimeRange timeRange;

	public boolean intersects(Event e)
	{
		// TODO make checking
		return false;
	}

	public boolean within(Event e)
	{
		// TODO make checking
		return true;
	}

	public String toString()
	{
		return "Room: " + this.roomNumber + ", " + this.timeRange.from.hour + ":" +
		       this.timeRange.from.minute + " - " + this.timeRange.to.hour + ":" + 
					 this.timeRange.to.minute;
	}
}
