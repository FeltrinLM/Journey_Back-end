package com.example.journey_backend.dto;

import java.time.LocalDate;

public class ColecaoDTO {

    private int colecao_id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    // Construtor vazio
    public ColecaoDTO() {}

    // Construtor com parâmetros
    public ColecaoDTO(int colecao_id, String nome, LocalDate dataInicio, LocalDate dataFim) {
        this.colecao_id = colecao_id;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    // Getters e Setters

    public int getColecao_id() {
        return colecao_id;
    }

    public void setColecao_id(int colecao_id) {
        this.colecao_id = colecao_id;
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
