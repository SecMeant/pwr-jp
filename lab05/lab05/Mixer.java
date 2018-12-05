package lab05;

import java.util.Vector;

interface TestInterface{
	public int getSpiceStateById(int spiceID);
}

class Mixer implements TestInterface, SpiceManager{
	
	public static final int SPICES_COUNT = 10;
	public static final int COOK_COUNT = 4;

	// Blocking / non-blocking call flags
	public static final boolean WAIT = true;
	public static final boolean DONT_WAIT = false;

	public static boolean MAKE_ORDER_ON_FULFILL_ERROR = false; 

	public static final int SPICE_MAX_STATE = 20;

	// Flags for indicating order status
	public static final int ORDER_STATE_FREE = 0;
	public static final int ORDER_STATE_PREPARE = 1;
	public static final int ORDER_STATE_FILLING = 2;

	// When state of any spices pass it value, mixer requests filling it from supplier
	public static final int SPICE_LOW_STATE = SPICE_MAX_STATE / 3;
	
	// Each index describes state of each mix
	private int[] spices = new int[SPICES_COUNT];


	Cook[] cooks = new Cook[COOK_COUNT];

	// Spices supplier
	public Supplier supplier = new Supplier(this);

	private OrderManager orderManager = new OrderManager(this, this.supplier);
	
	{
		for(int i = 0; i < Mixer.COOK_COUNT; i++){
			Cook cook = new Cook(this);
			cook.setDaemon(true);
			cook.start();
			this.cooks[i] = cook;
		}

		this.orderManager.setDaemon(true);
		this.orderManager.start();
	}

	public void getMix(Recipe r, boolean waitIfNotReady) throws Unfulfillable{
		synchronized (this.spices){
			if(!this.isRequestFulfillable(r)){
				
				// If blocking mode on, wait for notify
				while(waitIfNotReady){
					try{
						if(Mixer.MAKE_ORDER_ON_FULFILL_ERROR)
							this.registerUnfulfilledRequest(r);

						// Wait for fill
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
		}
	}

	public void fillSpices(int[] spices){
			synchronized(this.spices){
			this.orderManager.orderState = ORDER_STATE_FILLING;
			for(int i = 0; i < this.spices.length; i++){
				this.spices[i] += spices[i];
			}

			// Hard work, filling spices
			Utils.sleep(1000);

			this.spices.notifyAll();
			this.orderManager.orderState = ORDER_STATE_FREE;
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

		// Fake hard work
		Utils.sleep(600);

	}

	public void checkSpiceStateAndOrder(){
	}

	private void registerUnfulfilledRequest(Recipe recipe){
		this.supplier.makeOrder(new Order(recipe.spices));
	}

	/* TEST INTERFACE */
	public int getSpiceStateById(int spiceID){
		return this.spices[spiceID];
	}

	public void printSpices(){
		Utils.printTab(this.spices);
	}

	public int getCurrentOrderState(){
		return this.orderManager.orderState;
	}

	private class OrderManager extends Thread{
		private Supplier supplier;
		private Mixer parent;
		private boolean killFlag = false;

		public int orderState = ORDER_STATE_FREE;

		OrderManager(Mixer parent, Supplier supp){
			this.parent = parent;
			this.supplier = supp;
		}

		public void kill(){
			this.killFlag = true;
		}
		
		@Override
		public void run(){
			int[] toOrder = new int[Mixer.SPICES_COUNT];
			boolean isAnyToOrder;

			while(!killFlag){
				
				isAnyToOrder = false;

				for( int i = 0; i < Mixer.SPICES_COUNT; i++ ){
					
					if(this.parent.spices[i] < Mixer.SPICE_LOW_STATE){
						toOrder[i] = Mixer.SPICE_MAX_STATE - this.parent.getSpiceStateById(i);
						isAnyToOrder = true;
					}

					else
						toOrder[i] = 0;
				}

				if(!isAnyToOrder)
					continue;

				//this.supplier.makeOrder(new Order(toOrder));
				try{
				// Wait for order to end
				this.orderState = ORDER_STATE_PREPARE;
				this.supplier.makeOrder(new Order(toOrder)).join();
				}catch(InterruptedException e){
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
