package HW1DistAI;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;

/**
 * Created by Amir on 2016-11-09.
 */
public class CuratorAgent extends Agent {


   private AID a1 = new AID("CuratorAgent", AID.ISLOCALNAME);

    protected void setup(){

        System.out.println("Hello " + a1.getName());
        addBehaviour(new CyclicBehaviour() {
            public void action() {

            }
        });

    }

}
