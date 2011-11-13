import java.util.concurrent.*;
import java.net.*;
import java.io.*;

public class Client{

    private static Socket client_socket;
    private static int client_port;
    private static String node;
    private static String download_filepath;

    public static void main(String args[]){
	//P2pProtocolHandler p2p_handler = new P2pProtocolHandler(client_socket);
	BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
	
	set_params(args);

	while(true){
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
		break;
	    default:
		System.out.println("Comando invalido");
		break;
	    }
	}

	// try{
	//     client_socket.close();
	// }
	// catch(IOException e){}
    }

    private static void set_params(String args[]){
	char op = '\0';
	int i = 0;

	while( i < args.length ){
	    op = args[i].charAt(1);
	    switch(op){
	    case 'p':
		client_port = Integer.parseInt(args[i+1]);
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
    }
}