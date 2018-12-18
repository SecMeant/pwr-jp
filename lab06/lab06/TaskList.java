package lab06;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.*;

class ActionPanel extends JPanel{
	public AddTaskForm addTaskForm;
	public JButton refreshButton;
	public JButton solveButton;

	ActionPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.addTaskForm = new AddTaskForm();
		this.refreshButton = new JButton("Refresh");
		this.solveButton = new JButton("Solve");

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(this.refreshButton);
		buttonPanel.add(this.solveButton);

		this.add(buttonPanel);
		this.add(this.addTaskForm);
	}
}

class	TaskList extends JPanel{
	public static final int WIDTH = MainWindow.WINDOW_WIDTH;
	public static final int HEIGHT = MainWindow.WINDOW_HEIGHT;
	public static final Dimension SIZE = new Dimension(TaskList.WIDTH, TaskList.HEIGHT);

	private JList<String> list = new JList<String>(new DefaultListModel<String>());
	private JScrollPane scrollPane = new JScrollPane(this.list);

	private ActionPanel actionPanel = new ActionPanel();

	public TaskList(ActionListener addTaskButtonListener, ActionListener getTaskListButtonListener,
	                ActionListener solveTaskButtonListener){
		this.actionPanel.addTaskForm.addButtonPressedListener(addTaskButtonListener);
		this.actionPanel.refreshButton.addActionListener(getTaskListButtonListener);
		this.actionPanel.solveButton.addActionListener(solveTaskButtonListener);

		this.list.setFixedCellWidth(TaskList.WIDTH-20);

		this.scrollPane.setMinimumSize(TaskList.SIZE);
		this.scrollPane.getViewport().setViewSize(TaskList.SIZE);

		this.add(this.scrollPane);
		this.add(this.actionPanel);

		this.setVisible(true);
	}

	public void addElement(String element){
		((DefaultListModel<String>)this.list.getModel()).addElement(element);
	}

	public String getElement(int index){
		return ((DefaultListModel<String>)this.list.getModel()).get(index);
	}

	public String getSelectedElement(){
		return this.list.getSelectedValue();
	}

	public void clear(){
		((DefaultListModel<String>)this.list.getModel()).clear();
	}

	public String[] getInput(){
		return this.actionPanel.addTaskForm.getInput();
	}
}
