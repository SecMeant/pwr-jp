package lab06;

import java.util.Vector;
import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import java.awt.Dimension;

class Event{}

class MessageQueue extends Vector<String>{
	public void push(String val){
		this.add(val);
	}

	public String pop(){
		String ret = this.get(0);
		this.remove(0);
		return ret;
	}

}

class MessageManager extends JPanel{
	
	public static final int MESSAGE_DURATION = 1000;

	private MessageQueue messageQ = new MessageQueue();
	private Event updateEvent = new Event();
	private JLabel label = new JLabel();
	private MessageDisplayer messageDisplayer = new MessageDisplayer(this);

	public MessageManager(){
		this.add(this.label);
	}

	public void addMessage(String message){
		this.label.setForeground(Color.black);
		this.messageQ.push(message);
		synchronized(this.updateEvent){
			this.updateEvent.notifyAll();
		}
	}

	public void addMessageForced(String message){
		this.label.setForeground(Color.black);
		this.setMessage(message);
	}

	public void addMessageError(String message){
		this.label.setForeground(Color.red);
		this.setMessage(message);
	}

	public void addMessageSuccess(String message){
		this.label.setForeground(Color.blue);
		this.setMessage(message);
	}

	private void setMessage(String message){
		this.label.setText(message);
		this.label.setHorizontalAlignment(JLabel.CENTER);
	}

	public JLabel getLabel(){
		return this.label;
	}

	private class MessageDisplayer extends Thread{
		private MessageManager parent;

		MessageDisplayer(MessageManager parent){
			this.parent = parent;
			this.setDaemon(true);
			this.start();
		}

		public void run(){
			for(;;){

				// If message q is empty, wait for update event
				try{
					if(this.parent.messageQ.size() == 0){
						synchronized(this.parent.updateEvent){
							this.parent.updateEvent.wait();
						}
					}
				}catch(InterruptedException e){
					System.err.println(e.getMessage());
				}

				this.parent.label.setText(this.parent.messageQ.pop());

				// Wait some time until clearing message from screen / overriding with other
				try{
					TimeUnit.MILLISECONDS.sleep(MessageManager.MESSAGE_DURATION);
				}catch(InterruptedException e){
					System.err.println(e.getMessage());
				}
			}
		}
	}
}
