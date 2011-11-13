import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class P2pProtocolHandler{
    private static byte DOWNLOAD_HEXCODE  = 0x0;
    private static byte CONSULT_HEXCODE   = 0x1;
    private static byte REACHABLE_HEXCODE = 0x2;
    private static byte NULL_HEXCODE      = 0x4;
    private static int  NULL_HASHID       = 0xffffffff;
    // Estructuras de control
    private ConcurrentHashMap<String,String> SongDB;
    private static ArrayList<String> NodeDB; 
    private ConcurrentHashMap<Integer,P2pRequest> ConsultDB;
//    
//    private static byte S_DOWNLOAD_HEXCODE  = 0x0;
//    private static byte S_CONSULT_HEXCODE   = 0x1;
//    private static byte S_REACHABLE_HEXCODE = 0x2;
//    private static byte S_NULL_HEXCODE      = 0x4;
//    private static int  S_NULL_HASHID       = 0xffffffff;
    
    public P2pProtocolHandler(String knownNodesFilePath, String musicLib){
        ConsultDB = new ConcurrentHashMap<Integer,P2pRequest>();
        NodeDB = parseKnownNodesFile(knownNodesFilePath);
        SongDB = parseSongFile(musicLib);
    }
    
    private ConcurrentHashMap<String,String> parseSongFile(String musicLib){
        // Parser de Germán.
        ConcurrentHashMap<String,String> dummy = new ConcurrentHashMap
                <String,String>();
        return dummy;
    }
    
    private ArrayList<String> parseKnownNodesFile(String knownNodesFilePath){
        ArrayList<String> Nodes = new ArrayList<String>(); 
        try {
        BufferedReader nodeFile = new BufferedReader(new
                FileReader(knownNodesFilePath));
        String line;
        while ((line = nodeFile.readLine()) != null)
            Nodes.add(line);
        }
        catch(FileNotFoundException fnf) {
            System.out.println("Error al abrir archivo "
                    +knownNodesFilePath+" :"+fnf);
        }
        catch(IOException e){}
        return Nodes;
    }
    
    public P2pRequest getRequest(Socket s) {
        P2pRequest req = null;
        try {
            // Preparar para leer datos
            ObjectInputStream is = new ObjectInputStream(s.getInputStream());
            req = (P2pRequest) is.readObject();
        }
        catch (ClassNotFoundException csnf) {
            System.out.println("Error: "+csnf);
        }
        catch (IOException e ) {}
        return req;
    }
    
    public void makeConsult(P2pRequest req, Socket cs){
        
    }
    
    public void makeReachable(P2pRequest req, Socket cs){}
    
    public void sendSong(P2pRequest req, Socket cs) {
        // Nombre de archivo ?
        String nombreMP3 = new String(req.data);
        // Buscar en SongDB
        String rutaArchivo = SongDB.get(nombreMP3);
        // Cargar archivo
        try {
        File cancion = new File(rutaArchivo);
        FileInputStream fin = new FileInputStream(cancion);
        byte contenidoMP3[] = new byte[(int) cancion.length()];
        fin.read(contenidoMP3);
        // Preparar P2pRequest con respuesta
        P2pRequest respuesta = new P2pRequest(0x00,0,contenidoMP3);
        // Mandar respuesta al cliente
        ObjectOutputStream os = new ObjectOutputStream(cs.getOutputStream());
        os.writeObject(respuesta);
        os.close();
        fin.close();
        }
        catch(FileNotFoundException fnf) {
            System.out.println("Error: "+fnf);
        }
        catch(NullPointerException nl) {}
        catch(IOException e) {
            System.out.println("Error I/O: "+e);
        }
    }
}