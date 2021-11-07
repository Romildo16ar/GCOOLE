package com.example.gcoole.Modelo;

public class Producao {
    private int id;
    private int quant;
    private String data;
    private int idProdutor;
    private String idOnline;

    public Producao(){
        this.setId(0);
        this.setQuant(0);
        this.setData("");
        this.setIdProdutor(0);
        this.setIdOnline("");

    }

    public int getIdProdutor() {
        return idProdutor;
    }

    public void setIdProdutor(int idProdutor) {
        this.idProdutor = idProdutor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIdOnline() {
        return idOnline;
    }

    public void setIdOnline(String idOnline) {
        this.idOnline = idOnline;
    }
}
