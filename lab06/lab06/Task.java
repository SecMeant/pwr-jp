package lab06;

class Task{
	private int id;
	private String args;
	private String answer;

	Task(int id, String args){
		this.id = id;
		this.args = args;
	}

	Task(int id, String args, String answer){
		this(id, args);
		this.answer = answer;
	}

	public String serialize(){
		StringBuilder serialized = new StringBuilder();
		serialized.append(this.id);
		serialized.append("&");
		serialized.append(this.args);
		serialized.append("&");
		serialized.append(this.answer);

		return serialized.toString();
	}
}
