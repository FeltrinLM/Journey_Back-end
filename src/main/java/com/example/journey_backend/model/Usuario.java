package com.example.journey_backend.model;

// 1. Imports do Jakarta Persistence (JPA)
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

// 2. Imports do Jakarta Validation
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// 3. Imports do Jackson (JSON)
import com.fasterxml.jackson.annotation.JsonIgnore;

// 4. Imports do Java Padrão
import java.util.ArrayList;
import java.util.List;

@Entity // Define a classe como uma entidade JPA.
@Table(name = "usuario")
public class Usuario {

    // Enum interno que representa os tipos possíveis de usuário no sistema.
    public enum TipoUsuario {
        ADMINISTRADOR,
        FUNCIONARIO
    }

    @Id // Define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento (estratégia do banco).
    private int usuarioId;

    @Column(unique = true) // Evita duplicidade de nomes de usuário no banco.
    @NotBlank
    private String nome; // Nome do usuário.

    @NotBlank
    @Size(min = 8)
    private String senha; // Senha do usuário (armazenar sempre em formato hash).

    @Enumerated(EnumType.STRING) // Armazena o valor do enum como texto no banco de dados.
    @Column(nullable = false)
    @NotNull
    private TipoUsuario tipo; // Tipo do usuário: ADMINISTRADOR ou FUNCIONARIO.

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY) // Um usuário pode ter vários históricos de alteração.
    @JsonIgnore // Evita loop infinito na serialização JSON.
    private List<HistoricoAlteracao> historicos = new ArrayList<>();

    public Usuario() {
        // Construtor vazio (obrigatório para JPA).
    }

    // Construtor com parâmetros.
    public Usuario(String nome, String senha, TipoUsuario tipo) {
        this.nome = nome;
        this.senha = senha;
        this.tipo = tipo;
    }

    // Getters e Setters.
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

    public List<HistoricoAlteracao> getHistoricos() {
        return historicos;
    }

    public void setHistoricos(List<HistoricoAlteracao> historicos) {
        this.historicos = historicos;
    }
}
