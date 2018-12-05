package lab05;

import java.util.concurrent.TimeUnit;

class Utils{
	
	public static final int TIME_MULTIPLIER = 10;

	static void sleep(int sleepTimems){
		try{
			TimeUnit.MILLISECONDS.sleep(sleepTimems * Utils.TIME_MULTIPLIER);
		}catch(InterruptedException e){
			System.out.println("Interrupted exception thrown.");
		}
	}

	static void printTab(int[] tab){
		for( int i = 0; i < tab.length; i++ ){
			System.out.print(tab[i] + " ");
		}
		System.out.println("");
	}
}
