package edu.co.udistrital.model;

public enum TipoRepuesto {
    LLANTA("Llanta"),
    BATERIA("Bateria"),
    GASOLINA("Gasolina");

    private final String descripcion;

    TipoRepuesto(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
