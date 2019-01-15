package lab07;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TerminalGui extends JFrame{
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 400;

	private Terminal parent;

	private JPanel mainPanel = new JPanel();
	private JLabel ticketNumberLabel = new JLabel("Click on buttons to order ticket.");
	private JButton otherTicketButton = new JButton("other");
	private JButton highPriorityButton= new JButton("high priority");

	public TerminalGui(Terminal parent){
		this.parent = parent;
		this.initWindow();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(TerminalGui.WINDOW_WIDTH, TerminalGui.WINDOW_HEIGHT);
		this.setVisible(true);
	}

	private void initWindow(){
		this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
	
		this.otherTicketButton.addActionListener(new TicketButtonListener(this.parent, "other"));
		this.highPriorityButton.addActionListener(new TicketButtonListener(this.parent, "high priority"));

		this.mainPanel.add(this.ticketNumberLabel);
		this.mainPanel.add(this.otherTicketButton);
		this.mainPanel.add(this.highPriorityButton);

		this.add(this.mainPanel);

	}

	public void setLabel(String txt){
		this.ticketNumberLabel.setText(txt);
	}

	class TicketButtonListener implements ActionListener{
			Terminal parent;
			String ticketCategory;

			TicketButtonListener(Terminal parent, String ticketCat){
				this.parent = parent;
				this.ticketCategory = ticketCat;
			}

			public void actionPerformed(ActionEvent e){
				try{
					this.parent.getTicket(this.ticketCategory);
				}catch(Exception exp){
					System.err.println(exp.getMessage());
				}
			}
	}
}
