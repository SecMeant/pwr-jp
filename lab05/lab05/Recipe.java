package lab05;

import java.lang.Math;

class Recipe{
	public int[] spices = new int[Mixer.SPICES_COUNT];
	
	Recipe(int[] spices){
		for(int i = 0; i < Math.min(this.spices.length, spices.length); i++){
			this.spices[i] = spices[i];
		}
	}

	// I know its not optimal but java is horrible
	public String toString(){
		String ret = "";

		for(int i = 0; i < this.spices.length; i++){
			ret += " " + this.spices[i];
		}

		return ret;
	}
}
