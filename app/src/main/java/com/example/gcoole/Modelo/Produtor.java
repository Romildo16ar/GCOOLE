package com.example.gcoole.Modelo;

public class Produtor {
    private int id;
    private String nome;
    private int tipo;
    private int numProd;
    private String codigoSocronizacao;

    public Produtor(){
        this.setId(0);
        this.setNome("");
        this.setTipo(-1);
        this.setNumProd(-1);
        this.setCodigoSocronizacao("");

    }
    Produtor(int id, String nome, int tipo, int numProd, String codigoSocronizacao){
        this.setId(id);
        this.setNome(nome);
        this.setTipo(tipo);
        this.setNumProd(numProd);
        this.setCodigoSocronizacao(codigoSocronizacao);
    }

    public String toString(){
        return "Produtor{"+
                "id" + getId() +
                ", nome='" + getNome() + '\'' +
                ", tipo='" + getTipo() + '\'' +
                ", numProp='" + getNumProd() + '\'' +
                ", codigoSocronizacao='" + getCodigoSocronizacao() + '\'' +
                '}';

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getNumProd() {
        return numProd;
    }

    public void setNumProd(int numProd) {
        this.numProd = numProd;
    }

    public String getCodigoSocronizacao() {
        return codigoSocronizacao;
    }

    public void setCodigoSocronizacao(String codigoSocronizacao) {
        this.codigoSocronizacao = codigoSocronizacao;
    }
}
