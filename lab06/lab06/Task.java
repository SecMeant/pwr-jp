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
	
	public byte[] serialize(){
		return (this.operation + "\0" + this.args + "\0" + this.result + "\0").getBytes();
	}

	public int getSerializedSize(){
		// Length of all string and 3 null bytes
		return this.operation.length() + this.args.length() + this.result.length() + 3;
	}

	public void setResult(String result){
		this.result = result;
	}

	public String toString(){
		return "Operation: " + this.operation + ", Args: " + this.args + ", Result: " + this.result;
	}
}
