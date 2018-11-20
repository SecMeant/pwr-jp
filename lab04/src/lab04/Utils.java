package lab04;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Utils
{		
	
	public static final int ATTENDENCE_WEEK_COUNT = 40;
	
	static Vector<String> getListRow(DefaultTableModel model, int i)
	{
		Vector<String> ret = new Vector<String>();
		for(int j=0; j<model.getColumnCount(); j++)
			ret.addElement((String) model.getValueAt(i, j));
		
		return ret;
	}
	
	public static boolean isNumeric(String str)
	{
		try 
		{ 
			Integer.parseInt(str);
			return true;
		}  
		catch (NumberFormatException e) 
		{ 
			return false;
		} 
	}
}
