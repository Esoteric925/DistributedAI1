package HW1DistAI;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;

/**
 * Created by Amir on 2016-11-09.
 */
public class CuratorAgent extends Agent {


   private AID a1 = new AID("CuratorAgent", AID.ISLOCALNAME);



    MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
    );




    protected void setup(){

        System.out.println("Hello " + a1.getName());

        final DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Artifacts");
        sd.setName("MuseumArtifacts");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addBehaviour(new OneShotBehaviour(this) {
            ACLMessage receive = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
            @Override
            public void action() {
                System.out.println("Kommer vi in hit då?");

                if (receive != null){
                    System.out.println("Vad innehålle recieve i curator" + receive.getContent());
                    addBehaviour(new RespondPerformerCuratorAgent(myAgent, template));

                }else{
                    System.out.println("BAJS");
                }



            }
        });

    }

}
