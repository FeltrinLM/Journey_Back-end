package com.example.journey_backend.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class HistoricoAlteracaoDTO {

    private long id;
    private String entidade;
    private int entidadeId;
    private String campoAlterado;
    private String valorAntigo;
    private String valorNovo;

    @NotNull
    private LocalDateTime dataHora;

    @NotNull
    private Integer usuarioId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
