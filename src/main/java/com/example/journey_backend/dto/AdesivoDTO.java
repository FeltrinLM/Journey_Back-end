package com.example.journey_backend.dto;

import jakarta.validation.constraints.NotBlank;

public class AdesivoDTO {

    private int adesivoId;

    @NotBlank
    private String adesivoModelo;

    private boolean cromatico;

    public AdesivoDTO() {}

    public AdesivoDTO(int adesivoId, String adesivoModelo, boolean cromatico) {
        this.adesivoId = adesivoId;
        this.adesivoModelo = adesivoModelo;
        this.cromatico = cromatico;
    }

    public int getAdesivoId() {
        return adesivoId;
    }

    public void setAdesivoId(int adesivoId) {
        this.adesivoId = adesivoId;
    }

    public String getAdesivoModelo() {
        return adesivoModelo;
    }

    public void setAdesivoModelo(String adesivoModelo) {
        this.adesivoModelo = adesivoModelo;
    }

    public boolean isCromatico() {
        return cromatico;
    }

    public void setCromatico(boolean cromatico) {
        this.cromatico = cromatico;
    }
}
