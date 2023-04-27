package com.example.eleicoes.model;


import javax.persistence.*;

@Entity
@Table(name="partido")
public class Partido {

    /*
        Caso o nome da Entidade e seus atributos
        forem os mesmos da tabela no banco,
        as anotações @Table e @Column podem
        ser omitidas.
     */

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="par_id")
    private Long id;
    @Column(name="par_nome")
    private String nome;

    public Partido() {

    }

    public Partido(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
