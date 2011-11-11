import java.util.concurrent.*;
import java.net.*;

public class P2pProtocolHandler{
    private static byte DOWNLOAD_HEXCODE  = 0x0;
    private static byte CONSULT_HEXCODE   = 0x1;
    private static byte REACHABLE_HEXCODE = 0x2;
    private static byte NULL_HEXCODE      = 0x4;
    private static int  NULL_HASHID       = 0xffffffff;

    private Socket client_socket;

    public P2pProtocolHandler(Socket cs){
	client_socket = cs;
    }

    public P2pRequest getRequest(){ return new P2pRequest(); }

    public void makeConsult(ConcurrentMap<String,String> SongDB,
			    ConcurrentMap<Integer,P2pRequest> ConsultDB,
			    P2pRequest req){}

    public void makeReachable(ConcurrentMap<String,String> NodeDB,
			      ConcurrentMap<Integer,P2pRequest> ConsultDB,
			      P2pRequest req){}

    public void sendSong(ConcurrentMap<String,String> SongDB,
			 P2pRequest req){
	
    }
}