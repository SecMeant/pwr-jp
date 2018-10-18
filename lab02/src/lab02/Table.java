package lab02;

import java.lang.StringBuilder;

class Table
{
	public int number;
	public int size;
	
	public Table(int n, int s)
	{
		this.number = n;
		this.size= s;
	}

	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder();
		ret.append("{Number: ");
		ret.append(Integer.toString(this.number));
		ret.append(", size: ");
		ret.append(Integer.toString(this.size));
		ret.append("}");
		
		return ret.toString();
	}
}
