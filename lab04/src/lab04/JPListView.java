package lab04;

import java.awt.Dimension;

import javax.swing.*;

public class JPListView extends JPanel
{

	private static final long serialVersionUID = 1L;
	
	private DefaultListModel<String> model;
	private JList<String> list;
	private JScrollPane scrollPane;
	
	{
		this.model = new DefaultListModel<String>();
		this.list = new JList<>(this.model);
		this.scrollPane = new JScrollPane(this.list);
		this.scrollPane.setPreferredSize(new Dimension(200,40));
		this.add(this.scrollPane);
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
	
}
