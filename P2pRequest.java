public class P2pRequest{
    String data;
    P2pS.OPCODE op_code;
    int hash_id;
	
    public P2pRequest(){
	data = "";
	op_code = P2pS.OPCODE.NONE;
	hash_id = 0;
    }
	
    public P2pRequest(String d, P2pS.OPCODE oc, int hi){
	data = d;
	op_code = oc;
	hash_id = hi;
    }
}
