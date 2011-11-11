import java.util.concurrent.*;
import java.net.*;
import java.io.*;

public class Node {
    private static ConcurrentHashMap<String,String> SongDB;
    private static ConcurrentHashMap<String,String> NodeDB;
    private static ConcurrentHashMap<Integer,P2pRequest> ConsultDB;

    final static int SERVER_PORT = 55949;

    public static void main(String[] args){
	//ServerSocket node_socket
	try{
	  ServerSocket node_socket = new ServerSocket(SERVER_PORT);

	  Socket client_socket;

	  //Main loop of the node server
	  while(true){
	      client_socket = node_socket.accept();
	      new ClientRequestThread(client_socket,SongDB,NodeDB,ConsultDB).start();
	  }
	}
	catch(IOException e){}
	
	// try{
	//     node_socket.close();
	// }
	// catch(IOException e){}
    }   
}