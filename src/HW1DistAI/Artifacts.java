package HW1DistAI;

import jade.core.behaviours.TickerBehaviour;

/**
 * Created by Amir on 2016-11-09.
 */
public class Artifacts {
    String Id;
    String Type;

    public Artifacts(String Id, String Type){

        this.Id = Id;
        this.Type = Type;
    }

    public String getId(){
        return Id;
    }

    public String getType(){
        return Type;
    }


}
