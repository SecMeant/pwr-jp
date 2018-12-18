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
 * Very same thing works in server -> client direction when server sends
 * messages. Only difference is that name of types starts with ANS_ prefix
 * like ANS_TASKLIST
 * 
 * -!- REQUEST TYPES
 * == REQ_ADDTASK ==
 *	data should be strings null-terminated
 *	First argument is operation (example '+', '-', 'mod' etc)
 *	Second argument are args for that operation in string format
 *	each arg should be ';' semicolon separated
 *
 *	If data really sent will be longer than one assured in header
 *	server can end session by shutting down connection.
 *	
 * == REQ_GETTASKLIST ==
 *	after request no additional data should be sent.
 *
 *	In response server will send message with ANS_TASKLIST and data length
 *	in header. After header actual data will be send in form of operations, args and
 *	result being null byte separated and each arg will be semicolon separated and each
 *	whole task (op and args) will be two null bytes separated. Like so:
 *	OPERATION \0 ARG1;ARG2;ARG3 \0\0 OPERATION \0 ARG1;ARG2 \0 etc.
 * 
 * == REQ_RESULT ==
 *	Message that is used by client to send result of given task to the server.
 *	After req type data size should be sent indicating amount of data that client
 *	will send. After header client should send task object with operation, args, and result
 *	null separated and args semicolon separated. The amount of sent data should match
 *	one given in header. After sending results server will answer with RES_OK if successfully
 *	received and saved result.
 *
 * */

class TaskProtocol{
	// Client -> Server packets
	public static final int REQ_ADDTASK = 1;
	public static final int REQ_GETTASKLIST = 2;
	public static final int REQ_RESULT = 3;

	// Server -> Client packets
	public static final int RES_OK = 1;
	public static final int RES_ERR = 2;
	public static final int ANS_TASKLIST = 3;

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
}
