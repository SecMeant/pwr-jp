package lab04;

public class Main
{
	public static final DataBase dataBase = new DataBase("asdf", "qwer");
	
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
