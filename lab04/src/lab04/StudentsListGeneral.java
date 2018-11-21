package lab04;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class StudentsListGeneral extends JPListView
{
	private static final long serialVersionUID = 1L;
	
	public static final String[] HEADERS = new String[] {"First name", "Surname", "Pesel", "Programming","Digital signal processing","English"};
	public static final boolean[] EDITABLE = new boolean[] {false, false, true, true, true, true};
	
	public static final int PANELWIDTH = 600;
	public static final int PANELHEIGHT = 300;
	public static final Dimension STUDENTSPANELDIMENSION = 
			new Dimension(StudentsListGeneral.PANELWIDTH, StudentsListGeneral.PANELHEIGHT);
	
	private JButton deleteButton;
	
	StudentsListGeneral()
	{
		super(StudentsListGeneral.HEADERS, StudentsListGeneral.EDITABLE);
		
		this.getPane().setPreferredSize(StudentsListGeneral.STUDENTSPANELDIMENSION);
		
		this.deleteButton = new JButton();
		this.deleteButton.setPreferredSize(new Dimension(MainWindow.BTNWIDTH, MainWindow.BTNHEIGHT));
		this.deleteButton.setFont(new Font("Arial", Font.PLAIN, MainWindow.BTNFONTSIZE));
		this.deleteButton.setText("Usun");
		this.deleteButton.addActionListener(new RemoveButtonActionListener(this));
		this.model.addTableModelListener(new DataBaseSynchronizer(this.model));
		
		GridBagConstraints c = new GridBagConstraints();
		// Use previous height to calc new pos
		c.gridy = JPListView.LISTHEIGHT+1;
		c.gridheight = 1;
		this.add(this.deleteButton, c);
	}
	
	private class RemoveButtonActionListener implements ActionListener
	{
		StudentsListGeneral parent;
		
		RemoveButtonActionListener(StudentsListGeneral parent)
		{
			this.parent = parent;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			int idx = this.parent.getSelectedItemIndex();
			
			if(idx == -1)
				return;
			
			
			this.parent.removeElementAt(idx);
		}
	}
	
	private class DataBaseSynchronizer implements TableModelListener
	{
		private DefaultTableModel watchModel;
		
		DataBaseSynchronizer(DefaultTableModel toWatch)
		{
			this.watchModel = toWatch;
		}
		
		@Override
		public void tableChanged(TableModelEvent e)
		{
			// Other case are handled when button are clicked
			if(e.getType() == TableModelEvent.UPDATE)
			{		
				System.out.println("UPDATE");
				Vector<String> dataRow = Utils.getListRow(this.watchModel, e.getFirstRow());
				
				// Copy marks
				String[] marks = new String[dataRow.size()-3];
				String[] dataRowCast = dataRow.toArray(new String[dataRow.size()]);
				Utils.copyArray(marks, dataRowCast,3);
				
				Main.dataBase.changeStudentInfoByPesel(dataRow.get(2), dataRow.get(0), dataRow.get(1), dataRow.get(2), marks);
			}
		}
	}
}



