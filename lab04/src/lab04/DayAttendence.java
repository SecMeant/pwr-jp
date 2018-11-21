package lab04;

class DayAttendence
{
	int day;
	int hour;
	
	DayAttendence(int day, int hour)
	{
		this.day = day;
		this.hour = hour;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof DayAttendence))return false;
	    DayAttendence otherCasted = (DayAttendence)other;
		return (this.day == otherCasted.day && this.hour == otherCasted.hour);
	}
}