package lab06;

import java.io.IOException;

import java.io.OutputStream;
import java.io.DataOutputStream;

/* -!- PROTOCOL DETAILS
 * Server and client are communicating using these rules described below.
 * Server listens on port 1337 by default but this can be changed.
 * When connection is established whole process is called `session`.
 * After session begins client can server communicate using `messages`.
 * Each message contains header and data attached to it.
 * After starting session client can send any amount of messages to server,
 * however if any of messages are invalid in terms of protocol ( i.e bad header,
 * unaligned data, missing separator etc. ) server is allowed to shutdown connection
 * immediately and ignore rest of messages / data, therefore client should start new session.
 * After each request sent server will response with single int32 defined below as RES_OK
 * or RES_ERR
 * 
 * -!- PROTOCOL HEADERS
 * [ int32   | int32 ]
 * [REQ_TYPE | DATA_LENGTH]
 *
 * REQ_TYPE - request type
 * DATA_LENGTH - Length of whole data that will be send after header
 * 
 * -!- REQUEST TYPES
 * For REQ_TYPE == ADD_TASK
 *	data should be strings null-terminated
 *	First argument is operation (example '+', '-', 'mod' etc)
 *	Second argument are args for that operation in string format
 *	each arg should be ';' semicolon separated
 *
 *	If data really sent will be longer than one assured in header
 *	server can end session by shutting down connection.
 * */

class TaskProtocol{
	public static final int REQ_ADDTASK = 1;
	public static final int REQ_GETTASKLIST = 2;

	public static final int RES_OK = 1;
	public static final int RES_ERR = 2;

	public static final int HEADER_SIZE = 32 * 2;

	public static void sendAddTaskRequest(DataOutputStream out,
	                                      String op, String args)
	throws IOException{
		// Send header
		// Write request id
		out.writeInt(REQ_ADDTASK);

		// Write data size
		out.writeInt(
			op.length() +
			1 + // null byte, terminator
			args.length() +
			1 // null byte, terminator
			);

		// Send data
		// Write operation
		byte[] tmp = op.getBytes();
		out.write(tmp,0,tmp.length);

		// Null termination for operation string
		out.write(0);

		// Write args for operation
		tmp = args.getBytes();
		out.write(tmp, 0, tmp.length);

		// Null termination for args string
		out.write(0);
	}

	public static void sendGetTaskListRequest(OutputStream out_){

	}
}
