package lab05;

public interface SpiceManager{
	public void getMix(Recipe r, boolean waitIfNotReady) throws Unfulfillable;
	public void fillSpice(int spiceID, int spiceAmount);
}
