package lab05;

import javax.swing.SwingUtilities;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.lang.Thread;


class View{
	Mixer toWatch;

	JFrame mainFrame = new JFrame("Mixer");
	JPanel mainPanel = new JPanel();

	JLabel supplierLabel = new JLabel("Free");
	JLabel[] spicesState = new JLabel[Mixer.SPICES_COUNT];
	JLabel[] cooksState = new JLabel[Mixer.COOK_COUNT];

	public View(Mixer toWatch){
		this.toWatch = toWatch;

		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowGui();
			}
		});
	}

	public void createAndShowGui(){
		this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainFrame.add(this.mainPanel);
		this.updateView();
		this.mainFrame.pack();
		this.mainFrame.setVisible(true);
		this.mainFrame.setLocationRelativeTo(null);
	}

	public void updateView(){
		this.mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Workers count panel setup
		JPanel panel_tmp = new JPanel();
		this.supplierLabel.setText("");
		panel_tmp.add(this.supplierLabel);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		this.mainPanel.add(panel_tmp, c);

		// Spices state panel setup
		panel_tmp = new JPanel();
		panel_tmp.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 1;
		c.insets = new Insets(0,0,5,5);

		for ( int x = 0; x < this.spicesState.length; x++ ){
			this.spicesState[x] = new JLabel(String.valueOf(this.toWatch.getSpiceStateById(x)));
			c.gridx = x;
			panel_tmp.add(this.spicesState[x],c);
		}
		c.gridx = 0;
		this.mainPanel.add(panel_tmp, c);


		// Cook state panel setup
		panel_tmp = new JPanel();
		panel_tmp.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 2;
		c.insets = new Insets(0,0,5,5);

		for ( int x = 0; x < this.toWatch.cooks.length; x++ ){

			if(this.toWatch.cooks[x].getState() == Thread.State.WAITING)
				this.cooksState[x] = new JLabel("Waiting");
			else
				this.cooksState[x] = new JLabel("Working");
			
			c.gridx = x;
			panel_tmp.add(this.cooksState[x],c);
		}
		c.gridx = 0;
		this.mainPanel.add(panel_tmp, c);
	}

	public void updateViewState(){
		for( int i = 0; i < this.spicesState.length; i++){
			this.spicesState[i].setText(String.valueOf(this.toWatch.getSpiceStateById(i)));
		}

		for( int i = 0; i < this.cooksState.length; i++){
			if(this.toWatch.cooks[i].getState() == Thread.State.WAITING){
				this.cooksState[i].setText("Waiting");
				this.cooksState[i].setForeground(Color.YELLOW);
			}
			else{
				this.cooksState[i].setText("Working");
				this.cooksState[i].setForeground(Color.GREEN);
			}
		}

		this.setSupplierState(this.toWatch.getCurrentOrderState());

		this.mainFrame.pack();
	}

	private void setSupplierState(int state){
		switch(state){
			case Mixer.ORDER_STATE_FREE:
				this.supplierLabel.setText("Free");
				this.supplierLabel.setForeground(Color.GREEN);
				break;
			case Mixer.ORDER_STATE_PREPARE:
				this.supplierLabel.setText("Preparing");
				this.supplierLabel.setForeground(Color.ORANGE);
				break;
			case Mixer.ORDER_STATE_FILLING:
				this.supplierLabel.setText("Filling");
				this.supplierLabel.setForeground(Color.YELLOW);
				break;
		}
	}
}

