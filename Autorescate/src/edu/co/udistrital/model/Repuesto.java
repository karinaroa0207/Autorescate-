package edu.co.udistrital.model;

import java.util.UUID;

public class Repuesto {

    private final String codigoRepuesto;
    private TipoRepuesto tipo;

    public Repuesto(TipoRepuesto tipo) {
        this.codigoRepuesto = UUID.randomUUID().toString();
        this.tipo = tipo;
    }

    public String getCodigoRepuesto() {
        return codigoRepuesto;
    }

    public TipoRepuesto getTipo() {
        return tipo;
    }

    public void setTipo(TipoRepuesto tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "[" + codigoRepuesto + "] (" + (tipo != null ? tipo.toString() : "") + ")";
    }
}
