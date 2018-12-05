package lab05;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class Cook extends Thread{
	private static int globalID = 0;
	private int id;
	private SpiceManager spiceManager;
	private boolean isHired = false;

	Cook(SpiceManager sm){
		this.spiceManager = sm;
		synchronized (this){
			this.id = this.globalID;
			this.globalID++;
		}
		this.isHired = true;
	}

	public void fire(){
		this.isHired = false;
	}

	public void run(){
		while(this.isHired){
			this.cook();
			this.aquireRandomIngredients();
		}
	}

	private void cook(){
		try{
			int randomValue = 1337;
			TimeUnit.MILLISECONDS.sleep(randomValue);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}

	private void aquireRandomIngredients(){
		Recipe r = new Recipe(new int[]{2,6,4,0,2});
	
		try{
			this.spiceManager.getMix(r, Mixer.WAIT);
		}catch(Unfulfillable e){
			System.out.println(e.getMessage());
		}
	}


}
