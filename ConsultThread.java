
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ConsultThread extends Thread {
    int pos;
    String[] result;
    InetAddress ip;
    P2pRequest req;
    int port;
    P2pProtocolHandler p2pHandler;
    
   public ConsultThread(int i, String[] respuesta, InetAddress ip, 
           P2pRequest req, int port, P2pProtocolHandler pHandler) {
        pos = i;
        result = respuesta;
        this.ip = ip;
        this.req = req;
        this.port = port; 
        this.p2pHandler = pHandler;
    }
   
    @Override
   public void run() {
       // Crear conexión con el servidor vecino
        try { 
            Socket sok = new Socket(this.ip, this.port);
            result[pos] = p2pHandler.requestConsult(req, sok);
            sok.close();
        }
        catch(IOException e) {
            result[pos] = "";
        }
    }
}
