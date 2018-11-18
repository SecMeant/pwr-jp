package lab04;

import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

class TableChangeExtracter implements TableModelListener
{
	DefaultTableModel tableModel;
	
	TableChangeExtracter(DefaultTableModel model)
	{
		this.tableModel = model;
	}
	
	@Override
	public void tableChanged(TableModelEvent e)
	{
		if(e.getType() == TableModelEvent.UPDATE)
		{
			System.out.print("UPDATE row:");
			this.getRow(e.getFirstRow()).forEach(s->{
				System.out.print(s);
				System.out.print(" ");
			});
			System.out.println("");
		}
		else if(e.getType() == TableModelEvent.INSERT)
		{
			System.out.print("INSERT row:");
			this.getRow(e.getFirstRow()).forEach(s->{
				System.out.print(s);
				System.out.print(" ");
			});
			System.out.println("");
		}
		else if(e.getType() == TableModelEvent.DELETE)
		{
			System.out.print("DELETE row:");
			this.getRow(e.getFirstRow()).forEach(s->{
				System.out.print(s);
				System.out.print(" ");
			});
			System.out.println("");
		}
	}
	
	Vector<String> getRow(int i)
	{
		return (Vector<String>) this.tableModel.getDataVector().elementAt(i);
	}
}