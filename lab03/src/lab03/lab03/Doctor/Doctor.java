package lab03;

import java.util.ArrayList;

class Doctor
{
	private Integer id;
	private String firstName;
	private String lastName;
	private String speciality; // TODO enumtype ?
	public ArrayList<Event> duties = new ArrayList<>();

	Doctor(Integer id, String firstName, String lastName, String speciality)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.speciality = speciality;
	}

	public Integer getId(){return this.id;}
	public String getFirstName(){return this.firstName;}
	public String getLastName(){return this.lastName;}
	public String getSpeciality(){return this.speciality;}

	public boolean setId(Integer id)
	{
		this.id = id;
		return true;
	}

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

	public boolean setSpeciality(String speciality)
	{
		if(speciality == null)
			return false;

		this.speciality = speciality;
		return true;
	}

	public void addDuty(Event duty) throws Exception
	{
		this.duties.add(duty);
	}

	public String toString()
	{
		String ret = this.id + " " + this.firstName + " " + this.lastName + 
		             " " + this.speciality + " ";
		for(Event duty : this.duties)
			ret += duty.toString() + " ";
		return ret;
	}

}

