package lab05;

class Recipe{
	public int[] spices = new int[Mixer.SPICES_COUNT];
	
	Recipe(int[] spices){
		for(int i = 0; i < this.spices.length; i++){
			this.spices[i] = spices[i];
		}
	}
}
