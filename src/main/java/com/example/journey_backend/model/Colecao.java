package com.example.journey_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity // Define a classe como uma entidade JPA.
@Table(name = "colecao")
public class Colecao {

    @Id // Define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento (estratégia do banco).
    private int colecaoId;

    @NotBlank
    private String nome;

    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private LocalDate dataFim;

    // OneToMany mapeia o relacionamento 1:N com Estampa e Chaveiro
    @OneToMany(mappedBy = "colecao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estampa> estampas;

    @OneToMany(mappedBy = "colecao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chaveiro> chaveiros;

    // Construtor vazio (obrigatório para JPA).
    public Colecao() {}

    // Construtor com parâmetros.
    public Colecao(String nome, LocalDate dataInicio, LocalDate dataFim) {
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

    public List<Estampa> getEstampas() {
        return estampas;
    }

    public void setEstampas(List<Estampa> estampas) {
        this.estampas = estampas;
    }

    public List<Chaveiro> getChaveiros() {
        return chaveiros;
    }

    public void setChaveiros(List<Chaveiro> chaveiros) {
        this.chaveiros = chaveiros;
    }
}
