
import java.io.IOException;
import java.net.*;
import java.util.Random;

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
    
    public ServerRequest(Socket cs, int nodePort, String node, String operation,
            String data, String download_path){
        client_socket = bind_to_server(node, nodePort);
        this.operation = operation;
        req = null;
        this.data = data.getBytes();
        this.download_path = download_path;
    }
    
    public String run(){
        String ans = null;
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
            Random gen = new Random();
            String hash = Integer.toString(gen.nextInt()) +
                    client_socket.getInetAddress().getHostAddress() +
                    System.currentTimeMillis();
            req = new P2pRequest(CONSULT_HEXCODE,hash.hashCode(),data);
            ans = h.requestConsult(req, client_socket);
        }
        else if (operation.compareTo("reachable") == 0) {
            // Construir request
            Random gen = new Random();
            String hash = Integer.toString(gen.nextInt()) +
                    client_socket.getInetAddress().getHostAddress() +
                    System.currentTimeMillis();
            req = new P2pRequest(REACHABLE_HEXCODE,hash.hashCode(),null);
            ans = h.requestReachable(req, client_socket);
        }
        close_socket(client_socket);
        return ans;
    }
    
    private static Socket bind_to_server(String node_addr, int node_port){
        InetAddress addr;
        Socket s = null;
        
        try{
            addr = InetAddress.getByName(node_addr);
            s = new Socket(addr,node_port);
        }
        catch(UnknownHostException e){
            System.out.println("Host "+node_addr+" no encontrado");
            System.exit(1);
        }
        catch(SecurityException e){
            System.out.println("SecurityException");
            System.exit(1);
        }
        catch(IOException e){
            System.out.println("Error creando el socket");
            System.exit(1);
        }
        
        return s;
    }
    
    private static void close_socket(Socket client_socket){
        try{
            client_socket.close();
        }
        catch(IOException e){
            System.out.println("Error cerrando el socket del cliente");
            System.exit(1);
        }
    }
}