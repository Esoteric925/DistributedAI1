package HW1DistAI;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREResponder;

/**
 * Created by Amir on 2016-11-12.
 */
public class RespondPerformerProfilerAgent extends SimpleAchieveREResponder {


    public RespondPerformerProfilerAgent(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    
}
