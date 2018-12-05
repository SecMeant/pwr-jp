package lab05;

import java.util.Random;
import java.util.concurrent.TimeUnit;

class Order extends Recipe{
	Order(int[] args){
		super(args);
	}
}

class Supplier_Worker extends Thread{
	private static Random randomGenerator = new Random();
	
	public static final int minOrderTime = 200;
	public static final int maxOrderTime = 400;

	private boolean isHired = false;

	private Order order;
	private SpiceManager spiceManager;

	Supplier_Worker(SpiceManager sm, Order o){
		this.spiceManager = sm;
		this.order = o;
		this.isHired = true;
	}

	private void executeOrder(){
		int orderTime = Supplier_Worker.getRandomOrderTime();
		this.workHard(orderTime);
		this.spiceManager.fillSpices(this.order.spices);
	}

	private static int getRandomOrderTime(){
		int ret = Supplier_Worker.randomGenerator.nextInt(
			Supplier_Worker.maxOrderTime - Supplier_Worker.minOrderTime + 1 + minOrderTime);

		return ret;
	}

	static private void workHard(int workTime){
		Utils.sleep(workTime);
	}

	public void run(){
		this.executeOrder();
	}

	public void fire(){
		this.isHired = false;
	}
}

class Supplier{
	private SpiceManager spiceManager;

	Supplier(SpiceManager spiceManager){
		this.spiceManager = spiceManager;
	}

	public Supplier_Worker makeOrder(Order o){
		Supplier_Worker worker = new Supplier_Worker(this.spiceManager, o);
		worker.setDaemon(true);
		worker.start();
		return worker;
	}
}
