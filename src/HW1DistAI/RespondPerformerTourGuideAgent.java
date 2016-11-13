package HW1DistAI;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREResponder;

import java.util.ArrayList;

/**
 * Created by Amir on 2016-11-12.
 */
public class RespondPerformerTourGuideAgent extends SimpleAchieveREResponder {

    public RespondPerformerTourGuideAgent(Agent a, MessageTemplate mt) {
        super(a, mt);
    }


    @Override
    protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {

        ACLMessage reply = request.createReply();
        reply.setContent("hoa");
        reply.setPerformative(ACLMessage.AGREE);

        return reply;
    }

    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {

        return response;
    }



}

