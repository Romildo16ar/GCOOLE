package com.example.gcoole.Modelo;

public class ValorPorLitro {
    private int id;
    private float valor;
    private int mes;
    private int ano;

    public ValorPorLitro(){
        this.setId(0);
        this.setValor(0);
        this.setMes(0);
        this.setAno(0);
    }

    public ValorPorLitro(int id , float valor, int mes , int ano){
        this.setId(id);
        this.setValor(valor);
        this.setMes(mes);
        this.setAno(ano);

    }

    @Override
    public String toString() {
        return "ValorPorLitro{" +
                "id=" + getId() +
                ", valor=" + getValor() +
                ", mes=" + getMes() +
                ", ano=" + getAno() +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
