package lab05;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class Cook extends Thread{
	private static int globalID = 0;
	private int id;
	private SpiceManager spiceManager;
	private boolean isHired = false;
	private Random randomGenerator = new Random();

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
		int[] toRecipe = new int[Mixer.SPICES_COUNT];

		for( int i = 0; i < Mixer.SPICES_COUNT; i++ ){
			toRecipe[i] = this.randomGenerator.nextInt(Mixer.SPICE_MAX_STATE/4);
		}

		Recipe r = new Recipe(toRecipe);
	
		try{
			this.spiceManager.getMix(r, Mixer.WAIT);
		}catch(Unfulfillable e){
			System.out.println(e.getMessage());
		}
	}


}
