
import java.io.Serializable;

public class P2pRequest implements Serializable {
    int op_code;
    int hash_id;
    byte[] data;
	
    public P2pRequest(){
	op_code = 0;
	hash_id = 0;
        data = null;
    }
	
    public P2pRequest(int oc, int hi,byte[] d){
	op_code = oc;
	hash_id = hi;
        data = d;
    }
}
