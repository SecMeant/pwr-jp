package lab05;

import java.util.concurrent.TimeUnit;

class TestNotPassed extends Error{
	TestNotPassed(String e){
		super(e);
	}
}

class Tests{

	static void runAllTests(){
		fillAndGetTest();

		System.out.println("Tests passed successfully");
	}

	static void test_assert(boolean a, String msg)throws TestNotPassed{
		if(a == false)
			throw new TestNotPassed(msg);
	}

	static void fillAndGetTest(){
		Mixer mixer = new Mixer();

		View view = new View(mixer);
		view.workerCountLabel.setText(String.format("Workers: %d",1));

		for(;;){
			Utils.sleep(50);
			view.updateViewState();
		}

	}
}
