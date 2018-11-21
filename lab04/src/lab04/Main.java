package lab04;

public class Main
{
	public static final String studentsFileName = "C:\\Users\\hlz\\Desktop\\data\\students.txt";
	public static final String attendenceFileName = "C:\\Users\\hlz\\Desktop\\data\\attendence.txt";
	public static final DataBase dataBase = new DataBase(studentsFileName, attendenceFileName);
	
	private static void createAndShowGui()
	{
		new MainWindow("Hello world swing");
	}
	
	public static void main(String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
		
	}
}
