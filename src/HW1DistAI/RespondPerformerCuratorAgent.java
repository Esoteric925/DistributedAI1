package HW1DistAI;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SimpleAchieveREResponder;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Amir on 2016-11-13.
 */
public class RespondPerformerCuratorAgent extends SimpleAchieveREResponder {
    Artifacts artifact1 = new Artifacts("Painting1", "Davinci" , "Flowers", "1800");
    Artifacts artifact2 = new Artifacts("Painting2", "Davinci" , "Mona Lisa", "1800");
    Artifacts artifact3 = new Artifacts("Painting3", "Picasso" , "La Vie", "1900");
    ArrayList<Artifacts> artifacts = new ArrayList<Artifacts>();

    public RespondPerformerCuratorAgent(Agent a, MessageTemplate mt) {
        super(a, mt);
    }


    @Override
    protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
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

       // ACLMessage reply = request.createReply();
       // reply.setContent("HEJ FRAN CURATOR");
        reply.setPerformative(ACLMessage.AGREE);
        return reply;
    }

    @Override
    protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {

        return response;
    }
}
