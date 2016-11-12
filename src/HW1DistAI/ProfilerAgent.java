package HW1DistAI;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREInitiator;


import java.util.*;

/**
 * Created by Amir and Araxi on 2016-11-09.
 */

/*per, male, 15, davinci, nerd, hola, bandola*/
public class ProfilerAgent extends Agent {

    private AID a1 = new AID("ProfilerAgent", AID.ISLOCALNAME);
    boolean foundTour = false;
    String name;
    String gender;
    String age;
    String interest;
    String occupation;
    ArrayList<String> visitedArtifacts = new ArrayList<String>();
    AID[] tourGuideAgents;
    private MessageTemplate mt;


    protected void setup(){
        System.out.println("Hello " + a1.getName());

        Object[] args = getArguments();

        if (args != null && args.length > 0){
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
                addBehaviour(new TickerBehaviour(this, 1000) {
                    protected void onTick() {
                        //check for a service called virtual tour
                        DFAgentDescription template = new DFAgentDescription();
                        ServiceDescription sd = new ServiceDescription();

                        sd.setType("VirtualTour");
                        template.addServices(sd);

                        try {
                            //find agents that provides the virtual tour service
                            DFAgentDescription[] result = DFService.search(myAgent, template);
                            tourGuideAgents = new AID[result.length];

                            for (int i = 0; i < tourGuideAgents.length; i++) {
                                tourGuideAgents[i] = result[i].getName();
                            }
                            for (int i = 0; i < tourGuideAgents.length; i++) {
                                System.out.println("Tour guide agent #" + i + " is " + tourGuideAgents[i].getName());
                            }

                            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);

                            for(int i = 0; i < tourGuideAgents.length; i++) {
                                request.addReceiver(tourGuideAgents[i]);
                            }
                            request.setContent(interest);
                            request.setConversationId("create-virtual-tour");
                            request.setReplyWith("request" + System.currentTimeMillis());
                            myAgent.send(request);

                            //Prepare the template to get request information
                            mt = MessageTemplate.and(MessageTemplate.MatchConversationId("create-virtual-tour"),
                                    MessageTemplate.MatchInReplyTo(request.getReplyWith()));

                            myAgent.receive(mt);

                            myAgent.addBehaviour(new RequestPerformer(myAgent, request));

                        } catch (FIPAException e) {
                            e.printStackTrace();
                            System.out.println("Det blev fel");
                        }
                    }
                });

        }else{
            System.out.println("Nothing to do.. lets terminate ");
            doDelete();
        }





    }

    protected void takeDown(){
        System.out.println("The agent " + a1.getName() + " will be terminated");
    }


}
