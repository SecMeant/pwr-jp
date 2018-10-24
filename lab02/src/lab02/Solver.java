package lab02;

import java.util.ArrayList;

class Solver
{
	// Data structures of people and tables
	private ArrayList<Person> people;
	private ArrayList<Table> tables;
	
	// Used to store best solution from branches
	// If recurring branch find better solution than current it changes it
	private Solution bestSolution;
	
	// Evaluator that is called to decide if given connection 
	// should earn point when given combination is evaluated
	private EvaluatorInterface evaluator;
	
	// Starting state of branching algorithm
	private ArrayList<Integer> startState;
	
	// Structure that describes possible swap connections between people
	// There is no need to swap people around same table
	private ArrayList<ArrayList<Integer>> swapConnections;
	
	// Flag that indicates if solver is properly configured
	private boolean isset;
	
	Solver()
	{
		this.people = null;
		this.tables = null;
		this.startState = null;
		this.swapConnections = null;
		this.isset = false;
	}
	
	Solver(ArrayList<Person> people, ArrayList<Table> tables, EvaluatorInterface evaluatorFunction)
	{
		this.setup(people, tables, evaluatorFunction);
	}
	
	// Need to be called before solve is called
	public void setup(ArrayList<Person> people, ArrayList<Table> tables, EvaluatorInterface evaluatorFunction)
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
		this.evaluator = evaluatorFunction;
		this.bestSolution = new Solution(startState, -1337);
		
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
			throw new Error("There is not enough space for people to sit!");
		
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
	
	public Solution solve()
	{
		if(this.isset == false)
			throw new Error("You need to correctly setup Solver by calling setup(...) before calling solve!");
		
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
	
	private int calculate(BranchInfo bi)
	{
		return this.evaluator.evaluate(bi, this.people);
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
	
	@FunctionalInterface
    public static interface EvaluatorInterface
    {
        int evaluate(BranchInfo bi, ArrayList<Person> people);
    }
	
	public static int evaluatorMostHappy(BranchInfo bi, ArrayList<Person> people)
	{
		int sum = 0;
		
		for(int id=0; id<bi.currentState.size(); id++)
		{
			for(int jd=0; jd<bi.currentState.size(); jd++)
			{
				if(id == jd) continue;
				
				// If two people share same table and id person likes jd person
				if(bi.currentState.get(id) == bi.currentState.get(jd) && hasFriend(people, id, jd))
					sum++;
			}
		}
		
		return sum;
	}
	
	public static int evaluatorLessUnhappy(BranchInfo bi, ArrayList<Person> people)
	{
		int sum = 0;
		
		for(int id=0; id<bi.currentState.size(); id++)
		{
			for(int jd=0; jd<bi.currentState.size(); jd++)
			{
				if(id == jd) continue;
				
				// If two people share same table and id person likes jd person
				if(bi.currentState.get(id) == bi.currentState.get(jd) && notFriends(people, id, jd))
					sum--;
			}
		}
		
		return sum;
	}
	
	private static boolean hasFriend(ArrayList<Person> people, Integer person, Integer possibleFriend)
	{
		for(Person friend : people.get(person).friends)
		{
			if(possibleFriend == friend.number) return true;
		}
	
		return false;
	}
	
	private static boolean notFriends(ArrayList<Person> people, Integer person, Integer possibleUnknown)
	{
		for(Person friend : people.get(person).friends)
		{
			if(possibleUnknown == friend.number) return false;
		}
	
		return true;
	}
}
