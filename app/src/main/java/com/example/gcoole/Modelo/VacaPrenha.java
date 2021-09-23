package com.example.gcoole.Modelo;

public class VacaPrenha {
    private int id;
    private String dataInicialGestacao;
    private int numeroGestacao;
    private int idVaca;

    public VacaPrenha(){
        this.setId(0);
        this.setDataInicialGestacao("");
        this.setNumeroGestacao(0);
        this.setIdVaca(0);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataInicialGestacao() {
        return dataInicialGestacao;
    }

    public void setDataInicialGestacao(String dataInicialGestacao) {
        this.dataInicialGestacao = dataInicialGestacao;
    }

    public int getNumeroGestacao() {
        return numeroGestacao;
    }

    public void setNumeroGestacao(int numeroGestacao) {
        this.numeroGestacao = numeroGestacao;
    }

    public int getIdVaca() {
        return idVaca;
    }

    public void setIdVaca(int idVaca) {
        this.idVaca = idVaca;
    }
}
