package com.example.journey_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO para Usuario.
 * - senha é WRITE_ONLY: aceita no request, não é serializada nas respostas.
 * - validações básicas para nome, tipo e senha.
 */
public class UsuarioDTO {

    private int usuarioId;

    @NotBlank(message = "nome não deve estar em branco")
    private String nome;

    @NotNull(message = "tipo não pode ser nulo")
    private String tipo;

    @NotBlank(message = "senha não deve estar em branco")
    @Size(min = 8, message = "senha deve ter ao menos 8 caracteres")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    public UsuarioDTO() {}

    public UsuarioDTO(int usuarioId, String nome, String tipo) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.tipo = tipo;
    }

    // Getters / Setters
    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
