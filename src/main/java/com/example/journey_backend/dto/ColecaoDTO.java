package com.example.journey_backend.dto;

import java.time.LocalDate;

public class ColecaoDTO {

    private int colecaoId;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    // Construtor vazio
    public ColecaoDTO() {}

    // Construtor com parâmetros
    public ColecaoDTO(int colecaoId, String nome, LocalDate dataInicio, LocalDate dataFim) {
        this.colecaoId = colecaoId;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    // Getters e Setters


    public int getColecaoId() {
        return colecaoId;
    }

    public void setColecaoId(int colecaoId) {
        this.colecaoId = colecaoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
}
