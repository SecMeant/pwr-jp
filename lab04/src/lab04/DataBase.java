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
			this.setStudentAttendenceByPesel("997998999", 2, 4, 8, StudentsListAttendence.ATTENDENCE_FALSE);
			this.setStudentAttendenceByPesel("997998999", 2, 4, 8, StudentsListAttendence.ATTENDENCE_TRUE);
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
	
	void setStudentAttendenceByPesel(String pesel, int week, int day, int hour, String attendenceValue)
	{
		Student s = this.getStudentByPesel(pesel);
		DayAttendence datt = new DayAttendence(day, hour);
		int idx = s.attendence[week].attendence.indexOf(datt);
		
		// Not found
		if(idx == -1)
		{
			s.attendence[week].attendence.add(datt);
			return;
		}
		
		s.attendence[week].attendence.removeElementAt(idx);
	}
	
	void changeStudentInfoByPesel(String pesel, String newfirstName, String newsurname, String newpesel, String[] newmarks)
	{
		Student s = this.getStudentByPesel(pesel);
		
		if(s == null)
			return;
		
		System.out.println("New marks");
		for(String mark:newmarks)
		{
			System.out.println(mark);
		}
		
		if(newfirstName != null)
			s.firstName = newfirstName;
		if(newsurname != null)
			s.surname = newsurname;
		if(newpesel != null)
			s.setPesel(newpesel);
		if(newmarks != null)
			Utils.copyArray(s.marks, newmarks);
	}
	
	void removeStudentByPesel(String pesel)
	{
		for(int i=0; i<this.students.size(); i++)
		{
			if(this.students.get(i).getPesel().equals(pesel))
			{
				this.students.removeElementAt(i);
				break;
			}
		}
	}
	
	public void addStudent(Student s) throws DataBaseInsertException
	{
		if(this.getStudentByPesel(s.getPesel()) != null)
			throw new DataBaseInsertException("User with given pesel already exists in database");
		
		this.students.add(s);
	}
	
	public void addStudent(String[] newStudentInfo) throws DataBaseInsertException
	{
		if(newStudentInfo.length <3)
			return;
		
		this.addStudent(new Student(newStudentInfo[0], newStudentInfo[1], newStudentInfo[2]));
	}
	
	public Vector<Student> getStudents()
	{
		return this.students;
	}
}
