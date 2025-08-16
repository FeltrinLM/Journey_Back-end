package com.example.journey_backend.dto;

public class UsuarioDTO {

    private int usuarioId;
    private String nome;
    private String tipo;

    public UsuarioDTO() {}

    public UsuarioDTO(int usuarioId, String nome, String tipo) {
        this.usuarioId = usuarioId;
        this.nome = nome;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
