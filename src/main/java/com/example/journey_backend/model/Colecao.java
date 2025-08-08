package com.example.journey_backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity     //define a classe como uma entidade JPA.
public class Colecao {

    @Id     //define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incremento (estratégia do banco).
    private int colecao_id;

    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    //OneToMany mapeia o relacionamento 1:N com Estampa e Chaveiro
    //cascade = ALL: garante que alterações em Colecao afetem os filhos.
    @OneToMany(mappedBy = "colecao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estampa> estampas;

    @OneToMany(mappedBy = "colecao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chaveiro> chaveiros;


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
