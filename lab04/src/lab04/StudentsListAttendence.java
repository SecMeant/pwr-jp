package lab04;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import lab04.JPListView.ListModel;

public class StudentsListAttendence extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public static final String ATTENDENCE_TRUE = null;
	public static final String ATTENDENCE_FALSE = "X";
	
	public static final String[] HEADERS = new String[] {"---------", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	public static final boolean[] EDITABLE = new boolean[] {false, true, true, true, true, true};
	
	public static final Vector<String> hours = new Vector<>();
	
	NavigationPanel navigationPanel = new NavigationPanel();
	JPListView attendeceTable;
	StudentListFinal studentsTable;
	
	private int currentWeek = 1;
	private  String currentPeselSelected = "";
	
	// Used to prevent listeners on data change on the list to flush
	// recently read data from database
	private boolean isUpdatingAttendenceView = false;

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
		
		this.studentsTable.addSelectionListener(new TableSelectionEventHandler(this.studentsTable, this));
		this.attendeceTable.model.addTableModelListener(new AttendenceChangeListener(this));
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
		this.isUpdatingAttendenceView = true;
		
		WeekAttendence attendenceTable = Main.dataBase.getWeekAttendenceByPesel(this.currentPeselSelected, this.currentWeek);
		this.clearAttendenceTable();
		attendenceTable.attendence.forEach(att->{
			this.attendeceTable.model.setValueAt(att.value, att.hour, att.day);
		});
		
		this.isUpdatingAttendenceView = false;
	}
	
	private void clearAttendenceTable()
	{
		for(int i=0 ; i<this.attendeceTable.model.getRowCount() ; i++)
		{
			for(int j=1; j<this.attendeceTable.model.getColumnCount() ; j++)
			{
				this.attendeceTable.model.setValueAt(StudentsListAttendence.ATTENDENCE_TRUE, i, j);
			}
		}
	}

	private void updateAttendenceTable()
	{
		this.updateAttendeceTableData();
		this.updateAttendeceTableLabel();
	}
	
	class TableSelectionEventHandler implements ListSelectionListener
	{
		StudentsListAttendence parent;
		JPListView watchTable;
		
		TableSelectionEventHandler(JPListView toWatchTable, StudentsListAttendence parent)
		{
			this.watchTable = toWatchTable;
			this.parent = parent;
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			Vector<String> rowData = this.getSelectedRow(e.getSource());
			
			if(rowData.size() == 0)
			{
				this.parent.currentPeselSelected = "";
			}
			else
			{
				this.parent.currentPeselSelected = rowData.get(2);
			}
			
			this.parent.updateAttendenceTable();
		}	
		
		private Vector<String> getSelectedRow(DefaultListSelectionModel model)
		{
			int index = model.getMinSelectionIndex();
			
			if(index == -1)
				return new Vector<String>(6,6);
			
			return Utils.getListRow(this.watchTable.model, index);
		}
		
		private Vector<String> getSelectedRow(Object model)
		{
			return this.getSelectedRow((DefaultListSelectionModel) model);
		}
	}
	
	class AttendenceChangeListener implements TableModelListener
	{	
		StudentsListAttendence parent;
		
		AttendenceChangeListener(StudentsListAttendence parent)
		{
			this.parent = parent;
		}
		
		@Override
		public void tableChanged(TableModelEvent e)
		{
			// Changed outside of interesting area
			if(e.getColumn() < 1 || e.getFirstRow() == -1)
				return;
			
			// No student selected
			if(this.parent.currentPeselSelected.equals(""))
				return;
			
			if(this.parent.isUpdatingAttendenceView)
				return;
			
			System.out.println(String.format("%d %d %s",e.getFirstRow(), e.getColumn(), e.getSource().toString()));
			Main.dataBase.setStudentAttendenceByPesel(
					this.parent.currentPeselSelected,
					this.parent.currentWeek, 
					e.getColumn(), 
					e.getFirstRow(), 
					this.getInsertedValueFromEvent(e));
		}
		
		private String getInsertedValueFromEvent(TableModelEvent e)
		{
			ListModel source = (ListModel) e.getSource();
			return (String) source.parent.model.getValueAt(e.getFirstRow(),e.getColumn());
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
			
			this.parent.updateAttendenceTable();
		}
		
		boolean isForwardButton(Object button)
		{
			return this.parent.navigationPanel.isForwardButton(button);
		}
	}
}
