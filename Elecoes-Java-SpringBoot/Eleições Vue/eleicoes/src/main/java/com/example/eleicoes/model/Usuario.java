package com.example.eleicoes.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {

    @Id
    private Long id;
    private String login;
    private String senha;
    private String nivel;

    public Usuario() {

    }

    public Usuario(Long id, String login, String senha, String nivel) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.nivel = nivel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String  nivel) {
        this.nivel = nivel;
    }
}
