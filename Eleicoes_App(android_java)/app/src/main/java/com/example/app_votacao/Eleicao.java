package com.example.app_votacao;

import java.util.List;

// SIMPLES CLASS DE JAVA USADA PARA RECEBER AS INFORMAÇÕES DAS ELEIÇÕES VINDAS DO BACK
public class Eleicao {
    private int id;
    private int ano;
    private String tipo;
    public Eleicao(int id, int ano, String tipo)
    {
        this.id = id;
        this.ano = ano;
        this.tipo = tipo;
    }
    public Eleicao()
    {
        this.id = 0;
        this.ano = 0;
        this.tipo = "";
    }
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getAno()
    {
        return this.ano;
    }

    public void setAno(int ano)
    {
        this.ano = ano;
    }

    public String getTipo()
    {
        return this.tipo;
    }
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }
}
