package lab04;

import java.util.Vector;

public class DataBase
{
	private Vector<Student> students = new Vector<>();
	
	DataBase(String studentsFilePath, String attendenceFilePath)
	{
		// TODO read from files
		
		try 
		{
			this.addStudent(new Student("Patryk","Wlazlyn","997998999"));
			this.addStudent(new Student("John","Carmack","997998991"));
			
			this.students.get(0).attendence[2].attendence.add(new DayAttendence(2,3));			
		} catch (DataBaseInsertException e) {
			e.printStackTrace();
		}
	}
	 
	WeekAttendence getWeekAttendenceByPesel(String pesel, int weekNumber)
	{
		Student s = this.getStudentByPesel(pesel);
		
		if(s == null)
			return new WeekAttendence();
		
		return s.getWeekAttendence(weekNumber);
	}
	
	Student getStudentByPesel(String pesel)
	{
		for(Student s : this.students)
		{	
			if(s.getPesel().equals(pesel))
				return s;
		}
		
		return null;
	}
	
	public void addStudent(Student s) throws DataBaseInsertException
	{
		if(this.getStudentByPesel(s.getPesel()) != null)
			throw new DataBaseInsertException("User with given pesel already exists in database");
		
		this.students.add(s);
	}
	
	Vector<Student> getStudents()
	{
		return this.students;
	}
}
