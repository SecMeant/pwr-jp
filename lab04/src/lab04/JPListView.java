package lab04;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class JPListView extends JPanel
{

	private static final long serialVersionUID = 1L;
	
	public static final int LISTHEIGHT = 10;

	private JScrollPane scrollPane;
	protected DefaultTableModel model;
	private JTable list;
	private JLabel label;
	
	JPListView(String[] headers, boolean[] canEdit)
	{
		// Create model with editable only part of cells
		this.model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			
			boolean[] editable = canEdit;

            public boolean isCellEditable(int rowIndex, int columnIndex) {
            	return this.editable[columnIndex];
            }
		};
		
		this.list = new JTable(this.model);
		
		this.scrollPane = new JScrollPane(this.list);
		this.scrollPane.setSize(400, 200);

		this.label = new JLabel();
		this.label.setHorizontalAlignment(JLabel.CENTER);
		
		for(String header : headers)
		{
			this.model.addColumn(header);
		}
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.add(this.label, c);
		
		c.gridy = 1;
		c.gridheight = JPListView.LISTHEIGHT;
		this.add(this.scrollPane, c);
	}
	
	void addElement(String[] elem)
	{
		this.model.addRow(elem);
	}
	
	void removeElementAt(int elemId)
	{
		this.model.removeRow(elemId);
	}
	
	JScrollPane getPane()
	{
		return this.scrollPane;
	}
	
	void setLabel(String text)
	{
		this.label.setText(text);
	}
	
	int getSelectedItemIndex()
	{
		return this.list.getSelectedRow();
	}
	
	void updateListView(String[][] data)
	{
		for(int i=0; i<data.length; i++)
		{
			for(int j=0; j<data[i].length; j++)
			{
				try
				{
					this.model.setValueAt(data[i][j], i, j);
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					this.model.addRow(data[i]);
				}
			}
		}
		
	}
	
	void addSelectionListener(ListSelectionListener selectionListener)
	{
		this.list.getSelectionModel().addListSelectionListener(selectionListener);
	}
}
