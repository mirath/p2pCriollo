import java.util.concurrent.*;
import java.net.*;
import java.io.*;

public class Client{

    private static Socket client_socket = null;
    private static int node_port = -1;
    private static String node = null;
    private static String download_filepath = null;

    public static void main(String args[]){
	P2pProtocolHandler p2p_handler = new P2pProtocolHandler();
	BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	boolean running = true;

	System.out.println("Espere, realizando conexion al servidor...");

	set_params(args);

	client_socket = bind_to_server(node);
	if(client_socket == null){
	    System.out.println("bind_to_server failed, null socket");
	    System.exit(1);	    	    
	}

	System.out.println("Cliente listo para recibir ordenes");
	while(running){
	    String command = null;
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
		break;
	    case 'D':
	    case 'd':
		break;
	    case 'A':
	    case 'a':
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
	
	try{
	    client_socket.close();
	}
	catch(IOException e){
	    System.out.println("Error cerrando el socket del cliente");
	    System.exit(1);
	}
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
		download_filepath = args[i+1];
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
}