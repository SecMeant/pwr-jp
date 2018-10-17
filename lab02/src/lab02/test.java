import java.util.Vector;
import java.lang.Integer;

class A{
	int a;
	int b;
	A(){a=0; b=0;}
	A(int init){a=init; b=init+1;}
}

public class test{

	public static void f(int[] tab){
		tab[0] = 10;
	}

	public static void g(Vector<Integer> tab){
		tab.set(0, 10);
	}
	
	public static void main(String[] args){
		Vector<Integer> t = new Vector<>();
		int[] ta = new int[2];
	
		Integer n = 1;

		t.add(n);
		t.add(2);

		n = 10;

		ta[0] = 1;
		f(ta);

		for ( Integer it : t)
			System.out.println(it);

		for ( int it : ta)
			System.out.println(it);
	}
}
