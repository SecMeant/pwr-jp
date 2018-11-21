package lab04;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

public class StudentsAddFromPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	JPButton buttonAdd = new JPButton();
	JPButton buttonSaveDatabase = new JPButton();
	StudentsForm studentsForm = new StudentsForm();
	
	JPListView outputList;
	
	StudentsAddFromPanel(JPListView outputList)
	{
		this.outputList = outputList;
		
		this.buttonAdd.get().setFont(new Font("Arial", Font.PLAIN, MainWindow.BTNFONTSIZE));
		this.buttonAdd.get().setText("Add");
		this.buttonAdd.get().addActionListener(new AddStudentButtonListener(this));
		
		this.buttonSaveDatabase.get().setFont(new Font("Arial", Font.PLAIN, MainWindow.BTNFONTSIZE));
		this.buttonSaveDatabase.get().setText("Save all to file");
		this.buttonSaveDatabase.get().addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					Main.dataBase.SaveDataToFile();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}});
		
		// add elements
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		this.add(this.studentsForm, c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(this.buttonAdd, c);
		
		c.gridx = 0;
		c.gridy = 2;
		this.add(this.buttonSaveDatabase, c);
	}
	
	private class AddStudentButtonListener implements ActionListener
	{
		StudentsAddFromPanel parent;
		
		AddStudentButtonListener(StudentsAddFromPanel parent)
		{
			this.parent = parent;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(!this.validateInput())
				return;
			
			try
			{
				Main.dataBase.addStudent(this.parent.studentsForm.getFullFormInput());
			} 
			catch (DataBaseInsertException e1)
			{
				e1.printStackTrace();
				return;
			}
			
			this.parent.outputList.addElement(this.parent.studentsForm.getFullFormInput());
			this.parent.studentsForm.clearForm();
		}
		
		boolean validateInput()
		{
			if(Main.dataBase.getStudentByPesel(this.parent.studentsForm.getPeselInput()) != null)
				return false;
			return true;
		}
	}

}
