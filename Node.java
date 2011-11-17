import java.net.*;
import java.io.*;

/**
 * 
 * @author jorge
 */
public class Node {
    private static int node_port;
    private static String node_id;
    private static String music_library_filepath;
    private static String known_nodes_filepath;
    //public static Thread mainThread;
    
    /**
     *
     * @param args
     */
    public static void main(String[] args){
        //mainThread = Thread.currentThread();
        
        //Parseo de par�metros
        set_params(args);
        
        try{
	    System.out.println("Servidor "+node_id+" estableciendo puerto de escucha");//flag
            ServerSocket node_socket = new ServerSocket(node_port);
            Socket client_socket = null;
            
            // Crear P2pProtocolHandler genérico 
            P2pProtocolHandler genericHandler = 
                    new P2pProtocolHandler(known_nodes_filepath,
					   music_library_filepath, node_id);
            
	    System.out.println("Servidor "+node_id+" listo para recibir ordenes");//flag
            //Loop principal del servidor
            while(true){
                client_socket = node_socket.accept();
                new ClientRequestThread(client_socket, 
                        genericHandler).start();
            }
        }
        catch(FileNotFoundException fnf) {
            System.out.println("Error al abrir archivo "
                    +known_nodes_filepath+" :"+fnf);
        }
        catch(IOException e){}
    }
    
    private static void set_params(String args[]){
        char op = '\0';
        int i = 0;
        
        while( i < args.length ){
            op = args[i].charAt(1);
            switch(op){
                case 'p':
                    node_port = Integer.parseInt(args[i+1]);
                    break;

                case 'c':
                    known_nodes_filepath = args[i+1];
                    break;

                case 'b':
                    music_library_filepath = args[i+1];
                    break;

                case 'i':
                    node_id = args[i+1];
                    break;

                default:
                    System.out.println("Opcion incorrecta");
                    System.exit(1);
                    break;
            }
            i += 2;
        }
    }
}
