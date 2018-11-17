package lab04;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class StudentsListGeneral extends JPListView
{
	private static final long serialVersionUID = 1L;
	
	public static final int PANELWIDTH = 600;
	public static final int PANELHEIGHT = 300;
	public static final Dimension STUDENTSPANELDIMENSION = 
			new Dimension(StudentsListGeneral.PANELWIDTH, StudentsListGeneral.PANELHEIGHT);
	
	private JButton deleteButton;
	
	StudentsListGeneral(String[] courses)
	{
		for(String header : courses)
		{
			this.model.addColumn(header);
		}
		
		this.getPane().setPreferredSize(StudentsListGeneral.STUDENTSPANELDIMENSION);
		
		this.deleteButton = new JButton();
		this.deleteButton.setPreferredSize(new Dimension(MainWindow.BTNWIDTH, MainWindow.BTNHEIGHT));
		this.deleteButton.setFont(new Font("Arial", Font.PLAIN, MainWindow.BTNFONTSIZE));
		this.deleteButton.setText("Usun");
		this.deleteButton.addActionListener(new RemoveButtonActionListener(this));
		
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
}



