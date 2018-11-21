package lab04;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
	JPanel AddStudentPanel = new JPanel();
	JPanel MarksPanel = new JPanel();
	JPanel AttendencePanel = new JPanel();
	JPanel OtherPanel = new JPanel();
	StudentsListGeneral studentsListGeneral = new StudentsListGeneral();
	StudentsListAttendence studentsListAttendence = new StudentsListAttendence();
	
	public MainWindow(String string)
	{
		// delegate construction
		super(string);
		
		this.tabPane.addTab("Add student", this.AddStudentPanel);
		this.tabPane.addTab("Marks", this.MarksPanel);
		this.tabPane.addTab("Attendence", this.AttendencePanel);
		this.tabPane.addTab("Other", this.OtherPanel);
		
		// setup window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.studentsListGeneral.setLabel("Students");

		this.studentsListAttendence.attendeceTable.setLabel("Week 1");
		this.studentsListAttendence.studentsTable.setLabel("Students list");
		this.studentsListAttendence.studentsTable.watch(this.studentsListGeneral);
		
		// get current data from database
		this.studentsListGeneral.syncWithDataBase(Main.dataBase);
		
		// set layout before adding elements
		this.setLayout(new SpringLayout());
		
		// Add elements
		this.AddStudentPanel.add(new StudentsAddFromPanel(this.studentsListGeneral));
		this.MarksPanel.add(studentsListGeneral);
		this.AttendencePanel.add(studentsListAttendence);

		this.setContentPane(this.tabPane);
		
		// center
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
}
