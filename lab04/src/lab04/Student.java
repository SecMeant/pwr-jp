package lab04;

import java.util.Vector;

public class Student
{
	static final String[] MARKS_NAMES = new String[] {"Programming languages","Calculus","Digital signal processing"};
	
	public String firstName;
	public String surname;
	private String pesel;
	final String[] marks = new String[Student.MARKS_NAMES.length];
	final WeekAttendence[] attendence;
	
	{
		this.attendence = new WeekAttendence[Utils.ATTENDENCE_WEEK_COUNT];
		for(int i=0; i<this.attendence.length; i++)
		{
			this.attendence[i] = new WeekAttendence();
		}
	}
	
	Student(String firstName, String surname, String pesel)
	{
		this.firstName = firstName;
		this.surname = surname;
		this.pesel = "0";
		this.setPesel(pesel);
	}
	
	public WeekAttendence getWeekAttendence(int weekNumber)
	{
		return this.attendence[weekNumber];
	}
	
	public void addAttendence(int week, int day, int hour)
	{
		this.attendence[week-1].attendence.add(new DayAttendence(day, hour));
	}
	
	String getPesel()
	{
		return this.pesel;
	}
	
	void setPesel(String pesel)
	{
		if(Utils.isNumeric(pesel))
			this.pesel = pesel;
	}
	
	void setPesel(int pesel)
	{
		this.pesel = String.valueOf(pesel);
	}
	
	Vector<String> getDataAsVector()
	{
		Vector<String> ret = new Vector<String>();
		ret.add(this.firstName);
		ret.add(this.surname);
		ret.add(this.pesel);
		for(String mark : this.marks)
			ret.add(mark);
		
		return ret;
	}
}
