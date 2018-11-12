package lab04;

public class Main
{
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
