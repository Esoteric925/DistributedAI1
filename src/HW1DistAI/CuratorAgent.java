package HW1DistAI;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;

/**
 * Created by Amir on 2016-11-09.
 */
public class CuratorAgent extends Agent {


   private AID a1 = new AID("CuratorAgent", AID.ISLOCALNAME);

    MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchConversationId("requests-artifacts"),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
    );

    protected void setup(){
      final ACLMessage receive = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));



        System.out.println("Hello " + a1.getName());
        addBehaviour(new OneShotBehaviour(this) {
            public void action() {

                if (receive != null){
                    addBehaviour(new RespondPerformerCuratorAgent(myAgent, template));

                }



            }
        });

    }

}
