package HW1DistAI;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
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
            MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
             );
    AID[] curatorAgents;

    protected void setup() {
        System.out.println("Hello " + a1.getName());

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

                   done();


                }
            }
        });

        addBehaviour(new CyclicBehaviour() {

            ACLMessage receive = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

            @Override
            public void action() {
                DFAgentDescription dfd = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();

                sd.setType("Artifacts");
                dfd.addServices(sd);
                SearchConstraints sc = new SearchConstraints();
                sc.setMaxResults(new Long(1));
                send(DFService.createSubscriptionMessage(myAgent, getDefaultDF(), dfd, sc));


                try {
                    DFAgentDescription[] result = DFService.search(myAgent, dfd);
                    curatorAgents = new AID[result.length];

                    for (int i = 0; i < curatorAgents.length; i++) {
                        curatorAgents[i] = result[i].getName();
                    }


                    ACLMessage requestArtifacts = new ACLMessage(ACLMessage.REQUEST);
                    for(int i = 0; i < curatorAgents.length; i++) {
                        requestArtifacts.addReceiver(curatorAgents[i]);
                    }
                  //  for (int i = 0; i < curatorAgents.length; i++) {
                  //      System.out.println("Curator agent #" + i + " is " + curatorAgents[i].getName());
                  //  }
                    requestArtifacts.setContent(receive.getContent());
                    requestArtifacts.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
                    requestArtifacts.setConversationId("request-artifacts");
                    myAgent.addBehaviour(new RequestPerformerTourGuideAgent(myAgent, requestArtifacts));

                } catch (FIPAException e) {
                    e.printStackTrace();
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
