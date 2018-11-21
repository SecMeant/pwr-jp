package lab04;

import java.awt.Dimension;

import javax.swing.*;

public class MainWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	
	public static final Dimension WINDOW_DIMENSION = 
			new Dimension(MainWindow.WIDTH, MainWindow.HEIGHT);
	
	public static final int PANEL_WIDTH = MainWindow.WIDTH - 10;
	public static final int PANEL_HEIGHT = 40;
	
	public static final Dimension PANEL_DIMENSION =
			new Dimension(MainWindow.WIDTH,MainWindow.HEIGHT);
	
	public static final int BTNWIDTH = 80;
	public static final int BTNHEIGHT = 30;
	public static final int BTNFONTSIZE = 10;

	JTabbedPane tabPane = new JTabbedPane();
	JPanel addStudentPanelTab = new JPanel();
	JPanel marksPanelTab = new JPanel();
	JPanel attendencePanelTab = new JPanel();
	JPanel otherPanelTab = new JPanel();
	StudentsListGeneral studentsListGeneral = new StudentsListGeneral();
	StudentsListAttendence studentsListAttendence = new StudentsListAttendence();
	OtherPanel otherPanel = new OtherPanel(this.studentsListGeneral);
	
	public MainWindow(String string)
	{
		// delegate construction
		super(string);
		
		this.tabPane.addTab("Add student", this.addStudentPanelTab);
		this.tabPane.addTab("Marks", this.marksPanelTab);
		this.tabPane.addTab("Attendence", this.attendencePanelTab);
		this.tabPane.addTab("Other", this.otherPanelTab);
		
		// setup window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.studentsListGeneral.setLabel("Students");

		this.studentsListAttendence.attendeceTable.setLabel("Week 1");
		this.studentsListAttendence.studentsTable.setLabel("Students list");
		this.studentsListAttendence.studentsTable.watch(this.studentsListGeneral);
		
		this.otherPanel.studentList.watch(this.studentsListGeneral);
		
		// get current data from database
		this.studentsListGeneral.syncWithDataBase(Main.dataBase);
		
		// set layout before adding elements
		this.setLayout(new SpringLayout());
		
		// Add elements
		this.addStudentPanelTab.add(new StudentsAddFormPanel(this.studentsListGeneral));
		this.marksPanelTab.add(studentsListGeneral);
		this.attendencePanelTab.add(studentsListAttendence);
		this.otherPanelTab.add(this.otherPanel);

		this.setContentPane(this.tabPane);
		
		// center
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
}
