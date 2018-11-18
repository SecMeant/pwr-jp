package lab04;

import java.awt.GridBagConstraints;
import java.util.Vector;

import javax.swing.JPanel;

public class StudentsListAttendence extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public static final String[] HEADERS = new String[] {"---------", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	public static final boolean[] EDITABLE = new boolean[] {false, true, true, true, true, true};
	public static final String[] HEADERS2 = new String[] {"Name", "Surname", "Pesel"};
	public static final boolean[] EDITABLE2 = new boolean[] {false, false, false};
	public static final Vector<String> hours = new Vector<>();
	
	NavigationPanel navigationPanel = new NavigationPanel();
	JPListView attendeceTable;
	JPListView studentsTable;

	StudentsListAttendence()
	{		
		GridBagConstraints c = new GridBagConstraints();
		
		this.attendeceTable = new JPListView(StudentsListAttendence.HEADERS, StudentsListAttendence.EDITABLE);
		c.gridx = 1;
		
		this.add(this.attendeceTable, c);
		
		this.studentsTable = new JPListView(StudentsListAttendence.HEADERS2, StudentsListAttendence.EDITABLE2);
		c.gridx = 2;
		this.add(this.studentsTable, c);
		
		
		c.gridx = 0;
		c.gridy = 11;
		this.add(this.navigationPanel, c);
		
		for(int i=0; i<10;i++)
		{
			Vector<String> ins = new Vector<>();
			this.attendeceTable.model.addRow(ins);
			this.attendeceTable.model.setValueAt(String.format("%d:%d%d", i+7, 0, 0), i, 0);
		}
	}
	
	
	
}
