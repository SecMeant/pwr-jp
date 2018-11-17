package lab04;

import java.awt.Dimension;

public class StudentsMarkList extends JPListView
{
	private static final long serialVersionUID = 1L;

	public static final int PANELWIDTH = 900;
	public static final int PANELHEIGHT = 350;
	public static final Dimension STUDENTSPANELDIMENSION = 
			new Dimension(StudentsListGeneral.PANELWIDTH, StudentsListGeneral.PANELHEIGHT);
	
	{
		this.getPane().setPreferredSize(StudentsListGeneral.STUDENTSPANELDIMENSION);
	}
}
