package lab04;

import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

class TableChangeExtracter implements TableModelListener
{
	DefaultTableModel watchModel;
	DefaultTableModel outModel;
	
	TableChangeExtracter(DefaultTableModel watchmodel, DefaultTableModel outmodel)
	{
		this.watchModel = watchmodel;
		this.outModel = outmodel;
	}
	
	@Override
	public void tableChanged(TableModelEvent e)
	{
		if(e.getType() == TableModelEvent.UPDATE)
		{		
			Vector<String> dataRow = this.getRow(e.getFirstRow());
			for(int i=0; i<3; i++)
			{
				this.outModel.setValueAt(dataRow.get(i), e.getFirstRow(), i);
			}
		}
		else if(e.getType() == TableModelEvent.INSERT)
		{
			Vector<String> dataRow = this.getRow(e.getFirstRow());
			this.outModel.addRow(dataRow);
		}
		else if(e.getType() == TableModelEvent.DELETE)
		{
			System.out.println("DELETE");
			this.outModel.removeRow(e.getFirstRow());
		}
	}
	
	Vector<String> getRow(int i)
	{
		Vector<String> ret = new Vector<String>();
		for(int j=0; j<3; j++)
			ret.addElement((String) this.watchModel.getValueAt(i, j));
		
		return ret;
	}
}