package lab04;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class OtherPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	StudentListFinal studentList = new StudentListFinal();
	SummaryPanel summary = new SummaryPanel();
	
	OtherPanel(JPListView toWatchTable)
	{
		this.setLayout(new GridBagLayout());
		this.studentList.addSelectionListener(new TableSelectionEventHandler(this.studentList ,this));
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.add(studentList, c);
		
		c.gridx = 1;
		this.add(summary, c);
		
		this.summary.setPersonalAverage("5");
		this.summary.setClassAverage("2");
		
		this.summary.setVisible(true);
		this.studentList.setVisible(true);
	}

	class TableSelectionEventHandler implements ListSelectionListener
	{
		OtherPanel parent;
		JPListView watchTable;
		
		TableSelectionEventHandler(JPListView toWatchTable, OtherPanel parent)
		{
			this.watchTable = toWatchTable;
			this.parent = parent;
		}
		
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			Vector<String> rowData = this.getSelectedRow(e.getSource());
			
			System.out.println(Main.dataBase.getAverageByPesel(rowData.get(2)));
			System.out.println(Main.dataBase.getClassAverage());
			
			this.parent.summary.setPersonalAverage(String.valueOf(Main.dataBase.getAverageByPesel(rowData.get(2))));
			this.parent.summary.setClassAverage(String.valueOf(Main.dataBase.getClassAverage()));
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
}
