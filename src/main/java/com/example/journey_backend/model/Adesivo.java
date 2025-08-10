package com.example.journey_backend.model;

import jakarta.persistence.*;

@Entity        //define a classe como uma entidade JPA.
public class Adesivo {

    @Id     //define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-incremento (estratégia do banco).
    private int adesivo_id;

    private String adesivo_modelo;
    private boolean cromatico;

    // Construtor vazio (obrigatório para JPA).
    public Adesivo() {}

    // Construtor com parâmetros.
    public Adesivo(String adesivo_modelo, boolean cromatico) {
        this.adesivo_modelo = adesivo_modelo;
        this.cromatico = cromatico;
    }

    // Getters e Setters

    public int getAdesivo_id() {
        return adesivo_id;
    }

    public void setAdesivo_id(int adesivo_id) {
        this.adesivo_id = adesivo_id;
    }

    public String getAdesivo_modelo() {
        return adesivo_modelo;
    }

    public void setAdesivo_modelo(String adesivo_modelo) {
        this.adesivo_modelo = adesivo_modelo;
    }

    public boolean isCromatico() {
        return cromatico;
    }

    public void setCromatico(boolean cromatico) {
        this.cromatico = cromatico;
    }
}
