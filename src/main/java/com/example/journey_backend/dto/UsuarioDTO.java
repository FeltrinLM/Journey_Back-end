package com.example.journey_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size; // Importado para @Size

public class UsuarioDTO {

    private int usuarioId;

    @NotBlank
    private String nome;

    @NotBlank // ADICIONADO: Senha não pode ser vazia
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.") // ADICIONADO: Regra mínima
    private String senha; // ADICIONADO: Campo para receber a senha

    @NotNull
    private String tipo;

    public UsuarioDTO() {
        // Construtor intencionalmente vazio
    }

    // CONSTRUTOR ATUALIZADO
    public UsuarioDTO(int usuarioId, String nome, String senha, String tipo) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.senha = senha;
        this.tipo = tipo;
    }

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

    // GETTER ADICIONADO
    public String getSenha() {
        return senha;
    }

    // SETTER ADICIONADO
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}