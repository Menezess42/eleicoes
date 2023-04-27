package com.example.eleicoes.model;

import javax.persistence.*;

@Entity
@Table(name="cargo")
public class Cargo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    @Column(name = "car_descr")
    private String descricao;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ca_id")
    private Candidato candidato;

    public Cargo() {

    }

    public Cargo(Long id, String descricao, Candidato candidato) {
        this.id = id;
        this.descricao = descricao;
        this.candidato = candidato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

}
