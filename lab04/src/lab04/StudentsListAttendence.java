package lab04;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StudentsListAttendence extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public static final String[] HEADERS = new String[] {"---------", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	public static final boolean[] EDITABLE = new boolean[] {false, true, true, true, true, true};
	
	public static final Vector<String> hours = new Vector<>();
	
	NavigationPanel navigationPanel = new NavigationPanel();
	JPListView attendeceTable;
	StudentListFinal studentsTable;
	
	int currentWeek = 1;

	StudentsListAttendence()
	{		
		GridBagConstraints c = new GridBagConstraints();
		
		this.attendeceTable = new JPListView(StudentsListAttendence.HEADERS, StudentsListAttendence.EDITABLE);
		c.gridx = 1;
		
		this.add(this.attendeceTable, c);
		
		this.studentsTable = new StudentListFinal();
		c.gridx = 2;
		this.add(this.studentsTable, c);
		
		c.gridx = 0;
		c.gridy = 11;
		this.add(this.navigationPanel, c);
		
		this.studentsTable.addSelectionListener(new TableSelectionExtracter(this.studentsTable));
		this.navigationPanel.addActionPerformedListener(new NavigationButtonListener(this));
		
		for(int i=0; i<10;i++)
		{
			Vector<String> ins = new Vector<>();
			this.attendeceTable.model.addRow(ins);
			this.attendeceTable.model.setValueAt(String.format("%d:%d%d", i+7, 0, 0), i, 0);
		}
	}
	
	private void incrementWeekNumber()
	{
		if(this.currentWeek > 40)
			return;
		
		this.currentWeek++;
		this.updateAttendenceTable();
	}
	
	private void decrementWeekNumber()
	{
		if(this.currentWeek <= 1)
			return;
		
		this.currentWeek--;
		this.updateAttendenceTable();
	}
	
	private void updateAttendeceTableLabel()
	{
		this.attendeceTable.setLabel(String.format("Week %d", this.currentWeek));
	}
	
	private void updateAttendeceTableData()
	{
		
	}
	
	private void updateAttendenceTable()
	{
		this.updateAttendeceTableData();
		this.updateAttendeceTableLabel();
	}
	
	class TableSelectionExtracter implements ListSelectionListener
	{
		JPListView watchTable;
		
		TableSelectionExtracter(JPListView toWatchTable)
		{
			this.watchTable = toWatchTable;
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			Vector<String> rowData = this.getSelectedRow(e.getSource());
			
			// TODO add database object
			//boolean[][] attendenceTable = dataBase.getWeekAttendeceByPesel(rowData.get(2), this.getRowIdFromSelectEvent(e));
		}	
		
		private Vector<String> getSelectedRow(DefaultListSelectionModel model)
		{
			int index = model.getMinSelectionIndex();
			return Utils.getListRow(this.watchTable.model, index);
		}
		
		private Vector<String> getSelectedRow(Object model)
		{
			return this.getSelectedRow((DefaultListSelectionModel) model);
		}
		
		private int getRowIdFromSelectEvent(ListSelectionEvent e)
		{
			return ((DefaultListSelectionModel) e.getSource()).getMinSelectionIndex();
		}
	}
	
	class NavigationButtonListener implements ActionListener
	{
		StudentsListAttendence parent;
		
		NavigationButtonListener(StudentsListAttendence parent)
		{
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(this.isForwardButton(e.getSource()))
			{
				this.parent.incrementWeekNumber();
				return;
			}
			
			this.parent.decrementWeekNumber();
		}
		
		boolean isForwardButton(Object button)
		{
			return this.parent.navigationPanel.isForwardButton(button);
		}
	}
}
