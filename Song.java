/**
 * Abstracción de canción.
 */
public class Song{
    /**
     *Identificador único del nodo que tiene la canción.
     */
    public String node_id;

    /**
     *Localización de la canción.
     */
    public String location;

    /**
     *Título de la canción.
     */
    public String title;

    /**
     *Creador de la canción.
     */
    public String creator;
    
    /**
     * 
     * @param l Localización de la canción.
     * @param t Título de la canción.
     * @param c Creador de la canción.
     * @param n Identificador único del nodo que tiene la canción.
     */
    public Song(String l, String t, String c, String n){
	location = l.toLowerCase();
	title = t.toLowerCase();
	creator = c.toLowerCase();
	node_id = n;
    }

    /**
     *Constructor por defecto.
     */
    public Song(){}
    
    /**
     * Retorna una representación en String de esta canción.
     * @return String representativo de la canción.
     */
    @Override
    public String toString() {
        String resp = creator+"@@"+title;
        return resp;
    }
            
}