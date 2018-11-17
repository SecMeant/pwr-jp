package lab04;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JPListView extends JPanel
{

	private static final long serialVersionUID = 1L;
	
	public static final int LISTHEIGHT = 10;
	
	public static final String[] HEADERLIST = new String[] {"First name", "Surname", "Pesel"};

	private JScrollPane scrollPane;
	protected DefaultTableModel model;
	private JTable list;
	private JLabel label;
	
	{
		// Create model with editable only part of cells
		this.model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int rowIndex, int columnIndex) {
            	if (columnIndex > 2)
            		return true;
            	return false;
            }
		};
		
		this.list = new JTable(this.model);
		
		for(String header : JPListView.HEADERLIST)
		{
			this.model.addColumn(header);
		}
		
		this.scrollPane = new JScrollPane(this.list);

		this.label = new JLabel();
		this.label.setHorizontalAlignment(JLabel.CENTER);
		
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
}
