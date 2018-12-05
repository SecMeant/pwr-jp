package lab05;

import javax.swing.SwingUtilities;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.lang.Thread;


class View{
	Mixer toWatch;

	JFrame mainFrame = new JFrame("Mixer");
	JPanel mainPanel = new JPanel();

	JLabel workerCountLabel = new JLabel("Workers: ");
	JLabel[] spicesState = new JLabel[Mixer.SPICES_COUNT];

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
		panel_tmp.add(this.workerCountLabel);

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
			String state;

			if(this.toWatch.cooks[x].getState() == Thread.State.WAITING)
				state = String.valueOf("Waiting");
			else
				state = String.valueOf("Working");
				
			c.gridx = x;
			panel_tmp.add(new JLabel(state),c);
		}
		c.gridx = 0;
		this.mainPanel.add(panel_tmp, c);
	}

	public void updateViewState(){
		for( int i = 0; i < this.spicesState.length; i++){
			this.spicesState[i].setText(String.valueOf(this.toWatch.getSpiceStateById(i)));
		}

		this.mainFrame.pack();
	}
}

