public class P2pRequest{
    String data;
    int data_size;
    int op_code;
    int hash_id;
	
    public P2pRequest(){
	data = "";
	op_code = 0;
	hash_id = 0;
    }
	
    public P2pRequest(String d, int oc, int hi){
	data = d;
	op_code = oc;
	hash_id = hi;
    }
}
