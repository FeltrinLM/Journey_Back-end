package com.example.journey_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EstampaDTO {

    private int estampaId;

    @NotBlank
    private String nome;

    @Min(0)
    private int quantidade;

    @NotNull
    private Integer colecaoId;

    // Construtor vazio
    public EstampaDTO() {}

    // Construtor com parâmetros
    public EstampaDTO(int estampaId, String nome, int quantidade, Integer colecaoId) {
        this.estampaId = estampaId;
        this.nome = nome;
        this.quantidade = quantidade;
        this.colecaoId = colecaoId;
    }

    // Getters e Setters
    public int getEstampaId() {
        return estampaId;
    }

    public void setEstampaId(int estampaId) {
        this.estampaId = estampaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getColecaoId() {
        return colecaoId;
    }

    public void setColecaoId(Integer colecaoId) {
        this.colecaoId = colecaoId;
    }
}
