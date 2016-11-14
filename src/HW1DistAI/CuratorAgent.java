package HW1DistAI;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREResponder;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Amir and Araxi on 2016-11-09.
 */
public class CuratorAgent extends Agent {


   private AID a1 = new AID("CuratorAgent", AID.ISLOCALNAME);

    MessageTemplate templateTourGuide = MessageTemplate.and(
            MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

    MessageTemplate templateProfiler = MessageTemplate.and( MessageTemplate.MatchConversationId("from-profiler"),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

    protected void setup(){
        System.out.println("Hello " + a1.getName());

        /* Create Artifacts service in DF */
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


        SequentialBehaviour s = new SequentialBehaviour();
        ParallelBehaviour p = new ParallelBehaviour();
        p.addSubBehaviour(new RespondToTourGuideAgent(this, templateTourGuide));
        p.addSubBehaviour(new RespondToProfilerAgent(this, templateProfiler));

        s.addSubBehaviour(p);
        addBehaviour(s);

    }

    private class RespondToTourGuideAgent extends SimpleAchieveREResponder {

        public RespondToTourGuideAgent(Agent a, MessageTemplate mt) {
            super(a, mt);
        }

        @Override
        protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
            Artifacts artifact1 = new Artifacts("Painting1", "Davinci" , "Flowers", "1800");
            Artifacts artifact2 = new Artifacts("Painting2", "Davinci" , "Mona Lisa", "1800");
            Artifacts artifact3 = new Artifacts("Painting3", "Picasso" , "La Vie", "1900");
            ArrayList<Artifacts> artifacts = new ArrayList<Artifacts>();
            artifacts.add(artifact1);
            artifacts.add(artifact2);
            artifacts.add(artifact3);
            ArrayList<String> requestedArtifacts = new ArrayList<String>();
            ACLMessage reply = request.createReply();

            for(int i = 0; i < artifacts.size(); i ++){
                if(artifacts.get(i).getArtist().equalsIgnoreCase(request.getContent().toString()) ||
                        artifacts.get(i).getType().equalsIgnoreCase(request.getContent().toString()) ||
                        artifacts.get(i).getYear().equalsIgnoreCase(request.getContent().toString())){
                    requestedArtifacts.add(artifacts.get(i).getId());
                }
            }

            try {
                reply.setContentObject(requestedArtifacts);
            } catch (IOException e) {
                e.printStackTrace();
            }
            reply.setPerformative(ACLMessage.INFORM);
            return reply;
        }

        @Override
        protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
            return response;
        }
    }

    private class RespondToProfilerAgent extends SimpleAchieveREResponder {
        Artifacts artifact1 = new Artifacts("Painting1", "Davinci" , "Flowers", "1800");
        Artifacts artifact2 = new Artifacts("Painting2", "Davinci" , "Mona Lisa", "1800");
        Artifacts artifact3 = new Artifacts("Painting3", "Picasso" , "La Vie", "1900");
        ArrayList<Artifacts> artifacts = new ArrayList<Artifacts>();

        public RespondToProfilerAgent(Agent a, MessageTemplate mt) {
            super(a, mt);
        }


        @Override
        protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {

            artifacts.add(artifact1);
            artifacts.add(artifact2);
            artifacts.add(artifact3);
            ACLMessage reply = request.createReply();
            ArrayList<Artifacts> a = new ArrayList<Artifacts>();
            try {
                ArrayList<String> interestedArtifacts = (ArrayList<String>) request.getContentObject();
                for(int i = 0; i<interestedArtifacts.size(); i++){
                    for(int j = 0; j< artifacts.size(); j++){
                        if(artifacts.get(j).getId().equalsIgnoreCase(interestedArtifacts.get(i))){
                             a.add(i, artifacts.get(j));
                        }
                    }
                }
                reply.setContentObject(a);
                reply.setPerformative(ACLMessage.AGREE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return reply;
        }

        @Override
        protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {

            return response;
        }
    }

}
