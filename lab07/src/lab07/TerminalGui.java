package lab07;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;

public class TerminalGui extends JFrame{
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 400;

	private Terminal parent;

	private JPanel mainPanel = new JPanel();
	private JLabel ticketNumberLabel = new JLabel("0");
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
		
		this.ticketNumberLabel.setAlignmentX(this.ticketNumberLabel.CENTER_ALIGNMENT);

		this.mainPanel.add(this.ticketNumberLabel);
		this.mainPanel.add(this.otherTicketButton);
		this.mainPanel.add(this.highPriorityButton);

		this.add(this.mainPanel);

	}

}
