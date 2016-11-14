package HW1DistAI;


import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
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
import java.util.*;

/**
 * Created by Amir and Araxi on 2016-11-09.
 */

/*Per, male, 15, Davinci, nerd, hola, bandola*/

public class ProfilerAgent extends Agent  {

    private AID a1 = new AID("ProfilerAgent", AID.ISLOCALNAME);
    String name;
    String gender;
    String age;
    String interest;
    String occupation;
    ArrayList<String> visitedArtifacts = new ArrayList<String>();
    AID[] tourGuideAgents;
    private MessageTemplate mt;
    AID [] curatorAgents;


    protected void setup(){
        final Object[] args = getArguments();
        System.out.println("Hello " + a1.getName());

        if (args != null && args.length > 0){

            addBehaviour(new OneShotBehaviour() {
                @Override
                public void action() {
                    name = (String) args[0];
                    gender = (String) args[1];
                    age = (String) args[2];
                    interest = (String) args[3];
                    occupation = (String) args[4];

                    for (int i = 5; i< args.length; i++){
                        if (args[i] != null){
                            visitedArtifacts.add((String) args[i]);
                        }
                    }
                }
            });

            addBehaviour(new TickerBehaviour(this, 1000)  {
                protected void onTick() {

                    /*Check for a service called virtual tour*/
                    DFAgentDescription dfd = new DFAgentDescription();
                    ServiceDescription sd = new ServiceDescription();

                    sd.setType("VirtualTour");
                    dfd.addServices(sd);
                    SearchConstraints sc = new SearchConstraints();
                    sc.setMaxResults(new Long(1));
                    send(DFService.createSubscriptionMessage(myAgent, getDefaultDF(), dfd, sc));


                        /*find agents that provides the virtual tour service*/
                    DFAgentDescription[] result = new DFAgentDescription[0];

                    try {
                        result = DFService.search(myAgent, dfd);
                    } catch (FIPAException e) {
                        e.printStackTrace();
                    }
                    tourGuideAgents = new AID[result.length];
                        for (int i = 0; i < tourGuideAgents.length; i++) {
                            tourGuideAgents[i] = result[i].getName();
                        }

                        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                        for(int i = 0; i < tourGuideAgents.length; i++) {
                            request.addReceiver(tourGuideAgents[i]);
                        }
                        request.setContent(interest);
                        request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
                        request.setConversationId("request-virtual-tour");

                        /* request a virtual tour*/
                        myAgent.addBehaviour(new RequestPerformerProfilerAgent(myAgent, request));



            }});
        }else{ System.out.println("Nothing to do.. lets terminate "); doDelete(); }

    }
    protected void takeDown(){
        System.out.println("The agent " + a1.getName() + " will be terminated");
    }


    private class RequestPerformerProfilerAgent extends SimpleAchieveREInitiator {

        public RequestPerformerProfilerAgent(Agent a, ACLMessage msg) {
            super(a, msg);
        }

        @Override
        protected ACLMessage prepareRequest(ACLMessage msg) {
            return super.prepareRequest(msg);
        }

        @Override
        protected void handleInform(ACLMessage msg) {

            DFAgentDescription dfd1 = new DFAgentDescription();
            ServiceDescription sd1 = new ServiceDescription();

            sd1.setType("Artifacts");
            dfd1.addServices(sd1);
            SearchConstraints sc = new SearchConstraints();
            sc.setMaxResults(new Long(1));

            send(DFService.createSubscriptionMessage(myAgent, getDefaultDF(), dfd1, sc));
            DFAgentDescription[] result = new DFAgentDescription[0];

            try {
                result = DFService.search(myAgent, dfd1);
                curatorAgents = new AID[result.length];
                ACLMessage sendToCurator = new ACLMessage(ACLMessage.REQUEST);
                for (int i = 0; i < curatorAgents.length; i++) {
                    curatorAgents[i] = result[i].getName();
                    sendToCurator.addReceiver(curatorAgents[i]);
                    sendToCurator.setContentObject(msg.getContentObject());

                    sendToCurator.setConversationId("from-profiler");
                    send(sendToCurator);

                    ACLMessage receive = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.AGREE));
                    if (receive != null) {
                        ArrayList<Artifacts> information = (ArrayList<Artifacts>) receive.getContentObject();
                        System.out.println("The virtual tour is " );
                        for (int j = 0; j< information.size(); j++ ){
                            System.out.println(information.get(j).getId() + " " + information.get(j).getArtist() + " " +
                                    information.get(j).getType() + " " + information.get(j).getYear());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            super.handleInform(msg);
        }
    }
}

