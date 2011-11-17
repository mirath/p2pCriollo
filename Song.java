/**
 *
 * @author jorge
 */
public class Song{
    /**
     *
     */
    public String node_id;
    /**
     *
     */
    public String location;
    /**
     *
     */
    public String title;
    /**
     *
     */
    public String creator;
    
    /**
     * 
     * @param l
     * @param t
     * @param c
     * @param n
     */
    public Song(String l, String t, String c, String n){
	location = l.toLowerCase();
	title = t.toLowerCase();
	creator = c.toLowerCase();
	node_id = n;
    }

    /**
     *
     */
    public Song(){}
    
    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String resp = creator+"@@"+title;
        return resp;
    }
            
}