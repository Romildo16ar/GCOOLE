package com.example.gcoole.Modelo;

public class Vaca {

    private int id;
    private String nome;
    private int numVaca;

    public Vaca(){
        this.setId(0);
        this.setNome("");
        this.setNumVaca(-1);
    }
    Vaca(int id, String nome, int numVaca){
        this.setId(id);
        this.setNome(nome);
        this.setNumVaca(numVaca);
    }

    public String toString(){
        return "Proprietario{"+
                "id" + getId() +
                ", nome='" + getNome() + '\'' +
                ", numProp='" + getNumVaca() + '\'' +
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

    public int getNumVaca() {
        return numVaca;
    }

    public void setNumVaca(int numVaca) {
        this.numVaca = numVaca;
    }



}
