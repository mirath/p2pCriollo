
import java.io.IOException;
import java.net.*;

public class ServerRequest {
    private final byte DOWNLOAD_HEXCODE  = 0x0;
    private final byte CONSULT_HEXCODE   = 0x2;
    private final byte REACHABLE_HEXCODE = 0x3;
    private final byte NULL_HEXCODE      = 0x4;
    private final int  NULL_HASHID       = 0xffffffff;
    private Socket client_socket;
    private String operation;
    private String download_path;
    P2pRequest req;
    byte[] data;
    
    public ServerRequest(Socket cs, String operation, String data){
	client_socket = cs;
        this.operation = operation;
        req = null;
        this.data = data.getBytes();
    }

    public ServerRequest(Socket cs, String operation, String data, String download_path){
	client_socket = cs;
        this.operation = operation;
        req = null;
        this.data = data.getBytes();
	this.download_path = download_path;
    }

    public void run(){
        P2pProtocolHandler h = new P2pProtocolHandler();
        if (operation.compareTo("download") == 0){
            // Contruir request
            req = new P2pRequest(DOWNLOAD_HEXCODE,NULL_HASHID,data);
	    String songname = new String(req.data);
	    System.out.println("Descargando "+songname+"...");
            h.requestSong(req, download_path, client_socket);
	    System.out.println("Cancion "+songname+" descargada");
        }
        else if (operation.compareTo("consult") == 0) {
            // Construir request
            req = new P2pRequest(CONSULT_HEXCODE,NULL_HASHID,data);
            h.requestConsult(req, client_socket);
        }
        else if (operation.compareTo("reachable") == 0) {
            // Construir request
            req = new P2pRequest(REACHABLE_HEXCODE,NULL_HASHID,null);
            h.requestReachable(req, client_socket);
        }
        try {
            client_socket.close();
        }
        catch(IOException e) {
            System.out.println("No se pudo cerrar el socket: "+e);
        }
	return;
    }
}