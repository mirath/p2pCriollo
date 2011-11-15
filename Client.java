import java.net.*;
import java.io.*;
import java.util.*;

public class Client{

    private static Socket client_socket = null;
    private static int node_port = -1;
    private static String node = null;
    private static String download_path = null;
    private static List<Song> current_song_list;
    private static P2pProtocolHandler p2pHandler = new P2pProtocolHandler();


    public static void main(String args[]){
	BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));
	boolean running = true;

	set_params(args);
        
	System.out.println("Cliente listo para recibir o mandar ordenes");
	while(running){
	    String command = null;
	    ServerRequest svr = null;
	    try{
		command = console.readLine();
		command.trim();
	    }
	    catch(IOException e){
		System.exit(1);
	    }
	    
	    switch(command.charAt(0)){
	    case 'C':
	    case 'c':
		String[] resto = command.split("\\s");
                String ans = null;
	        // Preparar cadena
	        String expr = parseSearchEntry(resto);
	   	// Búsqueda por autor ?
		if (resto[0].compareTo("-a") == 0) {
			ServerRequest srv = new ServerRequest(client_socket, 
                                node_port, node, "consult", "A@@"+expr,
                                download_path);
		}
		// Búsqueda por título
                else if (resto[0].compareTo("-t") == 0) {
			ServerRequest srv = new ServerRequest(client_socket, 
                                node_port,node, "consult", "T@@"+expr, 
                                download_path); 
		}
                else {   // Búsqueda de todos los archivos
                    ServerRequest srv = new ServerRequest(client_socket, 
                                node_port,node, "consult", "W@@", 
                                download_path);
                    ans = srv.run();
                    // Parsear respuesta
                }
		break;
	    case 'A':
	    case 'a':
		break;
	    case 'D':
	    case 'd':

		// svr = new ServerRequest(client_socket, node_port, 
                //         node, "download", "one.mp3",download_path);
		svr = new ServerRequest
                        (client_socket,node_port,node,
                        "download","Moulin Rouge-Mireille Mathieu",
                        download_path);

	        svr.run();
		break;
	    case 'P':
	    case 'p':
	        svr = new ServerRequest
                        (client_socket,node_port,
                        node,"download",
                        "Moulin Rouge-Mireille Mathieu",download_path);
	        svr.run();
		try{
		    Runtime.getRuntime().exec(new String[]{"vlc",
                        "Moulin Rouge-Mireille Mathieu.mp3"});
		}
		catch(IOException e){
		    System.out.println("I/O Error: "+e);
		}
		break;
	    case 'Q':
	    case 'q':
		running = false;
	    break;
	    default:
		System.out.println("Comando invalido");
		break;
	    }
	}
    }
    
    private static String parseSearchEntry(String[] resto) {
        String expr = new String();
        for(int i = 1; i < resto.length; i++){
            expr += resto[i].toLowerCase();
            expr += " ";
        }
        return expr;
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
	    case 'n':
		node = args[i+1];
		break;
	    case 'd':
		download_path = args[i+1];
		break;
	    default:
		System.out.println("Opcion incorrecta");
		System.exit(1);
		break;
	    }
	    i += 2;
	}

	//Si no se especifico el puerto o el nodo al cual
	//conectarse salir con error
	if((node_port == -1)||(node == null)){
	    System.out.println("Uso: Cliente -p <puerto> -n <nodo> [-d <directorio de descargas>]");
	    System.exit(1);	    
	}
    }
}