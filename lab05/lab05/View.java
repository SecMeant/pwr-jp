package lab05;

import javax.swing.SwingUtilities;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;


class View{
	Mixer toWatch;

	JFrame mainFrame = new JFrame("Cos tam");
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

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		this.mainPanel.add(this.workerCountLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 1;
		c.insets = new Insets(0,0,5,5);

		for ( int x = 0; x < this.spicesState.length; x++ ){
			this.spicesState[x] = new JLabel(String.valueOf(this.toWatch.getSpiceStateById(x)));
			c.gridx = x;
			this.mainPanel.add(this.spicesState[x], c);
		}
	}

	public void updateViewState(){
		for( int i = 0; i < this.spicesState.length; i++){
			this.spicesState[i].setText(String.valueOf(this.toWatch.getSpiceStateById(i)));
		}

		this.mainFrame.repaint();
	}
}

