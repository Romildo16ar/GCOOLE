package com.example.gcoole.Modelo;

public class Produtor {
    private int id;
    private String nome;
    private int tipo;
    private int numProd;

    public Produtor(){
        this.setId(0);
        this.setNome("");
        this.setTipo(-1);
        this.setNumProd(-1);
    }
    Produtor(int id, String nome, int tipo, int numProd){
        this.setId(id);
        this.setNome(nome);
        this.setTipo(tipo);
        this.setNumProd(numProd);
    }

    public String toString(){
        return "Proprietario{"+
                "id" + getId() +
                ", nome='" + getNome() + '\'' +
                ", tipo='" + getTipo() + '\'' +
                ", numProp='" + getNumProd() + '\'' +
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
}
