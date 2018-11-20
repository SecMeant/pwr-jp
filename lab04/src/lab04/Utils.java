package lab04;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Utils
{		
	static Vector<String> getListRow(DefaultTableModel model, int i)
	{
		Vector<String> ret = new Vector<String>();
		for(int j=0; j<model.getColumnCount(); j++)
			ret.addElement((String) model.getValueAt(i, j));
		
		return ret;
	}
}
