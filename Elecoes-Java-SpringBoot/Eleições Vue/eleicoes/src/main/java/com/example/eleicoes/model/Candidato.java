package com.example.eleicoes.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="candidato")
public class Candidato {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ca_id")
    private Long id;

    @Column(name="ca_numero")
    private Long numero;

    @Column(name="ca_nome")
    private String nome;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "par_id", referencedColumnName = "par_id"
//    Exemplo acima é caso fosse um para um

    @ManyToOne
    @JoinColumn(name="par_id", nullable=false)
    private Partido partido;
    /*
        Quem possui a coluna de chave estrangeira recebe a anotação @JoinColumn.
        Por sua vez, do quem não tem achave estrangeira coloca  @OneToOne(mappedBy = "address")
     */
    @OneToMany(mappedBy="candidato")
    private List<Cargo> cargos;

    @OneToMany(mappedBy = "candidato")
    private List<Votos> votos;

    public Candidato() {

    }

    public Candidato(Long id, Long numero, String nome, Partido partido, List<Cargo> cargos, List<Votos> votos) {
        this.id = id;
        this.numero = numero;
        this.nome = nome;
        this.partido = partido;
        this.cargos = cargos;
        this.votos = votos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }


    // Se não colocar no candidato não consigo acessar o seus votos, pq está privado os votos
   /* public List<Votos> getVotos() {
        return votos;
    }*/
//
//    public void setVotos(List<Votos> votos) {
//        this.votos = votos;
//    }
}
