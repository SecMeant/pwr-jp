package lab02;

import java.util.ArrayList;

class Solver
{
	private ArrayList<Person> people;
	private ArrayList<Table> tables;
	private ArrayList<Integer> startState;
	private Solution bestSolution;
	private boolean isset;
	
	private ArrayList<ArrayList<Integer>> swapConnections;
	
	Solver()
	{
		this.people = null;
		this.tables = null;
		this.startState = null;
		this.swapConnections = null;
		this.isset = false;
	}
	
	Solver(ArrayList<Person> people, ArrayList<Table> tables) throws Exception
	{
		this.setup(people, tables);
	}
	
	// Need to be called before solve is called
	public void setup(ArrayList<Person> people, ArrayList<Table> tables) throws Exception
	{
		if(people == null || tables == null)
		{
			isset = false;
			return;
		}
		
		this.people = people;
		this.tables = tables;
		this.startState = new ArrayList<Integer>();
		this.swapConnections = new ArrayList<ArrayList<Integer>>();
		this.bestSolution = new Solution(startState, 0);
		
		// Creates StartState of people with assigned tables like this:
		// |1|2|3|4|5|6| <--- People
		// |1|1|1|2|2|3| <--- tables
		// In later steps will be permutated to find best solution
		tables.forEach(table->{
			for(int i=0; i<table.size; i++)
			{
				if(this.startState.size() >= this.people.size())
					return;
				
				this.startState.add(table.number);
			}
		});
		if(this.startState.size() != this.people.size())
			throw new Exception("There is not enough space for people to sit!");
		
		// Generates swapConnections table
		for(int i=0; i<this.startState.size(); i++)
		{
			this.swapConnections.add(new ArrayList<Integer>());
			for(int j=i; j<this.startState.size(); j++)
			{
				if(this.startState.get(i) != this.startState.get(j))
				{
					this.swapConnections.get(i).add(j);
				}
			}
		}
	
		this.isset = true;
	}
	
	public Solution solve() throws Exception
	{
		if(this.isset == false)
			throw new Exception("You need to correctly setup Solver by calling setup(...) before calling solve!");
		
		BranchInfo bi = new BranchInfo(this.people.size(), this.tables, this.startState, 0);
		branch(bi);
		
		return this.bestSolution;
	}
	
	private boolean shouldBreak(boolean[] bitfield)
	{
		int falses = 0;
		
		for(boolean field:bitfield)
		{
			if(field == false)
			{
				if(falses == 1) return false;
				falses++;
			}
		}
		
		return true;
	}
	
	private boolean canSwap(boolean[] infoTable, Integer root, Integer possibility)
	{
		if(infoTable[root] == true)
			return false;
	
		if(infoTable[possibility] == true)
			return false;
		
		return true;
	}
	
	private boolean hasFriend(Integer person, Integer possibleFriend)
	{
		for(Person friend : this.people.get(person).friends)
		{
			if(possibleFriend == friend.number) return true;
		}
	
		return false;
	}
	
	private int calculate(BranchInfo bi)
	{
		int sum = 0;
		
		for(int id=0; id<bi.currentState.size(); id++)
		{
			for(int jd=0; jd<bi.currentState.size(); jd++)
			{
				if(id == jd) continue;
				
				// If two people share same table and id person likes jd person
				if(bi.currentState.get(id) == bi.currentState.get(jd) && hasFriend(id, jd))
				{
					sum++;
				}
			}
		}
		
		
		return sum;
	}
	
	private void branch(BranchInfo bi)
	{
		if(this.shouldBreak(bi.swapInfo)) return;
		
		int currentRating = this.calculate(bi);
		
		if(currentRating > this.bestSolution.points)
		{
			this.bestSolution.seatInfo = bi.currentState;
			this.bestSolution.points = currentRating;
		}
		
		for(int id=bi.root+1; id<this.swapConnections.size(); id++)
		{
			/* Cannot be done that way, cannot capture id
			this.swapConnections.get(id).forEach(swapPossibility->{
				if(canSwap(bi.swapInfo, id, swapPossibility))
				{
					
				}
			});
			*/
			
			for(Integer swapPossibility : this.swapConnections.get(id))
			{
				if(canSwap(bi.swapInfo, id, swapPossibility))
				{
					BranchInfo nbi = bi.copy();
					
					// Set info about this swap
					nbi.swapInfo[id] = true;
					nbi.swapInfo[swapPossibility] = true;
					
					// Change to new root if any
					nbi.root = id;
					
					// Update current state
					Integer tmp = nbi.currentState.get(id);
					nbi.currentState.set(id, nbi.currentState.get(swapPossibility));
					nbi.currentState.set(swapPossibility, tmp);
					branch(nbi);
				}
			}
		}
	}
}
