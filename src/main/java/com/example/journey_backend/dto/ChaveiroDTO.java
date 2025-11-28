package com.example.journey_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChaveiroDTO {

    private int chaveiroId;

    @NotBlank
    private String chaveiroModelo;

    @NotNull
    private Integer colecaoId;

    // Construtor vazio
    public ChaveiroDTO() {
        // Construtor intencionalmente vazio
    }

    // Construtor com parâmetros
    public ChaveiroDTO(int chaveiroId, String chaveiroModelo, Integer colecaoId) {
        this.chaveiroId = chaveiroId;
        this.chaveiroModelo = chaveiroModelo;
        this.colecaoId = colecaoId;
    }

    // Getters e Setters
    public int getChaveiroId() {
        return chaveiroId;
    }

    public void setChaveiroId(int chaveiroId) {
        this.chaveiroId = chaveiroId;
    }

    public String getChaveiroModelo() {
        return chaveiroModelo;
    }

    public void setChaveiroModelo(String chaveiroModelo) {
        this.chaveiroModelo = chaveiroModelo;
    }

    public Integer getColecaoId() {
        return colecaoId;
    }

    public void setColecaoId(Integer colecaoId) {
        this.colecaoId = colecaoId;
    }
}
