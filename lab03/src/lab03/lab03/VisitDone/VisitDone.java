package lab03;

class VisitDone
{
	public Integer roomNumber;
	public Integer timeOffset;
	public Integer pacientID;
	public String notes;

	VisitDone(Integer roomNumber, Integer timeOffset, Integer pacientID, String notes)
	{
		this.roomNumber = roomNumber;
		this.timeOffset = timeOffset;
		this.pacientID = pacientID;
		this.notes = notes;
	}

	public String dump()
	{
		return this.roomNumber + ";" + this.timeOffset + ";" + this.pacientID + 
		       ";" + this.notes + "\n";
	}
}
