package com.example.journey_backend.model;

import jakarta.persistence.*;

@Entity     // Define a classe como uma entidade JPA.
public class Usuario {

    // Enum interno que representa os tipos possíveis de usuário no sistema.
    public enum TipoUsuario {
        ADMINISTRADOR,
        FUNCIONARIO
    }

    @Id     // Define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento (estratégia do banco).
    private int usuario_id;

    private String nome;  // Nome do usuário.
    private String senha; // Senha do usuário (armazenar sempre em formato hash).

    @Enumerated(EnumType.STRING)  // Armazena o valor do enum como texto no banco de dados.
    @Column(nullable = false)     // Define que o campo não pode ser nulo.
    private TipoUsuario tipo;     // Tipo do usuário: ADMINISTRADOR ou FUNCIONARIO.

    // Construtor padrão (obrigatório para JPA).
    public Usuario() {}

    // Construtor com parâmetros.
    public Usuario(String nome, String senha, TipoUsuario tipo) {
        this.nome = nome;
        this.senha = senha;
        this.tipo = tipo;
    }

    // Getters e Setters.

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
}
