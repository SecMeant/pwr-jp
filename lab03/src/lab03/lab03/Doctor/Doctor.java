package lab03;

class Doctor{
	private String firstName;
	private String lastName;
	private String speciality; // TODO enumtype ?

	Doctor(String firstName, String lastName, String speciality){
		this.firstName = firstName;
		this.lastName = lastName;
		this.speciality = speciality;
	}

	public String getFirstName(){return this.firstName;}
	public String getLastName(){return this.lastName;}
	public String getSpeciality(){return this.speciality;}

	public boolean setFristName(String firstName){
		if(firstName == null)
			return false;

		this.firstName = firstName;
		return true;
	}

	public boolean setLastName(String lastName){
		if(lastName == null)
			return false;

		this.lastName = lastName;
		return true;
	}

	public boolean setSpeciality(String speciality){
		if(speciality == null)
			return false;

		this.speciality = speciality;
		return true;
	}

	public String toString(){
		return this.firstName + " " + this.lastName + " " + this.speciality;
	}

}

