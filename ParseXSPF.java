import nanoxml.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author jorge
 */
public class ParseXSPF{
    /**
     * 
     * @param args
     */
    public static void main(String args[]){
	parse(args[0]);
    }

    /**
     *
     * @param filename
     * @return
     */
    public static HashMap<String,Song> parse(String filename){
	HashMap<String,Song> sl = new HashMap<String,Song>();

	try{
	    XMLElement xspf = new XMLElement();
	    FileReader reader = new FileReader(filename);
	    xspf.parseFromReader(reader);
	    
	    if(xspf.getName().compareTo("playlist") == 0){
		//Busco el elemento trackList
		Enumeration playlistContents = xspf.enumerateChildren();
		XMLElement trackList = null;
		do{
		    trackList = (XMLElement) playlistContents.nextElement();
		}while((trackList != null)&&
		       (trackList.getName().compareTo("trackList") != 0));

		if(trackList == null){
		    System.out.println("Error, lista de reproduccion mal formateada: No se encontro trackList");
		    System.exit(1);
		}

		//Itero sobre las canciones
		Enumeration tracks = trackList.enumerateChildren();
		while(tracks.hasMoreElements()){
		    XMLElement track = (XMLElement)tracks.nextElement();
		    Song s = new Song();
		    Enumeration attrs = track.enumerateChildren();
		    while(attrs.hasMoreElements()){
			XMLElement attr = (XMLElement)attrs.nextElement();
			get_xspf_attr(attr,s);
		    }
		    sl.put(s.title+"-"+s.creator,s);
		    System.out.println(s.title+"|||"+s.creator+"  "+s.location);//flag
		}
	    }
	    else{
		System.out.println("Error, lista de reproduccion mal formateada: No se encontro elemento playlist en el tope del arbol");
		System.exit(1);
	    }
	}
	catch(Exception e){}
	
	return sl;
    }

    /**
     *
     * @param attr
     * @param s
     */
    public static void get_xspf_attr(XMLElement attr, Song s){
	String attr_name = attr.getName();
	
	if (attr_name.compareTo("location") == 0){
	    s.location = attr.getContent().toLowerCase();
	}
	else if (attr_name.compareTo("title") == 0){
	    s.title = attr.getContent().toLowerCase();
	}
	else if (attr_name.compareTo("creator") == 0){
	    s.creator = attr.getContent().toLowerCase();
	}
    }
}