package lab04;

public class StudentListFinal extends JPListView
{
	private static final long serialVersionUID = 1L;
	
	public static final String[] HEADERS = new String[] {"Name", "Surname", "Pesel"};
	public static final boolean[] EDITABLE = new boolean[] {false, false, false};
		
	TableChangeExtracter changeExtracter;

	StudentListFinal()
	{
		super(StudentListFinal.HEADERS, StudentListFinal.EDITABLE);
	}
	
	public void watch(JPListView toWatchTable)
	{
		this.changeExtracter = new TableChangeExtracter(toWatchTable.model, this.model);
		toWatchTable.model.addTableModelListener(this.changeExtracter);
	}
}
