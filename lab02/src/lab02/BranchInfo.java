package lab02;

import java.util.ArrayList;
import java.util.stream.IntStream;

class BranchInfo
{
	public boolean[] swapInfo;
	public ArrayList<Integer> currentState;
	public Integer root;
	
	private BranchInfo()
	{}
	
	public BranchInfo(Integer peopleCount, Integer root)
	{
		this.swapInfo = new boolean[peopleCount];
		this.currentState = new ArrayList<>();
		IntStream.range(0, peopleCount).forEach(id->{
			this.currentState.add(id);
		});
		this.root = root;
	}
	
	public static BranchInfo copy(BranchInfo bi)
	{
		BranchInfo ret = new BranchInfo();
		
		// Copy swapInfo
		ret.swapInfo = new boolean[bi.swapInfo.length];
		for(int i = 0; i < bi.swapInfo.length; i++) ret.swapInfo[i] = bi.swapInfo[i];
		
		//Copy current state
		ret.currentState = new ArrayList<Integer>();
		for(int i = 0; i < bi.currentState.size(); i++) ret.currentState.add(bi.currentState.get(i));
		
		//Copy root
		ret.root = bi.root;
		
		return ret;
	}
	
	public BranchInfo copy()
	{
		return copy(this);
	}
}