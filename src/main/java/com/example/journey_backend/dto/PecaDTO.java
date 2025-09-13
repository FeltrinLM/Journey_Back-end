package com.example.journey_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class PecaDTO {

    private int pecaId;

    @NotBlank
    private String tipo;

    @NotBlank
    private String tamanho;

    @NotBlank
    private String cor;

    @Min(0)
    private int quantidade;

    // Construtor vazio
    public PecaDTO() {}

    // Construtor com parâmetros
    public PecaDTO(int pecaId, String tipo, String tamanho, String cor, int quantidade) {
        this.pecaId = pecaId;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.cor = cor;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public int getPecaId() {
        return pecaId;
    }

    public void setPecaId(int pecaId) {
        this.pecaId = pecaId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
