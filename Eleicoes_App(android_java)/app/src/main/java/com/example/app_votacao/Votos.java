package com.example.app_votacao;

public class Votos {
    private int vot_id;
    private int total;
    private Candidato candidato;

    public Votos(int id, int total, Candidato candidato) {
        this.vot_id = id;
        this.total = total;
        this.candidato = candidato;
    }

    public int getId() {
        return vot_id;
    }

    public void setId(int id) {
        this.vot_id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }
}
