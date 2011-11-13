import java.util.concurrent.*;
import java.io.*;
import java.net.*;

public class P2pProtocolHandler{
    private static byte DOWNLOAD_HEXCODE  = 0x0;
    private static byte CONSULT_HEXCODE   = 0x1;
    private static byte REACHABLE_HEXCODE = 0x2;
    private static byte NULL_HEXCODE      = 0x4;
    private static int  NULL_HASHID       = 0xffffffff;
//    
//    private static byte S_DOWNLOAD_HEXCODE  = 0x0;
//    private static byte S_CONSULT_HEXCODE   = 0x1;
//    private static byte S_REACHABLE_HEXCODE = 0x2;
//    private static byte S_NULL_HEXCODE      = 0x4;
//    private static int  S_NULL_HASHID       = 0xffffffff;
    
//    public P2pRequest getRequest(Socket client_socket){
//        BufferedReader in = null;
//        PrintWriter out = null;
//        int header = 0;
//        int op_code = 0;
//        int hash_id = 0;
//        int data_size = 0;
//        try{
//            // Inicialización de buffers.
//            in = new BufferedReader(new InputStreamReader( client_socket.getInputStream()));
//            out = new PrintWriter(client_socket.getOutputStream(), true);
//            //Se lee el header
//            header = in.read();
//            header = header << 16;
//            header += in.read();
//            op_code = header ^ 0xc0000000;
//            hash_id = header ^ 0x3fffffff;
//            data_size = in.read();
//            data_size = data_size <<16;
//            data_size += in.read();
//        }
//        catch(IOException e){}
//        
//        return new P2pRequest(null,op_code,hash_id);
//    }
    
    public void makeConsult(ConcurrentMap<String,String> SongDB,
            ConcurrentMap<Integer,P2pRequest> ConsultDB,
            P2pRequest req){
        
    }
    
    public void makeReachable(ConcurrentMap<String,String> NodeDB,
            ConcurrentMap<Integer,P2pRequest> ConsultDB,
            P2pRequest req){}
    
    public void sendSong(ConcurrentMap<String,String> SongDB,P2pRequest req) {
//        BufferedReader in = null;
//        OutputStream out = null;
//        Socket client_socket = req.sock;
//        try {
//            // Crear buffers
//            in = new BufferedReader(new InputStreamReader( 
//                    client_socket.getInputStream()));
//            out = client_socket.getOutputStream();
//            // Obtener información acerca de la canción.
//            // De cuántos bytes es el string nombre ?
//            int nReads = req.data_size/2;
//            char[] buff = new char[nReads];
//            in.read(buff, 0, nReads);
//            String songName = new String(buff);
//            // Buscar canción en base de datos
//            String fileName = SongDB.get(songName);
//            File songFile = new File(fileName);
//            // Preparar buffer para leer archivo
//            FileInputStream fin = new FileInputStream(songFile);
//            byte fileContent[] = new byte[(int)songFile.length()];
//            // Leer archivo
//            fin.read(fileContent);
//            out.write(fileContent);
//        }
//        catch (FileNotFoundException fnf) {
//            System.out.println("Archivo no encontrado: " + fnf);
//        }
//        catch (IOException e) {
//            System.out.println("Error de I/O: " + e);
//        }
    }
}