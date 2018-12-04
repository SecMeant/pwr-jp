package lab05;

import java.util.Vector;

interface TestInterface{
	public int getSpiceStateById(int spiceID);
}

class Mixer implements TestInterface, SpiceManager{
	
	public static final int SPICES_COUNT = 10;

	// Blocking / non-blocking call flags
	public static final boolean WAIT = true;
	public static final boolean DONT_WAIT = false;

	public static final int SPICE_MAX_STATE = 10;

	// When state of any spices pass it value, mixer requests filling it from supplier
	public static final int SPICE_LOW_STATE = SPICE_MAX_STATE / 3;
	
	// Each index describes state of each mix
	private int[] spices = new int[SPICES_COUNT];

	Vector<Cook> cooks = new Vector<>();

	// Spices supplier
	public Supplier supplier = new Supplier(this);

	public void getMix(Recipe r, boolean waitIfNotReady) throws Unfulfillable{
		System.out.println("Requesting :" + r.toString());
		synchronized (this.spices){
			if(!this.isRequestFulfillable(r)){
				
				// If blocking mode on, wait for notify
				while(waitIfNotReady){
					try{
						// Wait for fill
						System.out.println("Wait");
						this.registerUnfulfilledRequest(r);
						this.spices.wait();
					}catch(InterruptedException e){
						e.printStackTrace();
					}

					// If request now fulfillable, take it
					if(this.isRequestFulfillable(r)){
						this.applyRequest(r);
						return;
					}
				}
		
				// If non blocking mode, throw exception
				throw new Unfulfillable("Given request is unfulfillable");
			}
			this.applyRequest(r);
		}
	}
	
	public void fillSpice(int spiceID, int spiceAmount){
		synchronized (this.spices){
			this.spices[spiceID] += spiceAmount;
			this.spices.notifyAll();
			System.out.println("Notifying all");
		}
	}

	public void fillSpices(int[] spices){
		synchronized(this.spices){
			for(int i = 0; i < this.spices.length; i++){
				this.spices[i] += spices[i];
			}
			this.spices.notifyAll();
		}
	}

	public void showState(){
		for(int i = 0; i < this.spices.length; i++){
			System.out.printf("%d : %d\n", i, this.spices[i]);
		}
		System.out.println("");
	}

	private boolean isRequestFulfillable(Recipe r){
		if(this.spices.length < r.spices.length)
			return false;

		for(int i = 0; i < this.spices.length; i++){
			if(r.spices[i] > this.spices[i])
				return false;
		}

		return true;
	}

	private void makeSpiceRequest(int spiceID){
		int[] spices = new int[Mixer.SPICES_COUNT];
		spices[spiceID] = Mixer.SPICE_MAX_STATE - this.spices[spiceID];
		Order order = new Order(spices);
		this.supplier.makeOrder(order);
	}

	public void fillAllSpices(){
		int[] spices = new int[Mixer.SPICES_COUNT];

		for( int i = 0; i < this.spices.length; i++ ){
			spices[i] = Mixer.SPICE_MAX_STATE - this.spices[i];
		}

		Order order = new Order(spices);
		this.supplier.makeOrder(order);
	}

	// Used to update state of 
	private void applyRequest(Recipe r){
		for(int i = 0; i < this.spices.length; i++){
			this.spices[i] -= r.spices[i];
		}

		checkSpiceStateAndOrder();
	}

	private void checkSpiceStateAndOrder(){
		int[] toOrder = new int[Mixer.SPICES_COUNT];
		for( int i = 0; i < Mixer.SPICES_COUNT; i++ ){
			
			if(this.spices[i] < SPICE_LOW_STATE)
				toOrder[i] = SPICE_MAX_STATE - this.spices[i];

			else
				toOrder[i] = 0;
		}

		this.supplier.makeOrder(new Order(toOrder));
	}

	private void registerUnfulfilledRequest(Recipe recipe){
		System.out.println("register unfulfilled");
		this.supplier.makeOrder(new Order(recipe.spices));
	}

	public void hireNewCook(){
		this.cooks.add(new Cook(this));
	}

	/* TEST INTERFACE */
	public int getSpiceStateById(int spiceID){
		return this.spices[spiceID];
	}

	public void printSpices(){
		for ( int i = 0; i < this.spices.length; i++ ){
			System.out.print(this.spices[i] + " ");
		}
		System.out.println("");
	}

}
