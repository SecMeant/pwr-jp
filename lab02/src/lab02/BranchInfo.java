package lab02;

import java.util.ArrayList;

class BranchInfo
{
	public boolean[] swapInfo;
	public ArrayList<SeatPair> currentState;
	public Integer root;
	
	private BranchInfo()
	{}
	
	public BranchInfo(Integer peopleCount, Integer root)
	{
		this.swapInfo = new boolean[peopleCount];
		this.currentState = new ArrayList<SeatPair>();
		this.root = root;
	}
	
	public static BranchInfo copy(BranchInfo bi)
	{
		BranchInfo ret = new BranchInfo();
		
		// Copy swapInfo
		ret.swapInfo = new boolean[bi.swapInfo.length];
		for(int i = 0; i < bi.swapInfo.length; i++) ret.swapInfo[i] = bi.swapInfo[i];
		
		//Copy current state
		ret.currentState = new ArrayList<SeatPair>();
		for(int i = 0; i < bi.currentState.size(); i++) ret.currentState.set(i, bi.currentState.get(i).copy());
		
		//Copy root
		ret.root = bi.root;
		
		return ret;
	}
	
	public BranchInfo copy()
	{
		return copy(this);
	}
}