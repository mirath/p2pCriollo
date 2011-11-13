import java.util.concurrent.*;
import java.net.*;
import java.io.*;

public class ClientRequestThread extends Thread{

    private ConcurrentHashMap<String,String> SongDB;
    private ConcurrentHashMap<String,String> NodeDB;
    private ConcurrentHashMap<Integer,P2pRequest> ConsultDB;
    private Socket client_socket;

    public ClientRequestThread(Socket cs,
			       ConcurrentHashMap<String,String> SDB,
			       ConcurrentHashMap<String,String> NDB,
			       ConcurrentHashMap<Integer,P2pRequest> CDB){
	client_socket = cs;
	SongDB = SDB;
	NodeDB = NDB;
	ConsultDB = CDB;
    }

    public void run(){
	P2pProtocolHandler p2p_handler = new P2pProtocolHandler();
	P2pRequest request = p2p_handler.getRequest(client_socket);

	switch(request.op_code){
	case 1:
	    p2p_handler.makeConsult(SongDB,ConsultDB,request);
	    break;
	case 2:
	    p2p_handler.makeReachable(NodeDB,ConsultDB,request);
	    break;
	case 3:
	case 4:
	    p2p_handler.sendSong(SongDB,request);
	    break;
	}

	try{
	    client_socket.close();
	}
	catch(IOException e){}
	return;
    }
}