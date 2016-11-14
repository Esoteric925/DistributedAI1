package HW1DistAI;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREInitiator;
import jade.proto.states.MsgReceiver;
import java.util.ArrayList;

/**
 * Created by Amir and Araxi on 2016-11-09.
 */

public class TourGuideAgent extends Agent {

    private AID a1 = new AID("TourGuideAgent", AID.ISLOCALNAME);
    MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST) );
    AID[] curatorAgents;

    protected void setup() {
        System.out.println("Hello " + a1.getName());

        /* set up Virtual tour service in DF */
        final DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("VirtualTour");
        sd.setName("Museum");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        /* subscribe Artifacts service by Curator agent */
        DFAgentDescription dfd1 = new DFAgentDescription();
        ServiceDescription sd1 = new ServiceDescription();

        sd1.setType("Artifacts");
        dfd1.addServices(sd1);
        SearchConstraints sc = new SearchConstraints();
        sc.setMaxResults(new Long(1));
        send(DFService.createSubscriptionMessage(this, getDefaultDF(), dfd1, sc));

        /* find all Curator agents */
        try {
            DFAgentDescription[] result = DFService.search(this, dfd1);
            curatorAgents = new AID[result.length];

            for (int i = 0; i < curatorAgents.length; i++) {
                curatorAgents[i] = result[i].getName();
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        /* receive interest from Profiler agent */
        addBehaviour(new MsgReceiver(this, template, Long.MAX_VALUE, null, null){
            @Override

            protected void handleMessage( ACLMessage msg) {
    /* */
                ACLMessage requestArtifacts = new ACLMessage(ACLMessage.REQUEST);
                for(int i = 0; i < curatorAgents.length; i++) {
                    requestArtifacts.addReceiver(curatorAgents[i]);
                }
                requestArtifacts.setContent(msg.getContent());
                requestArtifacts.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
                requestArtifacts.setConversationId("request-artifacts");
                final ACLMessage profilerMsg = msg;

                /* ask for interest from Curator Agent */
                myAgent.addBehaviour(new SimpleAchieveREInitiator(myAgent, requestArtifacts){

                                         @Override
                                         protected ACLMessage prepareRequest(ACLMessage msg) {
                                             return super.prepareRequest(msg);
                                         }

                                         /* send interesting artifacts from Curator to Profiler agent */
                                         @Override
                                         protected void handleInform(ACLMessage msg) {

                                             try {
                                                 System.out.println("Searching for a virtual tour regarding your interest ...");
                                                 ArrayList<String> as = (ArrayList<String>) msg.getContentObject();
                                                 ACLMessage message = profilerMsg.createReply();
                                                 message.setContentObject(as);
                                                 message.setPerformative(ACLMessage.INFORM);
                                                 send(message);
                                             } catch (Exception e) {
                                                 e.printStackTrace();
                                             }
                                             super.handleInform(msg);
                                         }
                                     }
                );
                super.handleMessage(msg);
            }
        });
    }
}
