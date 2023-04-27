package com.example.app_votacao;

import java.util.List;
// ESSA É UMA SIMPLES CLASS EM JAVA PARA RECEBER OS CANDIDATOS VINDO DO BACKEND
public class Candidato implements Comparable<Candidato>{
    private int id;
    private int numero;
    private String nome;
    public Candidato(int id, int numero, String nome)
    {
        this.id = id;
        this.numero = numero;
        this.nome = nome;
    }
    public Candidato()
    {
        this(0,0,"");
    }
    public int getId()
    {
        return this.id;
    }
    public void setId(int id)
    {
        this.id=id;
    }
    public int getNumero()
    {
        return this.numero;
    }
    public void setNumero(int numero)
    {
        this.numero=numero;
    }
    public String getNome()
    {
        return this.nome;
    }
    public void setNome(String nome)
    {
        this.nome=nome;
    }

    @Override
    public int compareTo(Candidato candidato) {
        if(this.id  < candidato.getId())
            return -1;
        else if(this.id > candidato.getId())
            return 1;
        return 0;
    }
}
