package lab06;

import java.awt.Color;

import java.awt.Dimension;
import javax.swing.*;

class	TaskList extends JPanel{
	public static final int WIDTH = MainWindow.WINDOW_WIDTH;
	public static final int HEIGHT = MainWindow.WINDOW_HEIGHT;
	public static final Dimension SIZE = new Dimension(TaskList.WIDTH, TaskList.HEIGHT);

	private JList<String> list = new JList<String>(new DefaultListModel<String>());
	private JScrollPane scrollPane = new JScrollPane(this.list);

	private AddTaskForm addTaskForm = new AddTaskForm();

	public TaskList(){
		this.list.setFixedCellWidth(TaskList.WIDTH-20);

		this.scrollPane.setMinimumSize(TaskList.SIZE);
		this.scrollPane.getViewport().setViewSize(TaskList.SIZE);

		this.add(this.scrollPane);
		this.add(this.addTaskForm);

		this.setVisible(true);
	}

	public void addElement(String element){
		((DefaultListModel<String>)this.list.getModel()).addElement(element);
	}

	public String getElement(int index){
		return null;
	}

	public String getSelectedElement(){
		return this.list.getSelectedValue();
	}
}
