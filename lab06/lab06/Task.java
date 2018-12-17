package lab06;

class Task{
	public String operation;
	public String args;
	public String result;

	Task(String operation, String args){
		this.operation = operation;
		this.args = args;
	}

	Task(String operation, String args, String result){
		this(operation, args);
		this.result = result;
	}

	@Override
	public boolean equals(Object other){
		if(other == null) return false;
		if(other == this) return false;
		if(!(other instanceof Task)) return false;

		Task other_t = (Task) other;

		return this.operation.equals(other_t.operation) &&
		       this.args.equals(other_t.args);
	}

	public void setResult(String result){
		this.result = result;
	}
}
