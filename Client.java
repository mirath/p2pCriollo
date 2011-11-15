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
	BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
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
		client_socket = bind_to_server(node);

		String[] resto = command.split("\\s");
	        // Preparar petición al Nodo
	        client_socket = bind_to_server(node);
	        // Preparar cadena
	        String expr = parseSearchEntry(resto);
	   	// Búsqueda por autor ?
		if (resto[0].compareTo("-a") == 0) {
			ServerRequest srv = new ServerRequest(client_socket,
							      "consult", "A"+expr);
		}
		// Búsqueda por título
		else {
			ServerRequest srv = new ServerRequest(client_socket,
							      "consult", "T"+expr);
		}

		try{
		    client_socket.close();
		}
		catch(IOException e){
		    System.out.println("Error cerrando el socket del cliente");
		    System.exit(1);
		}

		close_socket(client_socket);
		break;
	    case 'A':
	    case 'a':
		break;
	    case 'D':
	    case 'd':
		client_socket = bind_to_server(node);

		svr = new ServerRequest(client_socket,"download","Moulin Rouge-Mireille Mathieu",download_path);
	        svr.run();

		try{
		    client_socket.close();
		}
		catch(IOException e){
		    System.out.println("Error cerrando el socket del cliente");
		    System.exit(1);
		}

		close_socket(client_socket);
		break;
	    case 'P':
	    case 'p':
		client_socket = bind_to_server(node);

		svr = new ServerRequest(client_socket,"download","Moulin Rouge-Mireille Mathieu",download_path);
	        svr.run();
		try{
		    Runtime.getRuntime().exec(new String[]{"vlc","Moulin Rouge-Mireille Mathieu.mp3"});
		}
		catch(IOException e){
		    System.out.println("I/O Error: "+e);
		}

		close_socket(client_socket);
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

    private static Socket bind_to_server(String node_addr){
	InetAddress addr;
	Socket s = null;

	System.out.println("Espere, realizando conexion al servidor...");

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

	System.out.println("Conexion realizada");
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