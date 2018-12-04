package lab05;

import java.util.concurrent.TimeUnit;

class Utils{
	
	static void sleep(int sleepTimems){
		try{
			TimeUnit.MILLISECONDS.sleep(sleepTimems);
		}catch(InterruptedException e){
			System.out.println("Interrupted exception thrown.");
		}
	}
}
