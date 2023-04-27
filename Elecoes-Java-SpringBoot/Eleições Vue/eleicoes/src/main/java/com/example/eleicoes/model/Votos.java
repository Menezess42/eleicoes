package com.example.eleicoes.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="votos")
public class Votos {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "vot_id")
    private Long vot_id;

    @Column(name = "vot_total")
    private int total;

    @ManyToOne
    @JoinColumn(name = "ca_id", referencedColumnName = "ca_id")
    private Candidato candidato;

    @ManyToOne
    @JoinColumn(name = "ele_id", referencedColumnName = "ele_id")
    private Eleicao eleicao;

    public Votos() {

    }

    public Votos(Long vot_id, int total, Candidato candidato, Eleicao eleicao) {
        this.vot_id = vot_id;
        this.total = total;
        this.candidato = candidato;
        this.eleicao = eleicao;
    }

    public Long getVot_id() {
        return vot_id;
    }

    public void setVot_id(Long vot_id) {
        this.vot_id = vot_id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Eleicao getEleicao() {
        return eleicao;
    }

    public void setEleicao(Eleicao eleicao) {
        this.eleicao = eleicao;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }
    public Candidato getCandidato()
    {
        return candidato;
    }

}
