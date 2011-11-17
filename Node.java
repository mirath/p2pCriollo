import java.net.*;
import java.io.*;

/**
 * Clase del servidor
 */
public class Node {
    private static int node_port;
    private static String node_id;
    private static String music_library_filepath;
    private static String known_nodes_filepath;
    //public static Thread mainThread;
    
    /**
     * 
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args){
        //mainThread = Thread.currentThread();
        
        //Parseo de parámetros
        set_params(args);
        
        try{
	    System.out.println("Servidor "+node_id+" estableciendo puerto de escucha");
            ServerSocket node_socket = new ServerSocket(node_port);
            Socket client_socket = null;
            
            // Crear P2pProtocolHandler genÃ©rico 
            P2pProtocolHandler genericHandler = 
                    new P2pProtocolHandler(known_nodes_filepath,
					   music_library_filepath, node_id);
            
	    System.out.println("Servidor "+node_id+" listo para recibir ordenes");
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
        catch(IOException e){
	    System.out.println("I/O Error: "+e);
	}
    }
    
    /**
     * Parsea los argumentos de la línea de comandos
     * @param args Argumentos de la línea de comandos
     */
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
