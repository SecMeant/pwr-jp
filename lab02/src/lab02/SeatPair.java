package lab02;

class SeatPair
{
	public Integer personID;
	public Integer tableID;
	
	SeatPair(Integer personID, Integer tableID)
	{
		this.personID = personID;
		this.tableID = tableID;
	}
	
	public SeatPair copy()
	{
		SeatPair ret = new SeatPair(this.personID, this.tableID);
		return ret;
	}
}