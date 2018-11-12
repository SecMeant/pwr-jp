package lab04;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainWindow extends JFrame
{
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	
	public static final int BTNWIDTH = 80;
	public static final int BTNHEIGHT = 30;
	public static final int BTNFONTSIZE = 10;

	JPanel contentPanel = new JPanel();
	JButton button = new JButton();
	DataInput firstNameInput = new DataInput("First name:");
	DataInput surnameInput = new DataInput("Surname:");
	
	public MainWindow(String string) {
		// delegate construction
		super(string);
		
		// setup window
		//this.setSize(MainWindow.WIDTH, MainWindow.HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.button.setPreferredSize(new Dimension(MainWindow.BTNWIDTH, MainWindow.BTNHEIGHT));
		this.button.setFont(new Font("Arial", Font.PLAIN, MainWindow.BTNFONTSIZE));
		this.button.setText("Push me!");
		this.button.addActionListener(new PushButtonActionLister(this));
		
		// set layout before adding elements
		this.setLayout(new SpringLayout());
		
		// add elements
		this.contentPanel.setLayout(new GridLayout(3,1));
		this.contentPanel.add(this.firstNameInput);
		this.contentPanel.add(this.surnameInput);
		this.contentPanel.add(button);
		
		this.setContentPane(this.contentPanel);
		
		this.setLocationRelativeTo(null); // center
		this.setVisible(true);
		this.pack();
	}
	
	private class PushButtonActionLister implements ActionListener
	{
		MainWindow parent;
		
		PushButtonActionLister(MainWindow parent)
		{
			this.parent = parent;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			System.out.println(String.format("Your name is %s %s", 
					            this.parent.firstNameInput.textfield.getText(),
					            this.parent.surnameInput.textfield.getText()));
			this.parent.firstNameInput.textfield.setText("");
			this.parent.surnameInput.textfield.setText("");
		}
	}

	private static final long serialVersionUID = 1L;
	
}
