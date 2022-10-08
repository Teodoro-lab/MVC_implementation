package controllers;

import models.Candidato;
import models.CandidatoDao;

import java.io.IOException;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import informer.Informer;
import libraries.abominableFramework.Controller;

public class CandidatoController extends Controller {

    public void handlePeerToPeerUpdate(JSONObject incommingInfo)
            throws UnknownHostException, IOException, ParseException {
        if (incommingInfo != null) {
            Candidato candidato = Candidato.getNullInstance();
            Candidato[] candidatos = (Candidato[]) candidato.getAll();

            for (int i = 0; i < candidatos.length; i++) {
                candidato = candidatos[i];
                long numVotos = (long) incommingInfo.get(candidato.getNombre());
                setVotosCandidato(candidato.getNombre(), numVotos);
            }
        }
    }

    public void increaseVotosCanditado(String canditadoName) throws UnknownHostException, IOException, ParseException {
        Candidato candidato = Candidato.getNullInstance();
        candidato = candidato.findCandidatoByName(canditadoName);
        candidato.setNumero_de_votos(candidato.getNumero_de_votos() + 1);
        candidato.update();
        Informer.startSendingInfo = true;
    }

    public void setVotosCandidato(String candidatoName, long votosNum) {
        Candidato candidato = Candidato.getNullInstance();
        candidato = candidato.findCandidatoByName(candidatoName);
        candidato.setNumero_de_votos(votosNum);
        candidato.update();
    }

    public CandidatoDao[] getAllCandidatos() {
        Candidato candidato = Candidato.getNullInstance();
        CandidatoDao[] candidatos = (CandidatoDao[]) candidato.getAll();
        return candidatos;
    }
}
