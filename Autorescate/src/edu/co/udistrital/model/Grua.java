package edu.co.udistrital.model;

public class Grua extends Unidad {
    
    private double capacidadToneladas;

    public Grua(String zona, double capacidadToneladas) {
        super(zona);
        this.capacidadToneladas = capacidadToneladas;
    }

    @Override
    public TipoVehiculo getTipo() {
        return TipoVehiculo.GRUA;
    }

    public double getCapacidadToneladas() {
        return capacidadToneladas;
    }

    @Override
    public Unidad crearCopiaBase() {
        return new Grua(this.getZona(), capacidadToneladas);
    }
}