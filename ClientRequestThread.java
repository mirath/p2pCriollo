import java.net.*;
import java.io.*;

public class ClientRequestThread extends Thread{
    private final byte DOWNLOAD_HEXCODE  = 0x0;
    private final byte CONSULT_HEXCODE   = 0x2;
    private final byte REACHABLE_HEXCODE = 0x3;
    private final byte NULL_HEXCODE      = 0x4;
    private final int  NULL_HASHID       = 0xffffffff;
    private Socket client_socket;
    private P2pProtocolHandler p2pHandler;

    public ClientRequestThread(Socket cs, P2pProtocolHandler p2ph){
	client_socket = cs;
        p2pHandler = p2ph;
    }
    
    @Override
    public void run(){
        P2pRequest request = p2pHandler.getRequest(client_socket);
        
        switch(request.op_code){
            case DOWNLOAD_HEXCODE:
                p2pHandler.sendSong(request, client_socket);
                break;
            case CONSULT_HEXCODE:
                p2pHandler.makeConsult(request, client_socket);
                break;
            case REACHABLE_HEXCODE:
                p2pHandler.makeReachable(request, client_socket);
                break;
            case NULL_HASHID:
        }
        try{
	    client_socket.close();
	}
	catch(IOException e) {}
	return;
    }
}