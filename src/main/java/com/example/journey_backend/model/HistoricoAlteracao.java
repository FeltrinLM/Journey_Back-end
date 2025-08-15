package com.example.journey_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_alteracao")
public class HistoricoAlteracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entidade;      // Ex: "Peca", "Colecao"
    private int entidadeId;       // ID da entidade alterada

    private String campoAlterado; // Ex: "quantidade", "nome"
    private String valorAntigo;
    private String valorNovo;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; // usuário que fez a alteração

    // ================== GETTERS E SETTERS ==================

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
