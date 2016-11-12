package HW1DistAI;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Created by Amir on 2016-11-09.
 */
public class TourGuideAgent extends Agent {

    String test = "test";
    private AID a1 = new AID("TourGuideAgent", AID.ISLOCALNAME);

    protected void setup() {

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


        addBehaviour(new CyclicBehaviour(this) {
            ACLMessage receive = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

            @Override
            public void action() {
                if (receive != null) {


                   // ACLMessage agree = new ACLMessage(ACLMessage.AGREE);

                    //MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("create-virtual-tour"),
                      //      MessageTemplate.MatchInReplyTo(agree.getReplyWith()));
                    //addBehaviour(new RespondPerformer(myAgent, mt));
                   // agree.setReplyWith(test);

                    ACLMessage reply = receive.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent(test);
                    myAgent.send(reply);


                }
            }
        });

        System.out.println("Hello " + a1.getName());

    }
}
