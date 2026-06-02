package edu.co.udistrital.model;

public enum TipoVehiculo {

    GRUA("Grua"),
    MOTO_DE_APOYO("Moto de apoyo"),
    CAMIONETA("Camioneta"),
    VEHICULO_LIVIANO("Vehiculo liviano");

    private final String descripcion;

    TipoVehiculo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
