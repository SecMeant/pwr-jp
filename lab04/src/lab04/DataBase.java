package lab04;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class DataBase
{
	private String studentsOutFileName;
	private String attendenceOutFileName;
	
	private Vector<Student> students = new Vector<>();
	
	DataBase(String studentsFilePath, String attendenceFilePath)
	{
		this.studentsOutFileName = studentsFilePath;
		this.attendenceOutFileName = attendenceFilePath;
		
		try
		{
			this.fetchStudentsFromFile(studentsFilePath);
			this.fetchAttendenceFromFile(attendenceFilePath);
		} 
		catch (FileNotFoundException | FileParsingException | DataBaseInsertException e1)
		{
			e1.printStackTrace();
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
		DayAttendence datt = new DayAttendence(day, hour, attendenceValue);
		int idx = s.attendence[week].attendence.indexOf(datt);
		
		// Not found, add new
		if(idx == -1)
		{
			s.attendence[week].attendence.add(datt);
			return;
		}
		
		// Clear attendence
		if(attendenceValue == null)
			s.attendence[week].attendence.removeElementAt(idx);
		// Set new value
		else
			s.attendence[week].attendence.get(idx).value = attendenceValue;
	}
	
	void changeStudentInfoByPesel(String pesel, String newfirstName, String newsurname, String newpesel, String[] newmarks)
	{
		Student s = this.getStudentByPesel(pesel);
		
		if(s == null)
			return;
		
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
		
		String marks[] = Arrays.copyOfRange(newStudentInfo, 3, newStudentInfo.length);
		
		Student s = new Student(newStudentInfo[0], newStudentInfo[1], newStudentInfo[2], marks);
		
		this.addStudent(s);
	}
	
	public Vector<Student> getStudents()
	{
		return this.students;
	}
	
	private void fetchStudentsFromFile(String filepath)
	throws FileNotFoundException, FileParsingException, DataBaseInsertException
	{
		File file = new File(filepath); 
	    Scanner sc = new Scanner(file);
	    sc.useDelimiter("\n");
	    
	    while(sc.hasNext())
	    {
	    	String line = sc.next();
	    	String[] data = line.split(";");
	    	
	    	// Change all empty fields from null to empty string
	    	for(int i=0; i<data.length; i++)
	    	{
	    		if(data[i].equals("null"))
	    			data[i] = "";
	    	}
	    	
	    	if(data.length < 3)
	    		throw new FileParsingException("Error! 0 Bad aligned data occured while parsing students file.");
	    	this.addStudent(data);
	    }
	    
	    sc.close();
	}
	
	private void fetchAttendenceFromFile(String filepath)
	throws FileNotFoundException, FileParsingException
	{
		File file = new File(filepath); 
	    Scanner sc = new Scanner(file);
	    sc.useDelimiter("\n");
	    
	    while(sc.hasNext())
	    {
	    	String line = sc.next();
	    	String[] data = line.split(";");
	    	if(data.length < 1)
	    		throw new FileParsingException("Error! 1 Bad aligned data occured while parsing attendece file.");
	    	
	    	// Change all empty fields from null to empty string
	    	for(int i=0; i<data.length; i++)
	    	{
	    		if(data[i].equals("null"))
	    			data[i] = "";
	    	}
	    	
	    	for(int i=1; i<data.length; i++)
	    	{
	    		String[] attendence = data[i].split(",");
	    		if(attendence.length != 4)
	    			throw new FileParsingException(
	    					String.format("Error! 2 Bad aligned data occured while parsing attendece file. %d", attendence.length));
	    		this.setStudentAttendenceByPesel(data[0],
	    				                         Integer.parseInt(attendence[0]),
	    				                         Integer.parseInt(attendence[1]),
	    				                         Integer.parseInt(attendence[2]),
	    				                         attendence[3]);
	    	}
	    	
	    }
	    
	    sc.close();
	}
	
	public void SaveDataToFile() throws IOException
	{
		this.saveStudentsToFile();
		this.saveAttendenceToFile();
	}
	
	private void saveStudentsToFile() throws IOException
	{
		 FileWriter fileWriter = new FileWriter(this.studentsOutFileName);
		 
		 for(Student s : this.students)
		 {
			 fileWriter.write(String.format("%s;%s;%s",s.firstName,s.surname,s.getPesel()));
			 for(String mark : s.marks)
			 {
				 fileWriter.write(String.format(";%s",mark));
			 }
			 fileWriter.write("\n");
		 }
		 
		 fileWriter.close();
	}
	
	private void saveAttendenceToFile() throws IOException
	{
		FileWriter fileWriter = new FileWriter(this.attendenceOutFileName);
		 
		 for(Student s : this.students)
		 {
			 fileWriter.write(String.format("%s",s.getPesel()));
			 
			 for(int i=0; i<s.attendence.length; i++)
			 {
				 for(DayAttendence da : s.attendence[i].attendence)
				 {
					 fileWriter.write(String.format(";%s,%s,%s,%s",String.valueOf(i),da.day,da.hour,da.value));
				 }
			 }

			 fileWriter.write("\n");
		 }
		 
		 fileWriter.close();
	}
	
	public float getAverageByPesel(String pesel)
	{
		Student s = this.getStudentByPesel(pesel);
		float sum = 0;
		float marksCount = 0;
		
		for(String marks : s.marks)
		{
			for(String mark : marks.split(","))
			{
				try
				{
					sum += Float.parseFloat(mark);
					marksCount++;
				}
				catch(NumberFormatException e)
				{
					// do nothing
				}
			}
		}
		
		if(marksCount == 0)
			return 0;
		
		return sum/marksCount;
	}
	
	public float getClassAverage()
	{
		float sum = 0;
		float marksCount = 0;
		
		for(Student s : this.students)
		{
			for(String marks : s.marks)
			{
				for(String mark : marks.split(","))
				{
					try
					{
						sum += Float.parseFloat(mark);
						marksCount++;
					}
					catch(NumberFormatException e)
					{
						// do nothing
					}
				}
			}
		}
		
		if(marksCount == 0)
			return 0;
		
		return sum/marksCount;
	}
}
