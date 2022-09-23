package controllers;

import abominableFramework.Controller;
import abominableFramework.Publisher;
import abominableFramework.Subscriber;
import models.Candidato;
import models.CandidatoDao;

public class CandidatoController extends Controller {
    public void increaseVotosCanditado(String canditadoName) {
        Candidato candidato = Candidato.getNullInstance();
        candidato = candidato.findCandidatoByName(canditadoName);
        candidato.setNumero_de_votos(candidato.getNumero_de_votos() + 1);
        candidato.update();
    }

    public CandidatoDao[] getAllCandidatos() {
        Candidato candidato = Candidato.getNullInstance();
        CandidatoDao[] candidatos = (CandidatoDao[]) candidato.getAll();
        return candidatos;
    }
}
