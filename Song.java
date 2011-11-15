public class Song{
    public String location;
    public String title;
    public String creator;
    
    public Song(String l, String t, String c){
	location = l.toLowerCase();
	title = t.toLowerCase();
	creator = c.toLowerCase();
    }

    public Song(){}
    
    @Override
    public String toString() {
        String resp = creator+"@@"+title;
        return resp;
    }
            
}