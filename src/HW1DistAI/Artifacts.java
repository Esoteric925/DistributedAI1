package HW1DistAI;

import java.io.Serializable;

/**
 * Created by Amir on 2016-11-09.
 */
public class Artifacts implements Serializable {
    String id;
    String artist;
    String type;
    String year;

    public Artifacts(String id, String artist, String type, String year){

        this.id = id;
        this.artist = artist;
        this.type = type;
        this.year = year;
    }

    public String getId() { return id; }
    public String getType(){
        return type;
    }
    public String getArtist(){
        return artist;
    }
    public String getYear(){
        return year;
    }


}
