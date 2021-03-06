package lab07;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

class CategoryLabel extends JPanel{
	public String category;
	public JLabel categoryLabel = new JLabel();
	public JLabel tickets = new JLabel();

	CategoryLabel(String catName){
		this.category = catName;
		this.categoryLabel.setText(catName + " queue: ");

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		this.add(categoryLabel);
		this.add(tickets);
	}

	public void setTicketNumbers(int[] t){
		String tickets = new String();

		if(t.length > 0)
			tickets += String.valueOf(t[0]);

		for( int i = 1; i < t.length; i++){
			tickets += ", " + String.valueOf(t[i]);
		}

		this.tickets.setText(tickets);
	}
}

public class MonitorGui extends JFrame{
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 400;

	private Monitor parent;

	private JPanel mainPanel = new JPanel();
	private ArrayList<CategoryLabel> categoryLabels = new ArrayList<>();

	public MonitorGui(Monitor parent){
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
		this.setSize(MonitorGui.WINDOW_WIDTH, MonitorGui.WINDOW_HEIGHT);
		this.setVisible(true);
	}

	private void initWindow(){
		this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
		
		for(String cat : Central.ticketCategories){
			this.categoryLabels.add(new CategoryLabel(cat));
		}

		this.categoryLabels.forEach(this.mainPanel::add);

		this.add(this.mainPanel);

	}

	public void updateCategoryTickets(String category, int[] tickets){
		for(CategoryLabel cl : this.categoryLabels){
			if(cl.category.equals(category)){
				cl.setTicketNumbers(tickets);
				return;
			}
		}
	}
}
