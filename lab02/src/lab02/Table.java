package lab02;

class Table
{
	public int number;
	public int size;
	
	public Table(int number, int size)
	{
		this.number = number;
		this.size= size;
	}

	@Override
	public String toString()
	{
		return "{Number: " + this.number + ", size: " + this.size;
	}
}
