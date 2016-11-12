package HW1DistAI;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREResponder;

/**
 * Created by Amir on 2016-11-12.
 */
public class RespondPerformer extends SimpleAchieveREResponder {

    public RespondPerformer(Agent a, MessageTemplate mt) {
        super(a, mt);
        //System.out.println("TourGuideAgent ");
    }


    protected void handleInform(ACLMessage msg){
        System.out.println("message är " + msg.getContent());
    }

}
