package com.example.journey_backend.dto;

public class UsuarioDTO {

    private int usuario_id;
    private String nome;
    private String tipo;

    public UsuarioDTO() {}

    public UsuarioDTO(int usuario_id, String nome, String tipo) {
        this.usuario_id = usuario_id;
        this.nome = nome;
        this.tipo = tipo;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
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
