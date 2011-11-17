import java.net.*;
import java.io.*;
import java.util.*;

/**
 *Clase del cliente
 */
public class Client{

    private static Socket client_socket = null;
    private static int node_port = -1;
    private static String node = null;
    private static String download_path = null;
    private static ArrayList<Song> current_songs = new ArrayList<Song>();
    private static P2pProtocolHandler p2pHandler = new P2pProtocolHandler();
    private static HashMap<String,Song> downloaded_songs = new HashMap<String,Song>();

    /**
     *
     * @param args
     */
    public static void main(String args[]){
	BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));
	boolean running = true;

	set_params(args);
        
	System.out.println("Cliente listo para recibir o mandar ordenes");
	while(running){
	    String command = null;
	    ServerRequest svr = null;
	    String ans = null;
	    String[] resto;
	    Song s = new Song();
	    print_songs();    

	    try{
		command = console.readLine();
		command.trim();
	    }
	    catch(IOException e){
		System.exit(1);
	    }

	    if(command.length() <= 0)
		continue;

	    switch(command.charAt(0)){
	    case 'C':
	    case 'c':
		resto = command.split("\\s");
	        // Preparar cadena
                if (resto.length > 1) {
		    // BÃºsqueda por autor ?
                    if (resto[1].compareTo("-a") == 0) {
                        String expr = parseSearchEntry(resto, 2);
                        ServerRequest srv =
			    new ServerRequest(client_socket,
					      node_port, node, "consult", "A@@"+expr,
					      download_path);
                        ans = srv.run();
			//System.out.println(ans);//flag
                    }
                    // BÃºsqueda por tÃ­tulo
                    else {
                        String expr = parseSearchEntry(resto, 2);
                        ServerRequest srv =
			    new ServerRequest(client_socket,
					      node_port,node, "consult", "T@@"+expr,
					      download_path);
                        ans = srv.run();
			//System.out.println(ans);//flag
		    }
		}
		// BÃºsqueda de todos los archivos
                else {
                    ServerRequest srv =
			new ServerRequest(client_socket, 
					  node_port,node, "consult", "W@@", 
					  download_path);
                    ans = srv.run();
                    //System.out.println(ans);//flag
                }
	    
	        current_songs = parse_songs(ans);

		break;

            case 'A':
	    case 'a':
                ServerRequest srv = new ServerRequest(client_socket, 
                                node_port,node, "reachable", "","");
                ans = srv.run();
                //System.out.println(ans);//flag
		print_reachable(ans);
		break;

	    case 'D':
	    case 'd':
		resto = command.split("\\s");
	        if(resto.length > 1){
		    int index = Integer.parseInt(resto[1]);

		    if(index >= current_songs.size()){
			System.out.println("La cancion con el id "+index+" no existe");
			break;
		    }

		    s = current_songs.get(index);
		    svr = new ServerRequest(client_socket,node_port,s.location,
		    			    "download",s.title+"-"+s.creator,
		    			    download_path);
		    String res = svr.run();

		    if(res != null){
			Song ds = new Song();
			ds.title   = s.title;
			ds.creator = s.creator;
			downloaded_songs.put(s.title+"-"+s.creator,ds);
		    }
		}
		else{
		    System.out.println("Comando Download malformado");
		}
		break;

	    case 'P':
	    case 'p':
		resto = command.split("\\s");
	        if(resto.length > 1){
		    int index = Integer.parseInt(resto[1]);

		    if(index >= current_songs.size()){
			System.out.println("La cancion con el id "+index+" no existe");
			break;
		    }

		    s = current_songs.get(index);

		    if(downloaded_songs.get(s.title+"-"+s.creator) == null){
			svr = new ServerRequest(client_socket,node_port,s.location,
						"download",s.title+"-"+s.creator,
						download_path);
			String res = svr.run();

			if(res != null){
			    Song ds = new Song();
			    ds.title   = s.title;
			    ds.creator = s.creator;
			    downloaded_songs.put(s.title+"-"+s.creator,ds);
			}
		    }
		    else{ System.out.println("Song already here"); } //flag
		}
		else{
		    System.out.println("Comando Play malformado");
		}

		try{
		    Runtime.getRuntime().exec(new String[]{"cvlc",
							   download_path+"/"+s.title+"-"+s.creator+".mp3"});
		}
		catch(IOException e){
		    System.out.println("I/O Error: "+e);
		}
	        // svr = new ServerRequest
                //         (client_socket,node_port,
                //         node,"download",
                //         "moulin rouge-mireille mathieu",download_path);
	        // svr.run();
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

    /**
     * Devuelve el número de carácteres
     * del título con más carácteres en su nombre.
     * @param songs Canciones que serán analizadas
     * @return Número de carácteres del título mas largo
     */
    private static int longest_title(ArrayList<Song> songs){
	int max = 0;
	for (int i = 0; i < songs.size(); ++i){
	    int aux = songs.get(i).title.length();
	    if (aux > max)
		max = aux; 
	}
	return max;
    }

    /**
     * Devuelve el número de carácteres del autor con más
     * carácteres en su nombre.
     * @param songs Canciones que serán analizadas
     * @return Número de carácteres del autor mas largo
     */
    private static int longest_creator(ArrayList<Song> songs){
	int max = 0;
	for (int i = 0; i < songs.size(); ++i){
	    int aux = songs.get(i).creator.length();
	    if ( aux > max)
		max = aux; 
	}
	return max;
    }

    /**
     *
     * @param n Número a procesar
     * @return Número de dígitos del número
     */
    private static int number_of_digits(int n){
	if (n == 0)
	    return 1;

	int digits = 0;

	while(n > 0){
	    digits += 1;
	    n = n/10;
	}

	return digits;
    }

    /**
     * Genera un String de espacios en blanco
     * @param n Número de espacios en blanco
     * @return String de espacios en blanco
     */
    private static String tab(int n){
	String tab = "";
	for(int i = 0; i < n; ++i){
	    tab += " ";
	}
	return tab;
    }

    /**
     * Genera un String de espacios en blanco
     * @param n Número de espacios en blanco
     * @return String de espacios en blanco
     */
    private static void print_reachable(String r){
	String rl[] = r.split("##");

	for(int i=0; i < rl.length; ++i){
	    System.out.println(rl[i]);
	}

	return;
    }
    
    /**
     * Imprime las canciones que resultaron de la última consulta
     */
    private static void print_songs(){
	if (current_songs.size() <= 0){
	    return;
	}

	int lt = max(longest_title(current_songs),6);
	int lc = max(longest_creator(current_songs),5);
	int d = max(number_of_digits(current_songs.size()),3);

	int sp = 4;

	System.out.println("Num"+tab(d-3+sp)+"Autor"+tab(lc-5+sp)+"Titulo"+tab(lt-6+sp)+"Nodo");
	for (int i = 0; i < current_songs.size(); ++i){
	    String c = current_songs.get(i).creator;
	    String t = current_songs.get(i).title;
	    String l = current_songs.get(i).node_id;
	    System.out.println(i + tab(d-number_of_digits(i)+sp)+
			       c + tab(lc-c.length()+sp)+
			       t + tab(lt-t.length()+sp)+
			       l);
	}	

	return;
    }

    /**
     * Parsea el resultado del comando C
     * @param ss resultado del comando C
     * @return Canciones parseadas
     */
    private static ArrayList<Song> parse_songs(String ss){
	ArrayList<Song> songs = new ArrayList<Song>();

	if(ss.length() <= 0){
	    System.out.println("No se encontraron canciones");
	    return songs;
	}
	
	String ss_strs[] = ss.split("##");

	for(int i = 0; i < ss_strs.length; ++i){
	    songs.add(parse_song(ss_strs[i]));
	}

	return songs;
    }

    /**
     * Genera un objeto Song a partir de una entrada del resultado
     * del comando C
     * @param s Canción a parsear
     * @return Objeto Song con la información de la canción
     */
    private static Song parse_song(String s){
	Song res = new Song();

	String song_data[] = s.split("@@");
	res.creator = song_data[0];
	res.title = song_data[1];
	res.location = song_data[2];
	res.node_id = song_data[3];

	return res;
    }

    /**
     * Parsea los argumentos del comando C
     * @param resto
     * @param startPoint
     * @return Objeto Song con la información de la canción
     */    
    private static String parseSearchEntry(String[] resto, int startPoint) {
        String expr = new String();
        int i = startPoint;
        for(i = startPoint; i < resto.length - 1; i++){
            expr += resto[i].toLowerCase();
            expr += " ";
        }
        expr += resto[i].toLowerCase();
        return expr;
    }

    /**
     * Parsea las opciones de la línea de comandos -p, -n y -d
     * @param args Línea de comandos
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

	if(download_path == null)
	    download_path = ".";
    }

    private static int max(int a, int b){
	if(a > b)
	    return a;
	else
	    return b;
    }
}