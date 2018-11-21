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
	JPButton button = new JPButton();
	JPButton buttonSaveDatabase = new JPButton();
	StudentsForm studentsForm = new StudentsForm();
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
		
		/* AddStudentsPanel setup */
		// Button setup
		this.button.get().setFont(new Font("Arial", Font.PLAIN, MainWindow.BTNFONTSIZE));
		this.button.get().setText("Add");
		this.button.get().addActionListener(new AddStudentButtonListener(this));
		
		this.buttonSaveDatabase.get().setFont(new Font("Arial", Font.PLAIN, MainWindow.BTNFONTSIZE));
		this.buttonSaveDatabase.get().setText("Save all to file");
		this.buttonSaveDatabase.get().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					Main.dataBase.SaveDataToFile();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}});
		
		this.studentsListGeneral.setLabel("Students");
		this.studentsListAttendence.attendeceTable.setLabel("Week 1");
		this.studentsListAttendence.studentsTable.setLabel("Students list");
		this.studentsListAttendence.studentsTable.watch(this.studentsListGeneral);
		this.studentsListGeneral.syncWithDataBase(Main.dataBase);
		
		// set layout before adding elements
		this.setLayout(new SpringLayout());
		
		// add elements
		this.AddStudentPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		this.AddStudentPanel.add(this.studentsForm, c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.AddStudentPanel.add(this.button, c);
		
		c.gridx = 0;
		c.gridy = 2;
		this.AddStudentPanel.add(this.buttonSaveDatabase, c);
		
		/* MarksPanel setup */
		this.MarksPanel.add(studentsListGeneral);
		
		/* AttendecePanel setup */
		this.AttendencePanel.add(studentsListAttendence);

		this.setContentPane(this.tabPane);
		
		// center
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	// Onclick handler
	private class AddStudentButtonListener implements ActionListener
	{
		MainWindow parent;
		
		AddStudentButtonListener(MainWindow parent)
		{
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(!this.validateInput())
				return;
			
			try
			{
				Main.dataBase.addStudent(this.parent.studentsForm.getFullFormInput());
			} 
			catch (DataBaseInsertException e1)
			{
				e1.printStackTrace();
				return;
			}
			
			this.parent.studentsListGeneral.addElement(this.parent.studentsForm.getFullFormInput());
			this.parent.studentsForm.clearForm();
		}
		
		boolean validateInput()
		{
			if(Main.dataBase.getStudentByPesel(this.parent.studentsForm.getPeselInput()) != null)
				return false;
			return true;
		}
	}
}
