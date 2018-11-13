package lab04;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;

public class JPListView extends JPanel
{

	private static final long serialVersionUID = 1L;
	
	private JScrollPane scrollPane;
	private DefaultListModel<String> model;
	private JList<String> list;
	private JLabel label;
	
	{
		this.model = new DefaultListModel<String>();
		this.list = new JList<>(this.model);
		
		this.scrollPane = new JScrollPane(this.list);
		this.scrollPane.setPreferredSize(new Dimension(200,100));

		this.label = new JLabel();
		this.label.setHorizontalAlignment(JLabel.CENTER);
		
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.add(this.label, c);
		
		c.gridy = 1;
		c.gridheight = 4;
		this.add(this.scrollPane, c);
	}
	
	void addElement(String elem)
	{
		this.model.addElement(elem);
	}
	
	void removeElement(String element)
	{
		this.model.removeElement(element);
	}
	
	void removeElementAt(int elemId)
	{
		this.model.removeElementAt(elemId);
	}
	
	JScrollPane getPane()
	{
		return this.scrollPane;
	}
	
	void setLabel(String text)
	{
		this.label.setText(text);
	}
}
