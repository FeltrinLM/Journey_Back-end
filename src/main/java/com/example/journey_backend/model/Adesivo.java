package com.example.journey_backend.model;

import jakarta.persistence.*;

@Entity        //define a classe como uma entidade JPA.
public class Adesivo {

    @Id     //define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-incremento (estratégia do banco).
    private int adesivoId;

    private String adesivoModelo;
    private boolean cromatico;

    // Construtor vazio (obrigatório para JPA).
    public Adesivo() {}

    // Construtor com parâmetros.
    public Adesivo(String adesivoModelo, boolean cromatico) {
        this.adesivoModelo = adesivoModelo;
        this.cromatico = cromatico;
    }

    // Getters e Setters

    public int getAdesivoId() {return adesivoId;}

    public void setAdesivoId(int adesivoId) {
        this.adesivoId = adesivoId;
    }

    public String getAdesivoModelo() {
        return adesivoModelo;
    }

    public void setAdesivoModelo(String adesivoModelo) {
        this.adesivoModelo = adesivoModelo;
    }

    public boolean isCromatico() {
        return cromatico;
    }

    public void setCromatico(boolean cromatico) {
        this.cromatico = cromatico;
    }
}
