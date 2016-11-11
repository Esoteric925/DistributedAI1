package HW1DistAI;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;

import java.util.*;

/**
 * Created by Amir and Araxi on 2016-11-09.
 */
public class ProfilerAgent extends Agent {

    private AID a1 = new AID("ProfilerAgent", AID.ISLOCALNAME);
    String name;
    String gender;
    String age;
    String interest;
    String occupation;
    ArrayList<String> visitedArtifacts = new ArrayList<String>();

    protected void setup(){
        System.out.println("Hello " + a1.getName());

        Object[] args = getArguments();

        if (args != null && args.length > 0){
            name = (String) args[0];
            gender = (String) args[1];
            age = (String) args[2];
            interest = (String) args[3];
            occupation = (String) args[4];

            for (int i = 5; i< args.length; i++){
                if (args[i] != null){
                    visitedArtifacts.add((String) args[i]);
                }
            }

            addBehaviour(new WakerBehaviour(this, 1000) {
                protected void handleElapsedTimeout(){
                }
            });

        }else{
            System.out.println("Nothing to do.. lets terminate ");
            doDelete();
        }





    }

    protected void takeDown(){
        System.out.println("The agent " + a1.getName() + " will be terminated");
    }


}
