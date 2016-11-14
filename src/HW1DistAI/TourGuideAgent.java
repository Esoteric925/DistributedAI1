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
import jade.lang.acl.UnreadableException;
import jade.proto.SimpleAchieveREInitiator;
import jade.proto.states.MsgReceiver;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Amir on 2016-11-09.
 */
public class TourGuideAgent extends Agent {

    String test = "test";
    private AID a1 = new AID("TourGuideAgent", AID.ISLOCALNAME);
    MessageTemplate template = MessageTemplate.and(
            MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST) );

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


        DFAgentDescription dfd1 = new DFAgentDescription();
        ServiceDescription sd1 = new ServiceDescription();

        sd1.setType("Artifacts");
        dfd1.addServices(sd1);
        SearchConstraints sc = new SearchConstraints();
        sc.setMaxResults(new Long(1));
        send(DFService.createSubscriptionMessage(this, getDefaultDF(), dfd1, sc));

        try {
            DFAgentDescription[] result = DFService.search(this, dfd1);
            curatorAgents = new AID[result.length];

            for (int i = 0; i < curatorAgents.length; i++) {
                curatorAgents[i] = result[i].getName();
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }


        addBehaviour(new MsgReceiver(this, template, Long.MAX_VALUE, null, null){
            @Override

            protected void handleMessage(final ACLMessage msg) {

                System.out.println("I MSGRECEIVER FÖRVÄNTAR MIG DAVINCI OCH FÅR " + msg.getContent());
                ACLMessage requestArtifacts = new ACLMessage(ACLMessage.REQUEST);
                for(int i = 0; i < curatorAgents.length; i++) {
                    requestArtifacts.addReceiver(curatorAgents[i]);
                }
                requestArtifacts.setContent(msg.getContent());
                requestArtifacts.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
                requestArtifacts.setConversationId("request-artifacts");
                final ACLMessage profilerMsg = msg;
                msg.createReply();
                msg.setContent("HEJEHEJEJJE");
                send(msg);

                myAgent.addBehaviour(new SimpleAchieveREInitiator(myAgent, requestArtifacts){

                                         @Override
                                         protected ACLMessage prepareRequest(ACLMessage msg) {

                                         //System.out.println("Vi är i tourguide och ska skicka ett msg till curator som är " + msg.getContent());
                                             return super.prepareRequest(msg);
                                         }

                                         @Override
                                         protected void handleInform(ACLMessage msg) {
                                             try {

                                                 ArrayList<String> as = (ArrayList<String>) msg.getContentObject();
                                                 for(int i = 0; i <as.size(); i++ ){
                                                     System.out.println("är i tourguidagent med arraylistan av paintings " + as.get(i));

                                                 }
                                                 //ACLMessage replyProfiler = new ACLMessage(ACLMessage.INFORM);
                                                 //replyProfiler.addReceiver(profiler);
  //  System.out.println("profilermsg innan " + profilerMsg.getContent());
            //                                     profilerMsg.createReply();
          //                                       profilerMsg.setContentObject(as);

                                            //     System.out.println("profilermsg efter" + profilerMsg.getContentObject());
                                             //    System.out.println("recievern för profilermsg e " + profilerMsg.getSender());
                                           //      send(profilerMsg);

                                             } catch (Exception e) {
                                                 e.printStackTrace();
                                             }

                                             super.handleInform(msg);
                                         }

                                         @Override
                                         protected void handleAgree(ACLMessage msg) {
                                             try {
                                                 System.out.println("ÄR I SIMPLEBEHAVIOIR I AGREE O FÖRVÄNTAR MIG ARRAYLIST");
                                                 ArrayList<String> as = (ArrayList<String>) msg.getContentObject();
                                                 for(int i = 0; i <as.size(); i++ ){
                                                     System.out.println("är i tourguidagent med arraylistan av paintings " + as.get(i));

                                                 }
                                             } catch (UnreadableException e) {
                                                 e.printStackTrace();
                                             }
                                             super.handleAgree(msg);
                                         }
                                     }
                );

                super.handleMessage(msg);
            }

        });
    }
}
