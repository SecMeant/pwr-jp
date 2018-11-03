package lab03;

class Pacient
{
	private String firstName;
	private String lastName;
	private Integer pesel;

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

	public String toString()
	{
		return this.firstName + " " + this.lastName + " " + this.pesel;
	}
}
