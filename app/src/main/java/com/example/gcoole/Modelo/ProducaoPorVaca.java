package com.example.gcoole.Modelo;

public class ProducaoPorVaca {

    private int id;
    private int quant;
    private String data;
    private int idVaca;

    public ProducaoPorVaca(){
        this.setId(0);
        this.setQuant(0);
        this.setData("");
        this.setIdVaca(0);


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

    public int getIdVaca() {
        return idVaca;
    }

    public void setIdVaca(int idVaca) {
        this.idVaca = idVaca;
    }


}
