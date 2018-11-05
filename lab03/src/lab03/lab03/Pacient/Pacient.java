package lab03;

import java.util.ArrayList;

class Pacient
{
	private String firstName;
	private String lastName;
	private Integer pesel;
	public ArrayList<Event> visits = new ArrayList<>();

	Pacient(String firstName, String lastName, Integer pesel)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.pesel = pesel;
	}

	public String getFirstName(){return this.firstName;}
	public String getLastName(){return this.lastName;}
	public Integer getPesel(){return this.pesel;}

	public boolean setFristName(String firstName)
	{
		if(firstName == null)
			return false;

		this.firstName = firstName;
		return true;
	}

	public boolean setLastName(String lastName)
	{
		if(lastName == null)
			return false;

		this.lastName = lastName;
		return true;
	}

	public boolean setPesel(Integer pesel)
	{
		if(pesel < 100000000 || pesel > 999999999)
		{
			return false;
		}

		this.pesel = pesel;
		return true;
	}

	public void addVisit(Event visit) throws Exception
	{
		this.visits.add(visit);
	}

	public String toString()
	{
		String ret = this.firstName + " " + this.lastName + " " + this.pesel + " ";
		for(Event visit : this.visits)
			ret += visit.toString() + " ";
		return ret;
	}
}
