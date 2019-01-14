package lab07;

import java.io.Serializable;

public class Info implements Serializable{
	public String categoryName;
	public int[] queue;

	@Override
	public String toString(){
		String ret = this.categoryName;

		for(int i : this.queue){
			ret += " " + String.valueOf(i);
		}
		return ret;
	}
}


