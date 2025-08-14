package com.example.journey_backend.dto;

public class AdesivoDTO {

    private int adesivo_id;
    private String adesivo_modelo;
    private boolean cromatico;

    public AdesivoDTO() {}

    public AdesivoDTO(int adesivo_id, String adesivo_modelo, boolean cromatico) {
        this.adesivo_id = adesivo_id;
        this.adesivo_modelo = adesivo_modelo;
        this.cromatico = cromatico;
    }

    public int getAdesivo_id() {
        return adesivo_id;
    }

    public void setAdesivo_id(int adesivo_id) {
        this.adesivo_id = adesivo_id;
    }

    public String getAdesivo_modelo() {
        return adesivo_modelo;
    }

    public void setAdesivo_modelo(String adesivo_modelo) {
        this.adesivo_modelo = adesivo_modelo;
    }

    public boolean isCromatico() {
        return cromatico;
    }

    public void setCromatico(boolean cromatico) {
        this.cromatico = cromatico;
    }
}
