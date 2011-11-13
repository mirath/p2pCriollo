import java.util.concurrent.*;
import java.io.*;
import java.net.*;

public class P2pProtocolHandler{
    private static byte DOWNLOAD_HEXCODE  = 0x0;
    private static byte CONSULT_HEXCODE   = 0x1;
    private static byte REACHABLE_HEXCODE = 0x2;
    private static byte NULL_HEXCODE      = 0x4;
    private static int  NULL_HASHID       = 0xffffffff;

    private Socket client_socket;
    private BufferedReader in = null;
    private PrintWriter out = null;

    public P2pProtocolHandler(Socket cs){
	client_socket = cs;
	try{
	    in = new BufferedReader(new InputStreamReader( client_socket.getInputStream()));
	    out = new PrintWriter(client_socket.getOutputStream(), true);
	}
	catch(IOException e){}
    }

    public P2pRequest getRequest(){
	int header = 0;
	int op_code = 0;
	int hash_id = 0;
	int data_size = 0;
	try{
	    //Se lee el header
	    header = in.read();
	    header = header << 16;
	    header += in.read();
	    op_code = header ^ 0xc0000000;
	    hash_id = header ^ 0x3fffffff;

	    data_size = in.read();
	    data_size = data_size <<16;
	    data_size += in.read();
	}
	catch(IOException e){}

	return new P2pRequest(null,op_code,hash_id);
    }

    public void makeConsult(ConcurrentMap<String,String> SongDB,
			    ConcurrentMap<Integer,P2pRequest> ConsultDB,
			    P2pRequest req){
	
    }

    public void makeReachable(ConcurrentMap<String,String> NodeDB,
			      ConcurrentMap<Integer,P2pRequest> ConsultDB,
			      P2pRequest req){}

    public void sendSong(ConcurrentMap<String,String> SongDB,P2pRequest req){}
}