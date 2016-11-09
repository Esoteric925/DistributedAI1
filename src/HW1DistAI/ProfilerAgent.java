package HW1DistAI;

import jade.core.AID;
import jade.core.Agent;

/**
 * Created by Amir and Araxi on 2016-11-09.
 */
public class ProfilerAgent extends Agent {

    private AID a1 = new AID("ProfilerAgent", AID.ISLOCALNAME);

    protected void setup(){

        System.out.println("Hello " + a1.getName());

    }



}
