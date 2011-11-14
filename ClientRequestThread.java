import java.net.*;
import java.io.*;

public class ClientRequestThread extends Thread{
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
	case 1:
	    p2pHandler.makeConsult(request, client_socket);
	    break;
	case 2:
	    p2pHandler.makeReachable(request, client_socket);
	    break;
	case 3:
	case 4:
	    p2pHandler.sendSong(request, client_socket);
	    break;
	}
	try{
	    client_socket.close();
	}
	catch(IOException e) {}
	return;
    }
}