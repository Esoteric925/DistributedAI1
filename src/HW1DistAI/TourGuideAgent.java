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


    private AID a1 = new AID("TourGuideAgent", AID.ISLOCALNAME);

    protected void setup(){

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("VirtualTour");
        sd.setName("Museum");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }catch(FIPAException e){
            e.printStackTrace();
        }


        addBehaviour(new CyclicBehaviour(this) {
            ACLMessage receive = receive();

            @Override
            public void action() {
                if (receive != null){
                    System.out.println("conversation ID är " + receive.getConversationId());
                    ACLMessage agree = new ACLMessage(ACLMessage.AGREE);
                MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId("create-virtual-tour"),
                        MessageTemplate.MatchInReplyTo(agree.getReplyWith()));
                addBehaviour(new RespondPerformer(myAgent, mt));

            }
        }});

        System.out.println("Hello " + a1.getName());

}
}
