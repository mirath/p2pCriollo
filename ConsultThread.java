
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * Se utiliza para hacer consultas en modo broadcast. 
 * El nodo que recibe una consulta, itera sobre la lista 
 * de nodos conocidos. En vez de tener que esperar por cada 
 * nodo vecino a que mande su respuesta, la clase ConsultThread
 * permite crear concurrentemente todas las conexiones a todos 
 * los nodos vecinos.
 */
public class ConsultThread extends Thread {
    int pos;
    String[] result;
    InetAddress ip;
    P2pRequest req;
    int port;
    P2pProtocolHandler p2pHandler;
    
    /**
     *
     * @param i posición del arreglo respuesta que le pertenece a este hilo. 
     * @param respuesta arreglo de respuesta. El hilo escribirá su respuesta 
     * en la casilla que le corresponda.
     * @param ip dirección IP para establecer la conexión con el nodo vecino.
     * @param req petición. Contiene la información relevante sobre la consulta:
     * código de operación, hash identificador, etc.
     * @param port puerto al cual se debe conectar el nodo.
     * @param pHandler maneja la conexión con el nodo vecino.
     */
    public ConsultThread(int i, String[] respuesta, InetAddress ip,
			 P2pRequest req, int port, P2pProtocolHandler pHandler) {
        pos = i;
        result = respuesta;
        this.ip = ip;
        this.req = req;
        this.port = port; 
        this.p2pHandler = pHandler;
    }
   
   /**
    * Abre y cierra la conexión con el nodo vecino.
    */
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
