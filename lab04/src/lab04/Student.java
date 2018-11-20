package lab04;

public class Student
{
	static final String[] MARKS_NAMES = new String[] {"Programming languages","Calculus","Digital signal processing"};
	
	public String firstName;
	public String surname;
	private String pesel;
	final String[] marks = new String[Student.MARKS_NAMES.length];
	final WeekAttendence[] attendence = new WeekAttendence[Utils.ATTENDENCE_WEEK_COUNT];
	
	Student(String firstName, String surname, String pesel)
	{
		this.firstName = firstName;
		this.surname = surname;
		this.pesel = "0";
		this.setPesel(pesel);
	}
	
	public WeekAttendence getWeekAttendence(int weekNumber)
	{
		return this.attendence[weekNumber-1];
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
}
