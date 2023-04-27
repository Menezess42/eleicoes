package com.example.eleicoes.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="eleicao")
public class Eleicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ele_id")
    private Long id;

    @Column(name = "ele_tipo")
    private String tipo;

    @Column(name = "ele_ano")
    private int ano;

    @OneToMany(mappedBy = "eleicao")
    private List<Votos> votos;

    public Eleicao() {

    }

    public Eleicao(Long id, String tipo, int ano, List<Votos> votos) {
        this.id = id;
        this.tipo = tipo;
        this.ano = ano;
        this.votos = votos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
