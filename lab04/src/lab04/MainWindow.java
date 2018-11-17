package lab04;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	public static final String[] COURSES = new String[] {"Programming","Digital signal processing","English"};

	JTabbedPane tabPane = new JTabbedPane();
	JPanel AddStudentPanel = new JPanel();
	JPanel MarksPanel = new JPanel();
	JPanel OtherPanel = new JPanel();
	JPButton button = new JPButton();
	StudentsForm studentsForm = new StudentsForm();
	StudentsListGeneral studentsListGeneral = new StudentsListGeneral(MainWindow.COURSES);
	
	public MainWindow(String string)
	{
		// delegate construction
		super(string);
		
		this.tabPane.addTab("Add / Remove student", this.AddStudentPanel);
		this.tabPane.addTab("Marks", this.MarksPanel);
		this.tabPane.addTab("Attendence", new JPanel());
		this.tabPane.addTab("Other", this.OtherPanel);
		
		// setup window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* AddStudentsPanel setup */
		// Button setup
		this.button.get().setPreferredSize(new Dimension(MainWindow.BTNWIDTH, MainWindow.BTNHEIGHT));
		this.button.get().setFont(new Font("Arial", Font.PLAIN, MainWindow.BTNFONTSIZE));
		this.button.get().setText("Add");
		this.button.get().addActionListener(new PushButtonActionLister(this));
		
		this.studentsListGeneral.setLabel("Students");
		
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
		
		/* MarksPanel setup */
		this.MarksPanel.add(studentsListGeneral);

		this.setContentPane(this.tabPane);
		
		// center
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.pack();
	}
	
	// Onclick handler
	private class PushButtonActionLister implements ActionListener
	{
		MainWindow parent;
		
		PushButtonActionLister(MainWindow parent)
		{
			this.parent = parent;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			this.parent.studentsListGeneral.addElement(this.parent.studentsForm.getFullFormInput());
			this.parent.studentsForm.clearForm();
		}
	}
}
