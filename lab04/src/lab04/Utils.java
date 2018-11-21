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

	public static void copyArray(String[] dest, String[] src)
	{
		for(int i=0; i<min(dest.length, src.length); i++)
		{
			dest[i] = src[i];
		}
		
	}
	
	public static void copyArray(String[] dest, String[] src, int startOffset)
	{
		for(int i=0; i<min(dest.length, src.length); i++)
		{
			dest[i] = src[i+startOffset];
		}
		
	}
	
	public static int min(int a, int b)
	{
		if(a < b)
			return a;
		return b;
	}
}
