package HW1DistAI;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Created by Amir on 2016-11-09.
 */
public class TourGuideAgent extends Agent {

    String test = "test";
    private AID a1 = new AID("TourGuideAgent", AID.ISLOCALNAME);
    MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchConversationId("requests-virtual-tour"),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
             );
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
                  //  System.out.println("Agent "+getLocalName()+": REQUEST received from "+receive.getSender().getName()+". Action is "
                   //         +receive.getContent());
                    addBehaviour(new RespondPerformerTourGuideAgent(myAgent, template));


                   /* ACLMessage requestArtifacts = new ACLMessage(ACLMessage.REQUEST);
                    requestArtifacts.setContent(receive.getContent());
                    requestArtifacts.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
                    requestArtifacts.setConversationId("request-artifacts");
                    addBehaviour(new RequestPerformerTourGuideAgent(myAgent, requestArtifacts)); */

                }
            }
        });



     //   System.out.println("Hello " + a1.getName());

    }

    private boolean checkAction(String msg) {
        // Simulate a check by generating a random number


        return (Math.random() > 0.2);
    }
}
