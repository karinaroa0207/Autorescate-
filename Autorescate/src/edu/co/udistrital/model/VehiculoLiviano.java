package edu.co.udistrital.model;

public class VehiculoLiviano extends Unidad {

    private int capacidadPasajeros;

    public VehiculoLiviano(String zona, int capacidadPasajeros) {
        super(zona);
        this.capacidadPasajeros = capacidadPasajeros;
    }

    @Override
    public String getTipo() {
        return "Vehiculo liviano";
    }

    public int getCapacidadPasajeros() {
        return capacidadPasajeros;
    }

    @Override
    public Unidad clonar() {
        return new VehiculoLiviano(this.getZona(), capacidadPasajeros);
    }
}
