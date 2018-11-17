package lab04;

import java.awt.GridBagConstraints;
import java.util.Vector;

public class StudentsListAttendence extends JPListView
{
	private static final long serialVersionUID = 1L;
	
	public static final String[] HEADERS = new String[] {"---------", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	public static final boolean[] EDITABLE = new boolean[] {false, true, true, true, true, true};
	public static final Vector<String> hours = new Vector<>();
	
	NavigationPanel navigationPanel = new NavigationPanel();

	StudentsListAttendence()
	{
		super(StudentsListAttendence.HEADERS, StudentsListAttendence.EDITABLE);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 11;
		this.add(this.navigationPanel, c);
		
		for(int i=0; i<10;i++)
		{
			Vector<String> ins = new Vector<>();
			this.model.addRow(ins);
			this.model.setValueAt(String.format("%d:%d%d", i+7, 0, 0), i, 0);
		}
	}
	
}
