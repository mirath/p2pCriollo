
import java.net.Socket;

public class P2pRequest{
    String data;
    int data_size;
    int op_code;
    int hash_id;
    Socket sock;
	
    public P2pRequest(){
	data = "";
	op_code = 0;
	hash_id = 0;
        sock = new Socket();
    }
	
    public P2pRequest(String d, int oc, int hi, Socket sk){
	data = d;
	op_code = oc;
	hash_id = hi;
        sock = sk;
    }
}
