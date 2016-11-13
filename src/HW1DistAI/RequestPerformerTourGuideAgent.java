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

    // System.out.println("Vi är i tourguide och ska skicka ett msg till curator som är " + msg.getContent());
        return super.prepareRequest(msg);
    }

    @Override
    protected void handleInform(ACLMessage msg) {
        super.handleInform(msg);
    }

    @Override
    protected void handleAgree(ACLMessage msg) {

        try {
//System.out.println("innan array");
            ArrayList<String> artifacts = (ArrayList) msg.getContentObject();


            for (int i = 0; i< artifacts.size(); i++){

                System.out.println("Listan innehåller " + artifacts.get(i));
            }
           // System.out.println("efter array med artifacts size som är " + artifacts.size());
        } catch (UnreadableException e) {
            e.printStackTrace();
        }
     //  System.out.println("Meddelandet som mottogs i tour guide agent är: " + msg.getContent());


        super.handleAgree(msg);
    }
}
