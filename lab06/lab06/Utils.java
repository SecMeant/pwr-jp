package lab06;

class Utils{
	public static byte[] serializeTask(Task t){
		return (t.operation + t.args).getBytes();	
	}
}
