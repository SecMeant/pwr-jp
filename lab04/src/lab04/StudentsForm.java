package lab04;

import javax.swing.*;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


public class StudentsForm extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private DataInput firstNameInput = new DataInput("First name:");
	private DataInput surnameInput = new DataInput("Surname:");
	private DataInput peselInput = new DataInput("Pesel:");

	{
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		this.add(this.firstNameInput,c);
		
		c.gridy = 1;
		this.add(this.surnameInput,c);
		
		c.gridy = 2;
		this.add(this.peselInput,c);
	}
	
	public String getFirstNameInput()
	{
		return this.firstNameInput.textfield.getText();
	}
	
	public String getSurnameInput()
	{
		return this.surnameInput.textfield.getText();
	}
	
	public String getPeselInput()
	{
		return this.peselInput.textfield.getText();
	}
	
	public String[] getFullFormInput()
	{
		return new String[] {this.getFirstNameInput(), this.getSurnameInput(), this.getPeselInput()};
	}
	
	public void clearFirstNameInput()
	{
		this.firstNameInput.textfield.setText("");
	}
	
	public void clearSurnameInput()
	{
		this.surnameInput.textfield.setText("");
	}
	
	public void clearPeselInput()
	{
		this.peselInput.textfield.setText("");
	}
	
	public void clearForm()
	{
		this.clearFirstNameInput();
		this.clearSurnameInput();
		this.clearPeselInput();
	}
}
