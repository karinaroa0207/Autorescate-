package edu.co.udistrital.model;

public class Grua extends Unidad {
    
    private double capacidadToneladas;

    public Grua(String zona, double capacidadToneladas) {
        super(zona);
        this.capacidadToneladas = capacidadToneladas;
    }

    @Override
    public String getTipo() {
        return "Grua";
    }

    public double getCapacidadToneladas() {
        return capacidadToneladas;
    }
}