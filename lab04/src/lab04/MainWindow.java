package lab04;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	
	public static final Dimension WINDOW_DIMENSION = 
			new Dimension(MainWindow.WIDTH, MainWindow.HEIGHT);
	
	public static final int PANEL_WIDTH = MainWindow.WIDTH - 10;
	public static final int PANEL_HEIGHT = 40;
	
	public static final Dimension PANEL_DIMENSION =
			new Dimension(MainWindow.WIDTH,MainWindow.HEIGHT);
	
	public static final int BTNWIDTH = 80;
	public static final int BTNHEIGHT = 30;
	public static final int BTNFONTSIZE = 10;

	JPanel contentPanel = new JPanel();
	JPButton button = new JPButton();
	DataInput firstNameInput = new DataInput("First name:");
	DataInput surnameInput = new DataInput("Surname:");
	JPListView list = new JPListView();
	
	public MainWindow(String string)
	{
		// delegate construction
		super(string);
		
		// setup window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Button setup
		this.button.get().setPreferredSize(new Dimension(MainWindow.BTNWIDTH, MainWindow.BTNHEIGHT));
		this.button.get().setFont(new Font("Arial", Font.PLAIN, MainWindow.BTNFONTSIZE));
		this.button.get().setText("Push me!");
		this.button.get().addActionListener(new PushButtonActionLister(this));
		
		// set layout before adding elements
		this.setLayout(new SpringLayout());
		
		// add elements
		this.contentPanel.setLayout(new GridLayout(4,1));
		this.contentPanel.add(this.firstNameInput);
		this.contentPanel.add(this.surnameInput);
		this.contentPanel.add(this.button);
		this.contentPanel.add(this.list);

		this.setContentPane(this.contentPanel);
		
		// center
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.pack();
	}
	
	// Onclick handler
	private class PushButtonActionLister implements ActionListener
	{
		MainWindow parent;
		
		PushButtonActionLister(MainWindow parent)
		{
			this.parent = parent;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			String name = this.parent.firstNameInput.textfield.getText() + " " +
                          this.parent.surnameInput.textfield.getText();
			
			this.parent.list.addElement(name);
			this.parent.firstNameInput.textfield.setText("");
			this.parent.surnameInput.textfield.setText("");
		}
	}
}
