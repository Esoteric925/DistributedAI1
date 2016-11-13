package HW1DistAI;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREInitiator;

import java.util.Vector;

/**
 * Created by Amir on 2016-11-11.
 */
public class RequestPerformerProfilerAgent extends SimpleAchieveREInitiator{


    public RequestPerformerProfilerAgent(Agent a, ACLMessage msg) {
        super(a, msg);

    }

    @Override
    protected ACLMessage prepareRequest(ACLMessage msg) {
        return super.prepareRequest(msg);


    }

    @Override
    protected void handleInform(ACLMessage msg) {
       // System.out.println("Vi är i handleInform" + msg.getContent());
        super.handleInform(msg);
    }

    @Override
    protected void handleAgree(ACLMessage msg) {

        System.out.println("Message som mottogs i profiler agent " + msg.getContent());
        super.handleAgree(msg);
    }
}
