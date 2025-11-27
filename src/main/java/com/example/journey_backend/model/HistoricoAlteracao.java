package com.example.journey_backend.model;

// 1. Imports do Jakarta Persistence (JPA)
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// 2. Imports do Jakarta Validation
import jakarta.validation.constraints.NotNull;

// 3. Imports do Java Padrão
import java.time.LocalDateTime;

@Entity
@Table(name = "historicoAlteracao") // Nome da tabela padronizado em snake_case
public class HistoricoAlteracao {

    @Id // Define a chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento (estratégia do banco).
    private Long id;

    private String entidade;      // Nome da entidade alterada (Ex: "Peca", "Colecao").
    private int entidadeId;       // ID da entidade alterada.

    private String campoAlterado; // Campo que foi alterado (Ex: "quantidade", "nome").
    private String valorAntigo;   // Valor antigo do campo.
    private String valorNovo;     // Valor novo do campo.

    @NotNull
    @Column(nullable = false)
    private LocalDateTime dataHora; // Data e hora da alteração.

    @ManyToOne(fetch = FetchType.LAZY) // Muitos históricos podem estar ligados a um único usuário.
    @JoinColumn(name = "usuarioId", nullable = false) // Cria a FK na tabela historico_alteracao.
    @NotNull
    private Usuario usuario;  // Usuário que realizou a alteração.

    // Construtor padrão (obrigatório para JPA).
    public HistoricoAlteracao() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public int getEntidadeId() {
        return entidadeId;
    }

    public void setEntidadeId(int entidadeId) {
        this.entidadeId = entidadeId;
    }

    public String getCampoAlterado() {
        return campoAlterado;
    }

    public void setCampoAlterado(String campoAlterado) {
        this.campoAlterado = campoAlterado;
    }

    public String getValorAntigo() {
        return valorAntigo;
    }

    public void setValorAntigo(String valorAntigo) {
        this.valorAntigo = valorAntigo;
    }

    public String getValorNovo() {
        return valorNovo;
    }

    public void setValorNovo(String valorNovo) {
        this.valorNovo = valorNovo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
