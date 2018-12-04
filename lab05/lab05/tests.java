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

		mixer.fillSpice(3,1337);
		mixer.fillSpice(2,3);
		mixer.fillSpice(6,123);

		test_assert(mixer.getSpiceStateById(3) == 1337, "Filling spice failed");
		test_assert(mixer.getSpiceStateById(2) == 3, "Filling spice failed");
		test_assert(mixer.getSpiceStateById(6) == 123, "Filling spice failed");

		mixer.fillAllSpices();

		Utils.sleep(Supplier_Worker.maxOrderTime);
		
		for( int i = 0; i < Mixer.SPICES_COUNT; i++ ){
			test_assert(mixer.getSpiceStateById(i) == Mixer.SPICE_MAX_STATE,
			            String.format("Filling spice failed %d != %d", i, Mixer.SPICE_MAX_STATE));
		}
	}
}
