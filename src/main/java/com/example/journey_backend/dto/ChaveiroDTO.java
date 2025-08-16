package com.example.journey_backend.dto;

public class ChaveiroDTO {

    private int chaveiroId;
    private String chaveiroModelo;
    private int colecaoId;

    // Construtor vazio
    public ChaveiroDTO() {}

    // Construtor com parâmetros
    public ChaveiroDTO(int chaveiroId, String chaveiroModelo, int colecaoId) {
        this.chaveiroId = chaveiroId;
        this.chaveiroModelo = chaveiroModelo;
        this.colecaoId = colecaoId;
    }

    // Getters e Setters

    public int getChaveiroId() {return chaveiroId; }

    public void setChaveiroId(int chaveiroId) {
        this.chaveiroId = chaveiroId;
    }

    public String getChaveiroModelo() {
        return chaveiroModelo;
    }

    public void setChaveiroModelo(String chaveiroModelo) {
        this.chaveiroModelo = chaveiroModelo;
    }

    public int getColecaoId() {
        return colecaoId;
    }

    public void setColecaoId(int colecaoId) {
        this.colecaoId = colecaoId;
    }
}
