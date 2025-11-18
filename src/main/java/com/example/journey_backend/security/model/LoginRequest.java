package com.example.journey_backend.security.model;

import jakarta.validation.constraints.NotBlank;

// Esta classe representa o JSON que o Postman vai enviar no Body
public class LoginRequest {
    @NotBlank
    private String nome;

    @NotBlank
    private String senha;

    // Getters e Setters
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}