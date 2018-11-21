package lab04;

import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

class TableChangeReflector implements TableModelListener
{
	DefaultTableModel watchModel;
	DefaultTableModel outModel;
	
	TableChangeReflector(DefaultTableModel watchmodel, DefaultTableModel outmodel)
	{
		this.watchModel = watchmodel;
		this.outModel = outmodel;
	}
	
	@Override
	public void tableChanged(TableModelEvent e)
	{
		if(e.getType() == TableModelEvent.UPDATE)
		{		
			Vector<String> dataRow = Utils.getListRow(this.watchModel, e.getFirstRow());
			for(int i=0; i<3; i++)
			{
				this.outModel.setValueAt(dataRow.get(i), e.getFirstRow(), i);
			}
		}
		else if(e.getType() == TableModelEvent.INSERT)
		{
			Vector<String> dataRow = Utils.getListRow(this.watchModel, e.getFirstRow());
			this.outModel.addRow(dataRow);
		}
		else if(e.getType() == TableModelEvent.DELETE)
		{
			this.outModel.removeRow(e.getFirstRow());
		}
	}
}