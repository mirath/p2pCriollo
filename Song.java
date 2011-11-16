public class Song{
    public String node_id;
    public String location;
    public String title;
    public String creator;
    
    public Song(String l, String t, String c, String n){
	location = l.toLowerCase();
	title = t.toLowerCase();
	creator = c.toLowerCase();
	node_id = n;
    }

    public Song(){}
    
    @Override
    public String toString() {
        String resp = creator+"@@"+title;
        return resp;
    }
            
}