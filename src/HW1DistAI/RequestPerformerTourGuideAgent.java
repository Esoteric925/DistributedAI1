package HW1DistAI;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.SimpleAchieveREInitiator;

import java.util.ArrayList;

/**
 * Created by Amir on 2016-11-12.
 */
public class RequestPerformerTourGuideAgent extends SimpleAchieveREInitiator {
    public RequestPerformerTourGuideAgent(Agent a, ACLMessage msg) {
        super(a, msg);
    }

    @Override
    protected ACLMessage prepareRequest(ACLMessage msg) {

        return super.prepareRequest(msg);
    }

    @Override
    protected void handleInform(ACLMessage msg) {
        super.handleInform(msg);
    }

    @Override
    protected void handleAgree(ACLMessage msg) {

       /* try {
            ArrayList<String> artifacts = (ArrayList<String>) msg.getContentObject();

            for (int i = 0; i< artifacts.size(); i++){

                System.out.println("Listan innehåller " + artifacts.get(i));
            }
        } catch (UnreadableException e) {
            e.printStackTrace();
        }*/
       System.out.println("Meddelandet från curator är: " + msg.getContent());


        super.handleAgree(msg);
    }
}
