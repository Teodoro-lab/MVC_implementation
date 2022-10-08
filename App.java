import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import controllers.CandidatoController;
import forwarderReceiver.Peer;
import informer.Informer;
import libraries.abominableFramework.Model;
import models.Candidato;
import views.JavaSwingUI;

public class App {

    public static void main(String[] args) throws IOException, ParseException {
        if (args.length != 3) {
            System.err.println("Waiting for <Port to connect> <Port to listen> <db folder name>");
            return;
        }

        // console arguments
        int portToListen = Integer.parseInt(args[0]);
        int portToSend = Integer.parseInt(args[1]);
        String db = args[2];

        // set the informer as the service that will be run
        Informer informer = new Informer();
        Peer peer = new Peer(informer, portToListen, portToSend);

        // db config
        Model.setDataBaseLocation(db);

        // create connections beetween view and model
        Candidato nullCandidato = Candidato.getNullInstance();
        CandidatoController ctr = new CandidatoController();
        JavaSwingUI appJavaSwingUI = new JavaSwingUI("App", ctr);

        ctr.mapModelWithView(nullCandidato, appJavaSwingUI);

        // Iterates forever, this causes to always be listening for
        // incomming messages and at the same time being aware
        // of changes inside the app and send them to the other peer
        while (true) {
            System.out.println("Entering loop");
            JSONObject response = peer.service();
            System.out.println(response);
            if (response != null) {
                System.out.println("Got in response");
                ctr.handlePeerToPeerUpdate(response);
            }
        }

    }
}
