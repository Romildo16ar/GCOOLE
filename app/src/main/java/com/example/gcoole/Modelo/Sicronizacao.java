package com.example.gcoole.Modelo;

public class Sicronizacao {
    private int id;
    private String codigo;
    private int flag;

    public Sicronizacao(){
        this.setId(0);
        this.setCodigo("");
        this.setFlag(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
