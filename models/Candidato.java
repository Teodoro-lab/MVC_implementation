package models;

import abominableFramework.Model;

public class Candidato extends Model implements CandidatoDao {
    private String nombre;
    private String partido_politico;
    private long numero_de_votos;

    public Candidato(String nombre, String partido_politico) {
        this.nombre = nombre;
        this.partido_politico = partido_politico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPartido_politico() {
        return partido_politico;
    }

    public void setPartido_politico(String partido_politico) {
        this.partido_politico = partido_politico;
    }

    public long getNumero_de_votos() {
        return numero_de_votos;
    }

    public void setNumero_de_votos(long numero_de_votos) {
        this.numero_de_votos = numero_de_votos;
    }

    public static Candidato getNullInstance() {
        return new Candidato("", "");
    }

    public Candidato findCandidatoByName(String name) {
        Candidato candidato;
        for (Model model : getAll()) {
            candidato = (Candidato) model;
            if (candidato.getNombre().equals(name)) {
                return candidato;
            }
        }
        return null;
    }

    @Override
    public long getNumVotos() {
        return numero_de_votos;
    }

}
