package com.example.journey_backend.security.model; // (ou onde você guarda seus DTOs)

// Esta classe representa a resposta JSON com o token
public class LoginResponse {
    private String token;
    private String tipo = "Bearer";

    public LoginResponse(String token) {
        this.token = token;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}