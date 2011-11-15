import nanoxml.*;
import java.util.*;
import java.io.*;

public class ParseXSPF{
    public static void main(String args[]){
	//Song songs[] = parse(args[0]);
	// for(int i = 0; i<songs.length; ++i){
	//     System.out.println(songs[i].location + "||" +
	// 		       songs[i].title + "||" +
	// 		       songs[i].creator);
	// }
    }

    //public static Song[] parse(String filename){
    public static HashMap<String,Song> parse(String filename){
	//LinkedList<Song> sl = new LinkedList<Song>();
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
		    //sl.add(s);
		    sl.put(s.title+"-"+s.creator,s);
		    //System.out.println("parser:"+s.title +"-"+ s.creator);//flag
		    //System.out.println(sl.get(s.title +"-"+ s.creator));//flag
		}
	    }
	    else{
		System.out.println("Error, lista de reproduccion mal formateada: No se encontro elemento playlist en el tope del arbol");
		System.exit(1);
	    }
	}
	catch(Exception e){}
	
	//Song sa[] = new Song[sl.size()];
	return sl;//.toArray(sa);
    }

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